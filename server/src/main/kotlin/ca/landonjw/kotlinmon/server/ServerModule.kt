package ca.landonjw.kotlinmon.server

import ca.landonjw.kotlinmon.server.command.CommandModule
import ca.landonjw.kotlinmon.server.network.ServerNetworkModule
import ca.landonjw.kotlinmon.server.storage.StorageModule
import org.kodein.di.*

object ServerModule {

    operator fun invoke() = DI.Module(name = "Server") {
        import(StorageModule())
        import(CommandModule())
        import(ServerNetworkModule())

        bind<ServerInitialization>() with eagerSingleton { ServerInitialization(instance(), instance(), instance(), instance()) }
    }

}