package co.sentinel.dvpn.domain.features.dvpn.tasks.connection

import co.sentinel.dvpn.domain.core.DVPN_NODE_NAME
import co.sentinel.dvpn.domain.core.exception.Failure
import co.sentinel.dvpn.domain.core.functional.Either
import co.sentinel.dvpn.domain.core.functional.requireLeft
import co.sentinel.dvpn.domain.core.functional.requireRight
import co.sentinel.dvpn.domain.core.interactor.BaseUseCase
import co.sentinel.dvpn.domain.features.dvpn.SetTunnelState
import co.sentinel.dvpn.domain.features.dvpn.model.ConnectionCredentials
import co.sentinel.dvpn.domain.features.dvpn.model.DvpnTunnel
import co.sentinel.dvpn.domain.features.dvpn.source.DVPNRepository
import co.sentinel.dvpn.domain.features.hub.source.HubRemoteRepository

class PostConnectionWithCredentials(
    private val hubRepository: HubRemoteRepository,
    private val dvpnRepository: DVPNRepository,
) : BaseUseCase<PostConnectionWithCredentials.Success, PostConnectionWithCredentials.Params>() {

    data class Params(val credentials: ConnectionCredentials)

    override suspend fun run(params: Params): Either<Failure, Success> {
        val credentials = params.credentials
        // create vpn profile
        val fetchVpnProfile = hubRepository.parseVpnProfile(credentials.payload)
        if (fetchVpnProfile.isLeft) {
            return Either.Left(fetchVpnProfile.requireLeft())
        }

        // create or update tunnel
        val keyPair = dvpnRepository.generateKeyPair(credentials.privateKey)
        val createOrUpdateTunnel = dvpnRepository.createOrUpdate(
            DVPN_NODE_NAME,
            fetchVpnProfile.requireRight(),
            keyPair,
            credentials.nodeAddress,
            credentials.subscriptionId,
        )

        if (createOrUpdateTunnel.isLeft) {
            return Either.Left(createOrUpdateTunnel.requireLeft())
        }

        // start the tunnel
        val setTunnelStateResult = dvpnRepository.setTunnelState(
            SetTunnelState.SetTunnelStateParams(
                DVPN_NODE_NAME,
                DvpnTunnel.State.UP,
            ),
        )

        if (setTunnelStateResult.isLeft) {
            return Either.Left(setTunnelStateResult.requireLeft())
        }

        return Either.Right(Success(setTunnelStateResult.requireRight().tunnel))
    }

    data class Success(val tunnel: DvpnTunnel)
}
