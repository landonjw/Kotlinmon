package ca.landonjw.kotlinmon

import net.minecraftforge.eventbus.api.BusBuilder
import net.minecraftforge.eventbus.api.IEventBus
import org.kodein.di.DI as KodeinDI

object Kotlinmon {

    const val MOD_ID = "kotlinmon"
    const val MOD_NAME = "Kotlinmon"

    val EVENT_BUS: IEventBus = BusBuilder.builder().startShutdown().build()

    lateinit var DI: KodeinDI
        private set

    fun initialize(modules: List<KodeinDI.Module>) {
        this.DI = KodeinDI {
            importAll(modules, allowOverride = true)
        }
    }

}