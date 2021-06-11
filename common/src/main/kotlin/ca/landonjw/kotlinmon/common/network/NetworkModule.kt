package ca.landonjw.kotlinmon.common.network

import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

object NetworkModule {

    val bindings = DI.Module(name = "Network") {
        bind<KotlinmonNetworkChannel>() with singleton { SimpleChannelWrapper() }
    }

}