package ca.landonjw.kotlinmon.client

import ca.landonjw.kotlinmon.client.render.pokeball.CompressingItemRenderer
import ca.landonjw.kotlinmon.client.render.pokeball.PokeBallRenderer
import ca.landonjw.kotlinmon.client.render.pokemon.PokemonRenderer
import ca.landonjw.kotlinmon.common.EntityRegistry
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

object ClientInitialization {

    private fun registerRenderers() {
        registerEntityRenderer(EntityRegistry.POKEMON) { PokemonRenderer(it) }
        registerEntityRenderer(EntityRegistry.POKEBALL) { PokeBallRenderer(it) }
        RenderingRegistry.registerEntityRenderingHandler(EntityType.ITEM) {
            CompressingItemRenderer(it, Minecraft.getInstance().itemRenderer)
        }
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