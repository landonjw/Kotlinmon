package ca.landonjw.kotlinmon.server

import ca.landonjw.kotlinmon.server.command.CommandModule
import ca.landonjw.kotlinmon.server.storage.StorageModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

object ServerModule {

    val bindings = DI.Module(name = "Server") {
        import(StorageModule.bindings)
        import(CommandModule.bindings)
        bind<ServerInitialization>() with singleton { ServerInitialization(instance(), instance()) }
    }

}