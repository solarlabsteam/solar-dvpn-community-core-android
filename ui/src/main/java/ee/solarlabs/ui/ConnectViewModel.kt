package ee.solarlabs.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.sentinel.dvpn.domain.core.functional.requireLeft
import co.sentinel.dvpn.domain.core.functional.requireRight
import co.sentinel.dvpn.domain.features.dvpn.GetVpnServiceIntent
import co.sentinel.dvpn.domain.features.dvpn.model.ConnectionCredentials
import co.sentinel.dvpn.domain.features.dvpn.model.ConnectionEvent
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.ConnectionEventBus
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.PostConnection
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.PostConnectionWithCredentials
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConnectViewModel : ViewModel(), KoinComponent {
    private val getVpnServiceIntent: GetVpnServiceIntent by inject()
    private val postConnection: PostConnection by inject()
    private val postConnectionWithCredentials: PostConnectionWithCredentials by inject()
    private val connectionEventBus by inject<ConnectionEventBus>()

    private val _connectionState: MutableLiveData<ConnectionState> = MutableLiveData()
    val connectionState: LiveData<ConnectionState> = _connectionState

    private var connectionDetails: ConnectionDetails? = null

    init {
        viewModelScope.launch {
            connectionEventBus.events
                .filter { it is ConnectionEvent.AttemptToConnect || it is ConnectionEvent.AttemptToConnectWithCredentials }
                .collectLatest {
                    when (it) {
                        is ConnectionEvent.AttemptToConnect -> {
                            connectionDetails = ConnectionDetails.NodeAddress(it.nodeAddress)
                            _connectionState.value = ConnectionState.CheckPermission
                        }

                        is ConnectionEvent.AttemptToConnectWithCredentials -> {
                            connectionDetails = ConnectionDetails.Credentials(it.credentials)
                            _connectionState.value = ConnectionState.CheckPermission
                        }

                        else -> Unit
                    }
                }
        }
    }

    /**
     * When receiving the connect event from the core, check if vpn permission is granted by launching this intent.
     */
    fun vpnCheckIntent(context: Context) =
        getVpnServiceIntent(context).let { if (it.isRight) it.requireRight() else null }

    fun attemptConnection() {
        when (val connectionDetailsLocal = connectionDetails) {
            is ConnectionDetails.NodeAddress -> {
                _connectionState.value = ConnectionState.AttemptingConnection
                postConnection(viewModelScope, PostConnection.Params(connectionDetailsLocal.nodeAddress)) {
                    _connectionState.value = ConnectionState.Done
                    if (it.isLeft) {
                        viewModelScope.launch {
                            connectionEventBus.emitEvent(ConnectionEvent.ConnectionError(it.requireLeft()))
                        }
                    }
                }
            }

            is ConnectionDetails.Credentials -> {
                _connectionState.value = ConnectionState.AttemptingConnection
                postConnectionWithCredentials(
                    viewModelScope,
                    PostConnectionWithCredentials.Params(connectionDetailsLocal.credentials),
                ) {
                    _connectionState.value = ConnectionState.Done
                    if (it.isLeft) {
                        viewModelScope.launch {
                            connectionEventBus.emitEvent(ConnectionEvent.ConnectionError(it.requireLeft()))
                        }
                    }
                }
            }

            else -> Unit
        }
    }
}

sealed interface ConnectionDetails {
    data class NodeAddress(val nodeAddress: String) : ConnectionDetails
    data class Credentials(val credentials: ConnectionCredentials) : ConnectionDetails
}
