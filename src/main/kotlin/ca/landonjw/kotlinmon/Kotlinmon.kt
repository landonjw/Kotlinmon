package ca.landonjw.kotlinmon

import ca.landonjw.kotlinmon.common.blocks.KotlinmonBlocks
import ca.landonjw.kotlinmon.server.command.CreatePokemon
import ca.landonjw.kotlinmon.common.init.EntityRegistry
import ca.landonjw.kotlinmon.common.init.ItemRegistry
import ca.landonjw.kotlinmon.common.pokeball.RenderPokeball
import ca.landonjw.kotlinmon.common.pokemon.RenderPokemon
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.block.Block
import net.minecraft.command.Commands
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.server.FMLServerStartingEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager

@Mod("kotlinmon")
class Kotlinmon {

    companion object {
        const val MODID = "kotlinmon"
        val LOGGER = LogManager.getLogger()
    }

    init {
        registerListeners()

        KotlinmonBlocks.register()
        EntityRegistry.register()
        ItemRegistry.register()

        FMLJavaModLoadingContext.get().modEventBus.addListener { event: FMLClientSetupEvent ->
            clientInit(event)
        }
    }

    fun clientInit(event: FMLClientSetupEvent) {
        LOGGER.info("Setting up client")
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.POKEMON.get()) { manager ->
            RenderPokemon(manager)
        }
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.POKEBALL.get()) { manager ->
            RenderPokeball(manager)
        }
    }

    fun registerListeners() {
        FMLJavaModLoadingContext.get().modEventBus.addListener { event: FMLCommonSetupEvent ->
            setup(event)
        }
        MinecraftForge.EVENT_BUS.register(this)
    }

    fun setup(event: FMLCommonSetupEvent) {
        LOGGER.info("Setting up common")
    }

    @SubscribeEvent
    fun serverStarting(event: FMLServerStartingEvent) {
        LOGGER.info("Server starting")
    }

    @SubscribeEvent
    fun onBlockRegistry(event: RegistryEvent.Register<Block>) {
        LOGGER.info("Registering block")
    }

    @SubscribeEvent
    fun onCommandRegistration(event: RegisterCommandsEvent) {
        val test = Commands.literal("pokecreate")
            .then(
                Commands.argument("species", StringArgumentType.word())
                    .executes(CreatePokemon())
            )
            .requires { it.hasPermissionLevel(0) }
            .executes {
                println("No argument supplied")
                return@executes 0
            }
        event.dispatcher.register(test)
    }

}