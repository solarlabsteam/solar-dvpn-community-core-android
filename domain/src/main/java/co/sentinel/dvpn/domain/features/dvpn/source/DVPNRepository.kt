package co.sentinel.dvpn.domain.features.dvpn.source

import android.content.Context
import android.content.Intent
import co.sentinel.dvpn.domain.core.exception.Failure
import co.sentinel.dvpn.domain.core.functional.Either
import co.sentinel.dvpn.domain.core.interactor.Success
import co.sentinel.dvpn.domain.features.dvpn.CreateTunnel
import co.sentinel.dvpn.domain.features.dvpn.DeleteTunnel
import co.sentinel.dvpn.domain.features.dvpn.GetTunnel
import co.sentinel.dvpn.domain.features.dvpn.GetTunnelConfig
import co.sentinel.dvpn.domain.features.dvpn.GetTunnelDuration
import co.sentinel.dvpn.domain.features.dvpn.GetTunnelStatistics
import co.sentinel.dvpn.domain.features.dvpn.GetTunnels
import co.sentinel.dvpn.domain.features.dvpn.InitBackend
import co.sentinel.dvpn.domain.features.dvpn.LoadTunnels
import co.sentinel.dvpn.domain.features.dvpn.RestoreState
import co.sentinel.dvpn.domain.features.dvpn.SetTunnelConfig
import co.sentinel.dvpn.domain.features.dvpn.SetTunnelName
import co.sentinel.dvpn.domain.features.dvpn.SetTunnelState
import co.sentinel.dvpn.domain.features.dvpn.model.DnsServer
import co.sentinel.dvpn.domain.features.dvpn.model.KeyPair
import co.sentinel.dvpn.domain.features.hub.model.VpnProfile
import java.lang.ref.WeakReference

/**
 * Repository that handles communication to the vpn implementation.
 */
interface DVPNRepository {

    /**
     * Inits and configures the backend. Must be called in the Application class before everything.
     */
    suspend fun init(params: InitBackend.InitBackendParams)

    /**
     * Returns currently loaded tunnels, or loads and returns if called the first time.
     */
    suspend fun getTunnels(): Either<Failure, GetTunnels.Success>

    /**
     * Creates a new configured tunnel.
     */
    suspend fun create(params: CreateTunnel.CreateTunnelParams): Either<Failure, CreateTunnel.Success>

    /**
     * Deletes an existing tunnel.
     */
    suspend fun delete(params: DeleteTunnel.DeleteTunnelParams): Either<Failure, DeleteTunnel.Success>

    /**
     * Fetches the tunnel configuration.
     */
    suspend fun getTunnelConfig(params: GetTunnelConfig.GetTunnelConfigParams): Either<Failure, GetTunnelConfig.Success>

    /**
     * Loads the tunnel list into cache.
     */
    suspend fun loadTunnels(): Either<Failure, LoadTunnels.Success>

    /**
     * Reloads tunnel states and updates the cached list.
     */
    fun refreshTunnelStates()

    /**
     * Helps restore tunnel config state when restarting app.
     */
    suspend fun restoreState(params: RestoreState.RestoreStateParams)

    /**
     * Saves current state of currently running tunnel so it can be restored later.
     */
    suspend fun saveState()

    /**
     * Sets new config parameters to a tunnel.
     */
    suspend fun setTunnelConfig(params: SetTunnelConfig.SetTunnelConfigParams): Either<Failure, SetTunnelConfig.Success>

    /**
     * Updates tunnel name.
     */
    suspend fun setTunnelName(params: SetTunnelName.SetTunnelNameParams): Either<Failure, SetTunnelName.Success>

    /**
     * Updates tunnel state.
     */
    suspend fun setTunnelState(params: SetTunnelState.SetTunnelStateParams): Either<Failure, SetTunnelState.Success>

    /**
     * Returns tunnel stats.
     */
    suspend fun getTunnelStatistics(params: GetTunnelStatistics.GetTunnelStatisticsParams): Either<Failure, GetTunnelStatistics.Success>

    /**
     * Generates private/public key pair for creating new tunnels.
     */
    fun generateKeyPair(): KeyPair
    fun generateKeyPair(privateKey: String): KeyPair

    /**
     * Returns GoBackend implementation of VpnService Intent.
     */
    fun getVpnServiceIntent(activity: WeakReference<Context>): Either<Failure, Intent?>

    suspend fun createOrUpdate(
        name: String,
        vpnProfile: VpnProfile,
        keyPair: KeyPair,
        nodeAddress: String,
        subscriptionId: Long
    ): Either<Failure, CreateTunnel.Success>

    suspend fun getTunnel(tunnelName: String): Either<Failure, GetTunnel.Success>
    fun getTunnelDuration(subscriptionId: Long?): Either<Failure, GetTunnelDuration.Success>

    /**
     * Updates default DNS.
     */
    suspend fun updateDns(dns: DnsServer): Either<Failure, Success>

    /**
     * Returns default DNS used.
     */
    suspend fun getDefaultDns(): DnsServer

    /**
     * Returns list of available DNS servers.
     */
    suspend fun getDnsList(): List<DnsServer>

}