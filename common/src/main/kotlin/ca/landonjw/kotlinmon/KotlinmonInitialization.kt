package ca.landonjw.kotlinmon

import ca.landonjw.kotlinmon.client.ClientInitialization
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.ItemRegistry
import ca.landonjw.kotlinmon.server.ServerInitialization
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

class KotlinmonInitialization(
    private val serverInitialization: ServerInitialization,
    private val clientInitialization: ClientInitialization,
    private val itemRegistry: ItemRegistry,
    private val entityRegistry: EntityRegistry
) {

    fun initialize() {
        // Setup server initialization hooks
        MinecraftForge.EVENT_BUS.register(serverInitialization)

        // Setup client initialization hooks
        DistExecutor.safeRunWhenOn(Dist.CLIENT) {
            DistExecutor.SafeRunnable {
                FMLJavaModLoadingContext.get().modEventBus.addListener { event: ModelBakeEvent ->
                    clientInitialization.onBakeModel(event)
                }
                FMLJavaModLoadingContext.get().modEventBus.addListener { event: FMLClientSetupEvent ->
                    clientInitialization.onClientSetup(event)
                }
            }
        }

        // Register entities and items
        entityRegistry.register()
        itemRegistry.register()
    }

}