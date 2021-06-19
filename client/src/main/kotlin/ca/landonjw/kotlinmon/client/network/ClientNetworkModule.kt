package ca.landonjw.kotlinmon.client.network

import ca.landonjw.kotlinmon.client.network.storage.party.UpdatePartyHandler
import ca.landonjw.kotlinmon.client.network.storage.party.UpdatePartySlotHandler
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

object ClientNetworkModule {

    operator fun invoke() = DI.Module(name = "Client Network") {
        bind<UpdatePartyHandler>() with singleton { UpdatePartyHandler(instance()) }
        bind<UpdatePartySlotHandler>() with singleton { UpdatePartySlotHandler(instance()) }
    }

}