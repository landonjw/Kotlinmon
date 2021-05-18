package ca.landonjw.kotlinmon.client

import ca.landonjw.kotlinmon.client.render.models.CustomModelDecorator
import ca.landonjw.kotlinmon.client.render.party.PokemonPartyOverlay
import ca.landonjw.kotlinmon.client.render.pokeball.PokeBallRenderer
import ca.landonjw.kotlinmon.client.render.pokemon.PokemonRenderer
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.PokeBallEntity
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.model.IBakedModel
import net.minecraft.client.renderer.model.ModelResourceLocation
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

object ClientInitialization {

    private fun registerRenderers() {
        registerEntityRenderer(EntityRegistry.POKEMON) { PokemonRenderer(it) }
        registerEntityRenderer(EntityRegistry.EMPTY_POKEBALL) { PokeBallRenderer(it) }
        registerEntityRenderer(EntityRegistry.OCCUPIED_POKEBALL) { PokeBallRenderer(it) }
    }

    private fun <T : Entity> registerEntityRenderer(
        registry: RegistryObject<EntityType<T>>,
        renderer: (EntityRendererManager) -> EntityRenderer<T>
    ) {
        RenderingRegistry.registerEntityRenderingHandler(registry.get(), renderer)
    }

    fun onClientSetup(event: FMLClientSetupEvent) {
        registerRenderers()
        MinecraftForge.EVENT_BUS.register(PokemonPartyOverlay())
    }

    /* TODO: I KNOW there is a better way to do this, but it's going to end up in a rabbit hole
     * For those courageous, I believe the correct avenue would be to implement a custom model loader.
     * See: https://discord.com/channels/313125603924639766/454376090362970122/797892085998813234
     * in The Forge Project discord (https://discord.gg/UvedJ9m) for a potential clue.
     *
     * - landonjw
     */
    @SubscribeEvent
    fun onBakeModel(event: ModelBakeEvent) {
        val registry = event.modelRegistry as MutableMap<ResourceLocation, IBakedModel>
        for (location in registry.keys) {
            if (location !is ModelResourceLocation) continue
            if (!location.path.contains("pokeball")) continue
            val model = registry[location]
            registry[location] = CustomModelDecorator(model!!)
        }
    }

}