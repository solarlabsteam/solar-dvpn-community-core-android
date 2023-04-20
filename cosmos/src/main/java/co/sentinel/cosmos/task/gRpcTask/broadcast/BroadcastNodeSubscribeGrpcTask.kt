package co.sentinel.cosmos.task.gRpcTask.broadcast

import co.sentinel.cosmos.R
import co.sentinel.cosmos.base.BaseChain
import co.sentinel.cosmos.base.BaseConstant
import co.sentinel.cosmos.base.BaseConstant.ERROR_CODE_BROADCAST
import co.sentinel.cosmos.base.BaseCosmosApp
import co.sentinel.cosmos.cosmos.Signer
import co.sentinel.cosmos.crypto.CryptoHelper
import co.sentinel.cosmos.dao.Account
import co.sentinel.cosmos.model.type.Fee
import co.sentinel.cosmos.network.ChannelBuilder
import co.sentinel.cosmos.task.CommonTask
import co.sentinel.cosmos.task.TaskResult
import co.sentinel.cosmos.utils.WKey
import com.google.protobuf2.Any
import cosmos.auth.v1beta1.QueryGrpc
import cosmos.auth.v1beta1.QueryOuterClass.QueryAccountRequest
import cosmos.auth.v1beta1.QueryOuterClass.QueryAccountResponse
import cosmos.tx.v1beta1.ServiceGrpc
import kotlinx.coroutines.guava.await
import org.bitcoinj.crypto.DeterministicKey
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


class BroadcastNodeSubscribeGrpcTask(
    app: BaseCosmosApp,
    private val mBaseChain: BaseChain,
    private val mAccount: Account,
    private val mToAddress: String,
    private val mMessage: Any,
    private val mFees: Fee,
    private val mChainId: String
) : CommonTask(app) {
    private var mAuthResponse: QueryAccountResponse? = null
    private var deterministicKey: DeterministicKey? = null

    init {
        mResult.taskType = BaseConstant.TASK_GRPC_BROAD_SEND
    }

    override suspend fun doInBackground(vararg strings: String): TaskResult {
        try {
            val entropy = CryptoHelper.doDecryptData(
                app.context.getString(R.string.key_mnemonic) + mAccount.uuid,
                mAccount.resource,
                mAccount.spec
            )
            deterministicKey = WKey.getKeyWithPathfromEntropy(
                BaseChain.getChain(mAccount.baseChain),
                entropy,
                mAccount.path.toInt(),
                mAccount.newBip44
            )
            val authStub = QueryGrpc.newFutureStub(ChannelBuilder.getChain(mBaseChain))
            val request = QueryAccountRequest.newBuilder().setAddress(mAccount.address).build()
            mAuthResponse = authStub.account(request).await()

            //broadCast
            val txService = ServiceGrpc.newFutureStub(ChannelBuilder.getChain(mBaseChain))
                .withDeadlineAfter(ChannelBuilder.TIME_OUT.toLong(), TimeUnit.SECONDS)
            val broadcastTxRequest = Signer.getGrpcNodeSubscribeReq(
                mAuthResponse,
                mToAddress,
                mFees,
                mMessage,
                deterministicKey,
                mChainId
            )
            val response = txService.broadcastTx(broadcastTxRequest).await()
            mResult.resultData = response.txResponse.txhash
            if (response.txResponse.code > 0) {
                mResult.errorCode = response.txResponse.code
                mResult.errorMsg = response.txResponse.raTimber
                mResult.isSuccess = false
            } else {
                mResult.isSuccess = true
            }
        } catch (e: Exception) {
            Timber.e("BroadcastNodeSubscribeGrpcTask " + e.message)
            mResult.errorCode = ERROR_CODE_BROADCAST
            mResult.errorMsg = e.localizedMessage ?: "Task error occurred."
            mResult.isSuccess = false
        }
        return mResult
    }
}
