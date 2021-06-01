package ca.landonjw.kotlinmon

import ca.landonjw.kotlinmon.client.ClientInitialization
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.ItemRegistry
import ca.landonjw.kotlinmon.server.ServerInitialization
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

@Mod("kotlinmon")
class Kotlinmon {

    init {
        initialize()

        EntityRegistry.register()
        ItemRegistry.register()
    }

    private fun initialize() {
        // Initialize dependency injection
        KotlinmonDI.initialize()

        // Setup server initialization hooks
        MinecraftForge.EVENT_BUS.register(ServerInitialization)

        // Setup client initialization hooks
        DistExecutor.safeRunWhenOn(Dist.CLIENT) {
            DistExecutor.SafeRunnable {
                FMLJavaModLoadingContext.get().modEventBus.addListener { event: ModelBakeEvent ->
                    ClientInitialization.onBakeModel(event)
                }
                FMLJavaModLoadingContext.get().modEventBus.addListener { event: FMLClientSetupEvent ->
                    ClientInitialization.onClientSetup(event)
                }
            }
        }
    }

    companion object {
        const val MODID = "kotlinmon"
    }

}
