package co.sentinel.dvpn.domain.core.di

import co.sentinel.dvpn.domain.features.dvpn.CreateTunnel
import co.sentinel.dvpn.domain.features.dvpn.DeleteTunnel
import co.sentinel.dvpn.domain.features.dvpn.GenerateKeyPair
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
import co.sentinel.dvpn.domain.features.dvpn.tasks.GetDns
import co.sentinel.dvpn.domain.features.dvpn.tasks.GetDnsList
import co.sentinel.dvpn.domain.features.dvpn.tasks.PutDns
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.ConnectionEventBus
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.DeleteConfiguration
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.DeleteConnection
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.DeleteSessions
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.GetConnection
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.PostConnection
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.PostConnectionWithCredentials
import co.sentinel.dvpn.domain.features.hub.tasks.DeleteSubscription
import co.sentinel.dvpn.domain.features.hub.tasks.GetQuota
import co.sentinel.dvpn.domain.features.hub.tasks.GetSubscriptions
import co.sentinel.dvpn.domain.features.hub.tasks.PostSubscription
import co.sentinel.dvpn.domain.features.purchase.tasks.GetOfferings
import co.sentinel.dvpn.domain.features.purchase.tasks.PostPurchase
import co.sentinel.dvpn.domain.features.registry.tasks.DeleteRegistry
import co.sentinel.dvpn.domain.features.registry.tasks.GetRegistry
import co.sentinel.dvpn.domain.features.registry.tasks.PostRegistry
import co.sentinel.dvpn.domain.features.solar.tasks.GetContinents
import co.sentinel.dvpn.domain.features.solar.tasks.GetCountries
import co.sentinel.dvpn.domain.features.solar.tasks.GetCountriesByContinent
import co.sentinel.dvpn.domain.features.solar.tasks.GetNodes
import co.sentinel.dvpn.domain.features.solar.tasks.GetNodesByAddresses
import co.sentinel.dvpn.domain.features.wallet.tasks.DeleteWallet
import co.sentinel.dvpn.domain.features.wallet.tasks.GetWallet
import co.sentinel.dvpn.domain.features.wallet.tasks.PostWallet
import co.sentinel.dvpn.domain.features.wallet.tasks.PutWallet
import org.koin.dsl.module

val domainModule = module {

    // solar
    single { GetContinents(get()) }
    single { GetCountries(get()) }
    single { GetCountriesByContinent(get()) }
    single { GetNodes(get()) }
    single { GetNodesByAddresses(get()) }

    // tunnel
    single { GetDnsList(get()) }
    single { GetDns(get()) }
    single { PutDns(get()) }
    single { DeleteConnection(get()) }
    single { DeleteSessions(get(), get(), get()) }
    single { DeleteConfiguration(get()) }
    single { PostConnection(get(), get(), get(), get()) }
    single { PostConnectionWithCredentials(get(), get()) }
    single { GetConnection(get()) }
    single { CreateTunnel(get()) }
    single { DeleteTunnel(get()) }
    single { GetTunnelConfig(get()) }
    single { InitBackend(get()) }
    single { RestoreState(get()) }
    single { GetTunnels(get()) }
    single { GetTunnel(get()) }
    single { GetTunnelStatistics(get()) }
    single { GetTunnelDuration(get()) }
    single { LoadTunnels(get()) }
    single { SetTunnelConfig(get()) }
    single { SetTunnelName(get()) }
    single { SetTunnelState(get()) }
    single { GenerateKeyPair(get()) }
    single { GetVpnServiceIntent(get()) }

    // wallet
    single { GetWallet(get()) }
    single { DeleteWallet(get()) }
    single { PutWallet(get()) }
    single { PostWallet(get()) }

    // hub
    single { GetSubscriptions(get()) }
    single { GetQuota(get()) }
    single { PostSubscription(get(), get()) }
    single { DeleteSubscription(get(), get()) }

    // purchases
    single { GetOfferings(get()) }
    single { PostPurchase(get(), get()) }

    // registry
    single { DeleteRegistry(get()) }
    single { GetRegistry(get()) }
    single { PostRegistry(get()) }

    single { ConnectionEventBus() }

}