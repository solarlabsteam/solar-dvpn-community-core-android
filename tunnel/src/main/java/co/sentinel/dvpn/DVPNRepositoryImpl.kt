package co.sentinel.dvpn

import android.annotation.SuppressLint
import android.content.Context
import co.sentinel.dvpn.core.mapper.ConfigToDomainConfigMapper
import co.sentinel.dvpn.core.mapper.DomainConfigToConfigMapper
import co.sentinel.dvpn.core.mapper.DomainStateToStateMapper
import co.sentinel.dvpn.core.mapper.DomainTunnelToTunnelMapper
import co.sentinel.dvpn.core.mapper.TunnelToDomainTunnelMapper
import co.sentinel.dvpn.core.model.TunnelWrapper
import co.sentinel.dvpn.core.store.ConfigStore
import co.sentinel.dvpn.core.store.ConnectionDurationStore
import co.sentinel.dvpn.core.store.TunnelCacheStore
import co.sentinel.dvpn.core.store.UserPreferenceStore
import co.sentinel.dvpn.domain.core.exception.Failure
import co.sentinel.dvpn.domain.core.functional.Either
import co.sentinel.dvpn.domain.core.functional.requireLeft
import co.sentinel.dvpn.domain.core.functional.requireRight
import co.sentinel.dvpn.domain.core.interactor.Success
import co.sentinel.dvpn.domain.features.dvpn.CreateTunnel
import co.sentinel.dvpn.domain.features.dvpn.DeleteTunnel
import co.sentinel.dvpn.domain.features.dvpn.GetTunnel
import co.sentinel.dvpn.domain.features.dvpn.GetTunnelConfig
import co.sentinel.dvpn.domain.features.dvpn.GetTunnelDuration
import co.sentinel.dvpn.domain.features.dvpn.GetTunnelStatistics
import co.sentinel.dvpn.domain.features.dvpn.GetTunnels
import co.sentinel.dvpn.domain.features.dvpn.GetVpnServiceIntent
import co.sentinel.dvpn.domain.features.dvpn.InitBackend
import co.sentinel.dvpn.domain.features.dvpn.LoadTunnels
import co.sentinel.dvpn.domain.features.dvpn.RestoreState
import co.sentinel.dvpn.domain.features.dvpn.SetTunnelConfig
import co.sentinel.dvpn.domain.features.dvpn.SetTunnelName
import co.sentinel.dvpn.domain.features.dvpn.SetTunnelState
import co.sentinel.dvpn.domain.features.dvpn.model.ConnectionEvent
import co.sentinel.dvpn.domain.features.dvpn.model.Dns
import co.sentinel.dvpn.domain.features.dvpn.model.DnsServer
import co.sentinel.dvpn.domain.features.dvpn.model.TunnelConfig
import co.sentinel.dvpn.domain.features.dvpn.model.TunnelInterface
import co.sentinel.dvpn.domain.features.dvpn.model.TunnelPeer
import co.sentinel.dvpn.domain.features.dvpn.source.DVPNRepository
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.ConnectionEventBus
import co.sentinel.dvpn.domain.features.hub.model.VpnProfile
import com.wireguard.android.backend.Backend
import com.wireguard.android.backend.GoBackend
import com.wireguard.android.backend.Tunnel
import com.wireguard.android.backend.WgQuickBackend
import com.wireguard.android.util.ModuleLoader
import com.wireguard.android.util.RootShell
import com.wireguard.android.util.ToolsInstaller
import com.wireguard.config.Config
import com.wireguard.crypto.Key
import com.wireguard.crypto.KeyPair
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import java.lang.ref.WeakReference
import co.sentinel.dvpn.domain.features.dvpn.model.KeyPair as DomainKeyPair

class DVPNRepositoryImpl(
    private val context: Context,
    private val configStore: ConfigStore,
    private val tunnelCacheStore: TunnelCacheStore,
    private val userPreferenceStore: UserPreferenceStore,
    private val connectionDurationStore: ConnectionDurationStore,
    private val eventBus: ConnectionEventBus
) : DVPNRepository {
    companion object {
        private val dnsServers = listOfNotNull(
            DnsServer(Dns.HANDSHAKE, "103.196.38.38, 103.196.38.39"),
            DnsServer(Dns.CLOUDFLARE, "1.1.1.1, 1.0.0.1"),
            DnsServer(Dns.GOOGLE, "8.8.8.8, 8.8.4.4")
        )

        val DEFAULT_DNS = dnsServers.first()
    }

    private var backend: Backend? = null
    private lateinit var moduleLoader: ModuleLoader
    private lateinit var rootShell: RootShell
    private lateinit var toolsInstaller: ToolsInstaller


    private var haveLoaded = false

    override suspend fun init(params: InitBackend.InitBackendParams) {
        rootShell = RootShell(context)
        toolsInstaller = ToolsInstaller(context, rootShell)
        moduleLoader = ModuleLoader(context, rootShell, params.userAgent)
        backend = determineBackend(params.alwaysOnCallback)
    }

    private suspend fun determineBackend(alwaysOnCallback: () -> Unit): Backend {
        val isKernelModuleDisabled = userPreferenceStore.disableKernelModule.firstOrNull() ?: false
        val areMultipleTunnelsAllowed = userPreferenceStore.multipleTunnels.firstOrNull() ?: false
        var backend: Backend? = null
        var didStartRootShell = false
        if (!ModuleLoader.isModuleLoaded() && moduleLoader.moduleMightExist()) {
            try {
                rootShell.start()
                didStartRootShell = true
                moduleLoader.loadModule()
            } catch (ignored: Exception) {
            }
        }
        if (!isKernelModuleDisabled && ModuleLoader.isModuleLoaded()) {
            try {
                if (!didStartRootShell)
                    rootShell.start()
                val wgQuickBackend = WgQuickBackend(context, rootShell, toolsInstaller)
                wgQuickBackend.setMultipleTunnels(areMultipleTunnelsAllowed)
                backend = wgQuickBackend
                // todo why are we calling this here?
                userPreferenceStore.multipleTunnels.collect {
                    wgQuickBackend.setMultipleTunnels(it)
                }
            } catch (ignored: Exception) {
            }
        }
        if (backend == null) {
            backend = GoBackend(context)
            GoBackend.setAlwaysOnCallback { alwaysOnCallback() }
        }
        return backend
    }

    private fun addTunnelToList(
        name: String,
        state: Tunnel.State,
        config: Config?
    ) =
        TunnelWrapper(name, state, config).also {
            tunnelCacheStore.add(it)
        }


    override suspend fun getTunnels(): Either<Failure, GetTunnels.Success> {
        return kotlin.runCatching {
            val tunnels = tunnelCacheStore.getTunnelList()
            if (tunnels.isEmpty()) {
                loadTunnels().let { result ->
                    return@let if (result.isLeft) {
                        Either.Left(result.requireLeft())
                    } else {
                        Either.Right(
                            GetTunnels.Success(
                                tunnelCacheStore.getTunnelList()
                                    .map { TunnelToDomainTunnelMapper.map(it) })
                        )
                    }
                }
            } else {
                Either.Right(GetTunnels.Success(tunnels.map {
                    TunnelToDomainTunnelMapper.map(
                        it
                    )
                }))
            }
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)
    }

    override suspend fun getTunnel(tunnelName: String): Either<Failure, GetTunnel.Success> {
        return kotlin.runCatching {
            getTunnels().let {
                if (it.isRight) {
                    val tunnel = it.requireRight().tunnels.firstOrNull { it.name == tunnelName }
                    if (tunnel != null) {
                        tunnel.nodeAddress = userPreferenceStore.nodeAddress.firstOrNull() ?: ""
                        tunnel.subscriptionId =
                            userPreferenceStore.subscriptionId.firstOrNull() ?: 0L
                        tunnel.duration =
                            connectionDurationStore.getConnectionTimestamp(tunnel.subscriptionId)
                        return@let Either.Right(GetTunnel.Success(tunnel))
                    }
                }
                Either.Left(GetTunnel.GetTunnelFailure.TunnelNotFound)
            }
        }
            .onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)
    }

    override suspend fun updateDns(dns: DnsServer): Either<Failure, Success> {
        return kotlin.runCatching {
            userPreferenceStore.setDns(dns)
            val tunnel =
                tunnelCacheStore.getTunnelList().firstOrNull { it.state == Tunnel.State.UP }
            return tunnel?.config?.let { config ->
                ConfigToDomainConfigMapper.map(config).let { domainConfig ->
                    setTunnelConfig(
                        SetTunnelConfig.SetTunnelConfigParams(
                            tunnelName = tunnel.name,
                            config = domainConfig.copy(
                                tunnelInterface = domainConfig.tunnelInterface.copy(
                                    dnsServers = dns.addresses
                                )
                            )
                        )
                    ).let {
                        if (it.isRight) {
                            Either.Right(Success)
                        } else {
                            Either.Left(it.requireLeft())
                        }
                    }
                }
            } ?: Either.Right(Success) // if there is no tunnel, it means we haven't ever connected
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)
    }

    override suspend fun getDefaultDns(): DnsServer =
        userPreferenceStore.dns.firstOrNull() ?: DEFAULT_DNS

    override suspend fun getDnsList(): List<DnsServer> = dnsServers

    @SuppressLint("CheckResult")
    override suspend fun createOrUpdate(
        name: String,
        vpnProfile: VpnProfile,
        keyPair: DomainKeyPair,
        nodeAddress: String,
        subscriptionId: Long
    ): Either<Failure, CreateTunnel.Success> =
        kotlin.runCatching {
            vpnProfile.let {
                when {
                    Tunnel.isNameInvalid(name) -> Either.Left(CreateTunnel.CreateTunnelFailure.InvalidName)
                    else -> {
                        if (tunnelCacheStore.getTunnelList().containsKey(name)) {
                            connectionDurationStore.clearConnectionTimestamp()
                            delete(DeleteTunnel.DeleteTunnelParams(name))
                        }
                        addTunnelToList(
                            name = name,
                            state = Tunnel.State.DOWN,
                            config = configStore.create(
                                name,
                                DomainConfigToConfigMapper.map(
                                    TunnelConfig(
                                        tunnelInterface = TunnelInterface(
                                            excludedApplications = listOf(),
                                            includedApplications = listOf(),
                                            addresses = vpnProfile.address,
                                            dnsServers = userPreferenceStore.dns.firstOrNull()?.addresses
                                                ?: DEFAULT_DNS.addresses,
                                            listenPort = vpnProfile.listenPort,
                                            mtu = "",
                                            privateKey = keyPair.privateKeyBase64,
                                            publicKey = keyPair.publicKeyBase64,
                                        ),
                                        peers = listOf(
                                            TunnelPeer(
                                                allowedIps = "0.0.0.0/0",
                                                endpoint = vpnProfile.peerEndpoint,
                                                persistentKeepAlive = "25",
                                                preSharedKey = null,
                                                publicKey = Key.fromBytes(vpnProfile.peerPubKeyBytes)
                                                    .toBase64()
                                            )
                                        )
                                    )
                                )
                            )
                        ).let {
                            userPreferenceStore.setSubscriptionId(subscriptionId)
                            userPreferenceStore.setNodeAddress(nodeAddress)
                            subscriptionId.let {
                                connectionDurationStore.saveConnectionTimestamp(
                                    subscriptionId,
                                    System.currentTimeMillis()
                                )
                            }
                            Either.Right(CreateTunnel.Success(TunnelToDomainTunnelMapper.map(it)))
                        }
                    }
                }
            }
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)


    override suspend fun create(params: CreateTunnel.CreateTunnelParams): Either<Failure, CreateTunnel.Success> {
        return params.runCatching {
            when {
                Tunnel.isNameInvalid(name) -> Either.Left(CreateTunnel.CreateTunnelFailure.InvalidName)
                tunnelCacheStore.getTunnelList().containsKey(name) -> Either.Left(
                    CreateTunnel.CreateTunnelFailure.NameAlreadyExists
                )

                else -> addTunnelToList(
                    name = name,
                    state = Tunnel.State.DOWN,
                    config = configStore.create(
                        name,
                        DomainConfigToConfigMapper.map(tunnelConfig)
                    )
                ).let {
                    Either.Right(CreateTunnel.Success(TunnelToDomainTunnelMapper.map(it)))
                }
            }
        }.onFailure {
            Timber.e(it)
        }.getOrNull() ?: Either.Left(Failure.AppError)
    }

    override suspend fun delete(params: DeleteTunnel.DeleteTunnelParams) =
        kotlin.runCatching {
            tunnelCacheStore.getTunnelList().let {
                it[params.name] ?: throw IllegalStateException()
            }.let { tunnel ->
                val originalState = tunnel.state
                val wasLastUsed = tunnel == tunnelCacheStore.getLastUsedTunnel()
                // Make sure nothing touches the tunnel.
                if (wasLastUsed) {
                    tunnelCacheStore.updateLastUsedTunnel(null)
                }
                tunnelCacheStore.delete(tunnel)
                var throwable: Throwable? = null
                try {
                    if (originalState == Tunnel.State.UP) {
                        backend?.setState(tunnel, Tunnel.State.DOWN, null)
                    }
                    try {
                        configStore.delete(tunnel.name)
                    } catch (e: Throwable) {
                        if (originalState == Tunnel.State.UP) {
                            backend?.setState(tunnel, Tunnel.State.UP, tunnel.config)
                        }
                        throw e
                    }
                } catch (e: Throwable) {
                    // Failure, put the tunnel back.
                    tunnelCacheStore.add(tunnel)
                    if (wasLastUsed) {
                        tunnelCacheStore.updateLastUsedTunnel(tunnel)
                    }
                    throwable = e
                }
                if (throwable != null)
                    return@runCatching Either.Left(DeleteTunnel.DeleteTunnelFailure.TunnelNotDeleted)

                Either.Right(DeleteTunnel.Success)
            }
        }.onFailure {
            Timber.e(it)
        }.getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun getTunnelConfig(params: GetTunnelConfig.GetTunnelConfigParams) =
        kotlin.runCatching {
            tunnelCacheStore.getTunnelList().firstOrNull { it.name == params.tunnelName }
                ?.let { tunnel ->
                    with(configStore.load(tunnel.name)) {
                        // sync up tunnel config in the store.
                        tunnel.config = this
                        Either.Right(GetTunnelConfig.Success(ConfigToDomainConfigMapper.map(this)))
                    }
                } ?: Either.Left(GetTunnelConfig.GetTunnelConfigFailure.TunnelNotFound)
        }.onFailure {
            Timber.e(it)
        }.getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun loadTunnels(): Either<Failure, LoadTunnels.Success> {
        return kotlin.runCatching {
            userPreferenceStore.lastUsedTunnel.firstOrNull()
                .let { lastUsedTunnel ->
                    val present = configStore.enumerate()
                    val running = backend?.runningTunnelNames
                    for (name in present)
                        addTunnelToList(
                            name = name,
                            state = if (running?.contains(name) == true) Tunnel.State.UP else Tunnel.State.DOWN,
                            config = configStore.load(name)
                        )
                    if (lastUsedTunnel != null) {
                        tunnelCacheStore.updateLastUsedTunnel(
                            tunnelCacheStore.getTunnelList()[lastUsedTunnel]
                        )
                    }
                    haveLoaded = true
                    restoreState(RestoreState.RestoreStateParams(true))
                    Either.Right(LoadTunnels.Success)
                }
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)
    }

    override fun refreshTunnelStates() {
        // todo refactor when needed maybe?
        try {
            val running = backend?.runningTunnelNames ?: return
            for (tunnel in tunnelCacheStore.getTunnelList())
                tunnel.onStateChanged(if (running.contains(tunnel.name)) Tunnel.State.UP else Tunnel.State.DOWN)
        } catch (e: Throwable) {
            Timber.e(e)
        }
    }

    override suspend fun restoreState(params: RestoreState.RestoreStateParams) {
        val restoreOnBoot = userPreferenceStore.restoreOnBoot.firstOrNull() ?: false
        val previouslyRunning = userPreferenceStore.runningTunnels.firstOrNull() ?: setOf()
        if (!haveLoaded || (!params.force && !restoreOnBoot)) {
            return
        }
        if (previouslyRunning.isEmpty()) {
            return
        }
        try {
            tunnelCacheStore.getTunnelList()
                .filter { previouslyRunning.contains(it.name) }
                .map {
                    setTunnelState(it, Tunnel.State.UP)
                }

        } catch (e: Throwable) {
            Timber.e(e)
        }
    }

    override suspend fun saveState() {
        userPreferenceStore.setRunningTunnels(
            tunnelCacheStore.getTunnelList()
                .filter { it.state == Tunnel.State.UP }
                .map { it.name }
                .toSet()
        )
    }

    override suspend fun setTunnelConfig(params: SetTunnelConfig.SetTunnelConfigParams): Either<Failure, SetTunnelConfig.Success> {
        return kotlin.runCatching {
            val name = params.tunnelName
            val config = DomainConfigToConfigMapper.map(params.config)
            return backend?.let {
                val tunnel = tunnelCacheStore.getTunnelList()[name]
                if (tunnel == null) {
                    Either.Left(SetTunnelConfig.SetTunnelConfigFailure.TunnelNotFound)
                } else {
                    it.setState(tunnel, tunnel.state, config)
                    configStore.save(tunnel.name, config)
                    Either.Right(SetTunnelConfig.Success)
                }
            } ?: Either.Left(SetTunnelConfig.SetTunnelConfigFailure.BackendNotInitialized)
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)
    }

    override fun getTunnelDuration(subscriptionId: Long?): Either<Failure, GetTunnelDuration.Success> =
        Either.Right(
            GetTunnelDuration.Success(
                connectionDurationStore.getConnectionTimestamp(
                    subscriptionId
                )
            )
        )

    override suspend fun setTunnelName(params: SetTunnelName.SetTunnelNameParams): Either<Failure, SetTunnelName.Success> {
        return kotlin.runCatching {
            val name = params.newTunnelName
            val tunnel = DomainTunnelToTunnelMapper.map(params.tunnel)
            when {
                Tunnel.isNameInvalid(name) -> Either.Left(SetTunnelName.SetTunnelNameFailure.InvalidName)
                tunnelCacheStore.getTunnelList().containsKey(name) -> Either.Left(
                    SetTunnelName.SetTunnelNameFailure.NameAlreadyExists
                )

                else -> {
                    val originalState = tunnel.state
                    val wasLastUsed = tunnel == tunnelCacheStore.getLastUsedTunnel()
                    // Make sure nothing touches the tunnel.
                    if (wasLastUsed) {
                        tunnelCacheStore.updateLastUsedTunnel(null)
                    }
                    tunnelCacheStore.delete(tunnel)
                    var throwable: Throwable? = null
                    try {
                        if (originalState == Tunnel.State.UP) {
                            backend?.setState(tunnel, Tunnel.State.DOWN, null)
                                ?: throw IllegalStateException()
                        }
                        configStore.rename(tunnel.name, name)
                        tunnel.onNameChanged(name)
                        if (originalState == Tunnel.State.UP) {
                            backend?.setState(tunnel, Tunnel.State.UP, tunnel.config)
                                ?: throw IllegalStateException()
                        }
                    } catch (e: Throwable) {
                        throwable = e
                        // On failure, we don't know what state the tunnel might be in. Fix that.
                        getTunnelState(tunnel)
                    }
                    // Add the tunnel back to the manager, under whatever name it thinks it has.
                    tunnelCacheStore.add(tunnel)
                    if (wasLastUsed)
                        tunnelCacheStore.updateLastUsedTunnel(tunnel)
                    if (throwable != null)
                        return Either.Left(SetTunnelName.SetTunnelNameFailure.SetNameFailed)

                    Either.Right(SetTunnelName.Success)
                }
            }
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)
    }


    override suspend fun setTunnelState(params: SetTunnelState.SetTunnelStateParams) =
        kotlin.runCatching {
            tunnelCacheStore.getTunnelList()[params.tunnelName]?.let { tunnel ->
                setTunnelState(tunnel, DomainStateToStateMapper.map(params.tunnelState))
            } ?: Either.Left(SetTunnelState.SetTunnelStateFailure.TunnelNotFound)
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)

    private suspend fun setTunnelState(
        tunnel: TunnelWrapper,
        state: Tunnel.State
    ) = kotlin.runCatching {
        configStore.load(tunnel.name).let {
            var newState = state
            var throwable: Throwable? = null
            try {
                backend?.let {
                    newState = it.setState(tunnel, state, tunnel.config)
                } ?: throw IllegalStateException()

                if (newState == Tunnel.State.UP) {
                    tunnelCacheStore.updateLastUsedTunnel(tunnel)
                }

                if (newState == Tunnel.State.DOWN) {
                    connectionDurationStore.clearConnectionTimestamp()
                }
            } catch (e: Throwable) {
                throwable = e
            }

            tunnel.onStateChanged(newState)

            if (newState != Tunnel.State.TOGGLE) {
                // won't emmit the toggle event
                eventBus.emitEvent(ConnectionEvent.ConnectionStateChanged(newState == Tunnel.State.UP))
            }

            saveState()

            if (throwable != null) {
                Either.Left(SetTunnelState.SetTunnelStateFailure.SetStateFailed)
            } else {
                Either.Right(SetTunnelState.Success(TunnelToDomainTunnelMapper.map(tunnel)))
            }
        }
    }.onFailure { Timber.e(it) }
        .getOrNull() ?: Either.Left(Failure.AppError)

    private fun getTunnelState(tunnel: TunnelWrapper) {
        backend?.let {
            tunnel.onStateChanged(it.getState(tunnel))
        }
    }

    override suspend fun getTunnelStatistics(params: GetTunnelStatistics.GetTunnelStatisticsParams) =
        kotlin.runCatching {
            val tunnelName = params.tunnelName
            val tunnel = tunnelCacheStore.getTunnelList()[tunnelName]
            if (tunnel == null) {
                Either.Left(GetTunnelStatistics.GetTunnelStatisticsFailure.TunnelNotFound)
            } else {
                tunnel.onStatisticsChanged(
                    backend?.getStatistics(tunnel)
                        ?: return Either.Left(GetTunnelStatistics.GetTunnelStatisticsFailure.BackendNotInitialized)
                )
                Either.Right(GetTunnelStatistics.Success)
            }
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)


    override fun generateKeyPair() = with(KeyPair()) {
        DomainKeyPair(
            privateKeyBase64 = privateKey.toBase64(),
            publicKeyBase64 = publicKey.toBase64()
        )
    }

    override fun generateKeyPair(privateKey: String): DomainKeyPair {
        return with(KeyPair(Key.fromBase64(privateKey))) {
            DomainKeyPair(
                privateKeyBase64 = privateKey,
                publicKeyBase64 = publicKey.toBase64(),
            )
        }
    }

    override fun getVpnServiceIntent(activity: WeakReference<Context>) = activity.get()?.let {
        if (backend is GoBackend) {
            Either.Right(GoBackend.VpnService.prepare(it))
        } else {
            Either.Left(GetVpnServiceIntent.GetVpnServiceIntentFailure.NotGoBackendError)
        }
    } ?: Either.Left(Failure.AppError)

}