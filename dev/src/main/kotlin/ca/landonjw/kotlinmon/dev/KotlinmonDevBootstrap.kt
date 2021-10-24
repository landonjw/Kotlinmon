package ca.landonjw.kotlinmon.dev

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.ClientModule
import ca.landonjw.kotlinmon.common.CommonModule
import ca.landonjw.kotlinmon.server.ServerModule
import net.minecraftforge.fml.common.Mod
import org.kodein.di.description
import org.kodein.di.instance
import org.kodein.di.newInstance

@Mod(Kotlinmon.MOD_ID)
class KotlinmonDevBootstrap {

    init {
        Kotlinmon.initialize(listOf(CommonModule(), ServerModule(), ClientModule()))
        println("""
            ${Kotlinmon.DI.container.tree.bindings.description(false, 0)}
        """.trimIndent())
        val initializer by Kotlinmon.DI.newInstance { KotlinmonDevInitialization(instance(), instance(), instance(), instance()) }
        initializer.initialize()
    }

}