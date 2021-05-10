package ca.landonjw.kotlinmon

import ca.landonjw.kotlinmon.client.ClientInitialization
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.ItemRegistry
import ca.landonjw.kotlinmon.server.ServerInitialization
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

@Mod("kotlinmon")
class Kotlinmon {

    companion object {
        const val MODID = "kotlinmon"
    }

    init {
        initialize()

        EntityRegistry.register()
        ItemRegistry.register()
    }

    private fun initialize() {
        MinecraftForge.EVENT_BUS.register(ServerInitialization)
        FMLJavaModLoadingContext.get().modEventBus.addListener { event: ModelBakeEvent ->
            ClientInitialization.onBakeModel(event)
        }
        FMLJavaModLoadingContext.get().modEventBus.addListener { event: FMLClientSetupEvent ->
            ClientInitialization.onClientSetup(event)
        }
    }

}