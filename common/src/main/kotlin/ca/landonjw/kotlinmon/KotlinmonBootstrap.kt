package ca.landonjw.kotlinmon

import ca.landonjw.kotlinmon.client.ClientModule
import ca.landonjw.kotlinmon.common.CommonModule
import ca.landonjw.kotlinmon.server.ServerModule
import net.minecraftforge.fml.common.Mod
import org.kodein.di.instance
import org.kodein.di.newInstance

@Mod(Kotlinmon.MOD_ID)
class KotlinmonBootstrap {

    init {
        Kotlinmon.initialize(listOf(CommonModule.bindings, ServerModule.bindings, ClientModule.bindings))
        val initializer by Kotlinmon.DI.newInstance { KotlinmonInitialization(instance(), instance(), instance(), instance()) }
        initializer.initialize()
    }

}