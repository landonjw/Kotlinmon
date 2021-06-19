package ca.landonjw.kotlinmon.client

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.keybindings.KeyBindingController
import ca.landonjw.kotlinmon.client.party.ClientPartySynchronizer
import ca.landonjw.kotlinmon.client.render.models.CustomModelDecorator
import ca.landonjw.kotlinmon.client.render.party.PokemonPartyOverlay
import ca.landonjw.kotlinmon.client.render.pokeball.PokeBallRenderer
import ca.landonjw.kotlinmon.client.render.pokemon.PokemonRenderer
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.network.PacketHandlerRegistrationEvent
import ca.landonjw.kotlinmon.client.network.storage.party.UpdatePartyHandler
import ca.landonjw.kotlinmon.client.network.storage.party.UpdatePartySlotHandler
import ca.landonjw.kotlinmon.common.network.packets.party.UpdateParty
import ca.landonjw.kotlinmon.common.network.packets.party.UpdatePartySlot
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultEmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultOccupiedPokeBallEntity
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

class ClientInitialization(
    private val entityRegistry: EntityRegistry,
    private val pokemonRendererFactory: (EntityRendererManager) -> PokemonRenderer,
    private val emptyPokeBallRendererFactory: (EntityRendererManager) -> PokeBallRenderer<DefaultEmptyPokeBallEntity>,
    private val occupiedPokeBallRendererFactory: (EntityRendererManager) -> PokeBallRenderer<DefaultOccupiedPokeBallEntity>,
    private val pokemonPartyOverlay: PokemonPartyOverlay,
    private val clientPartySynchronizer: ClientPartySynchronizer,
    private val keyBindingController: KeyBindingController,
    private val updatePartyHandler: UpdatePartyHandler,
    private val updatePartySlotHandler: UpdatePartySlotHandler
) {

    init {
        // TODO: Remove on module split
        Kotlinmon.EVENT_BUS.register(this)
    }

    private fun registerRenderers() {
        registerEntityRenderer(entityRegistry.POKEMON, pokemonRendererFactory)
        registerEntityRenderer(entityRegistry.EMPTY_POKEBALL, emptyPokeBallRendererFactory)
        registerEntityRenderer(entityRegistry.OCCUPIED_POKEBALL, occupiedPokeBallRendererFactory)
    }

    private fun <T : Entity> registerEntityRenderer(
        registry: RegistryObject<EntityType<T>>,
        renderer: (EntityRendererManager) -> EntityRenderer<T>
    ) {
        RenderingRegistry.registerEntityRenderingHandler(registry.get(), renderer)
    }

    fun onClientSetup(event: FMLClientSetupEvent) {
        registerRenderers()
        MinecraftForge.EVENT_BUS.register(pokemonPartyOverlay)
        MinecraftForge.EVENT_BUS.register(clientPartySynchronizer)
        keyBindingController.registerBindings()
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

    @SubscribeEvent
    fun onPacketRegistration(event: PacketHandlerRegistrationEvent) {
        event.registerClientPacketHandler(UpdateParty::class.java, updatePartyHandler)
        event.registerClientPacketHandler(UpdatePartySlot::class.java, updatePartySlotHandler)
    }

}