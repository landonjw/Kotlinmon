package ca.landonjw.kotlinmon.client

import ca.landonjw.kotlinmon.client.render.pokeball.RenderPokeball
import ca.landonjw.kotlinmon.client.render.pokemon.RenderPokemon
import ca.landonjw.kotlinmon.common.EntityRegistry
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

object ClientInitialization {

    private fun registerRenderers() {
        registerEntityRenderer(EntityRegistry.POKEMON) { RenderPokemon(it) }
        registerEntityRenderer(EntityRegistry.POKEBALL) { RenderPokeball(it) }
    }

    private fun <T : Entity> registerEntityRenderer(
        registry: RegistryObject<EntityType<T>>,
        renderer: (EntityRendererManager) -> EntityRenderer<T>
    ) {
        RenderingRegistry.registerEntityRenderingHandler(registry.get(), renderer)
    }

    fun onClientSetup(event: FMLClientSetupEvent) {
        registerRenderers()
    }

}