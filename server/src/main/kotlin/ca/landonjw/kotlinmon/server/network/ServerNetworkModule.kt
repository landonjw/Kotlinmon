package ca.landonjw.kotlinmon.server.network

import ca.landonjw.kotlinmon.server.network.party.SynchronizePartyRequestHandler
import ca.landonjw.kotlinmon.server.network.party.ThrowPartyPokemonHandler
import org.kodein.di.*

object ServerNetworkModule {

    operator fun invoke() = DI.Module(name = "Server Network") {
        bind<SynchronizePartyRequestHandler>() with singleton { SynchronizePartyRequestHandler(instance(), instance(), factory()) }
        bind<ThrowPartyPokemonHandler>() with singleton { ThrowPartyPokemonHandler(instance(), instance()) }
    }

}