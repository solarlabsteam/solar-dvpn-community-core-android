package co.sentinel.dvpn.domain.features.dvpn.model

import co.sentinel.dvpn.domain.core.exception.Failure

sealed class ConnectionEvent {
    data class ConnectionStateChanged(val isConnected: Boolean) : ConnectionEvent()
    data class ConnectionError(val failure: Failure) : ConnectionEvent()
    data class AttemptToConnect(val nodeAddress: String) : ConnectionEvent()
    data class AttemptToConnectWithCredentials(val credentials: ConnectionCredentials) : ConnectionEvent()
}
