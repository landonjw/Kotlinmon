package ca.landonjw.kotlinmon.client

import ca.landonjw.kotlinmon.client.keybindings.KeyBindingModule
import ca.landonjw.kotlinmon.client.network.ClientNetworkModule
import ca.landonjw.kotlinmon.client.party.ClientPartyStorage
import ca.landonjw.kotlinmon.client.party.ClientPartySynchronizer
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonDTODecoder
import ca.landonjw.kotlinmon.client.render.models.smd.repository.AsyncModelRepository
import ca.landonjw.kotlinmon.client.render.models.smd.repository.ModelRepository
import ca.landonjw.kotlinmon.client.render.models.smd.repository.SyncModelRepository
import ca.landonjw.kotlinmon.client.render.party.PokemonPartyOverlay
import ca.landonjw.kotlinmon.client.render.pokeball.PokeBallItemRenderer
import ca.landonjw.kotlinmon.client.render.pokeball.PokeBallRenderer
import ca.landonjw.kotlinmon.client.render.pokemon.PokemonRenderer
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultEmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultOccupiedPokeBallEntity
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer
import org.kodein.di.*

object ClientModule {

    operator fun invoke() = DI.Module(name = "Client") {
        import(KeyBindingModule())
        import(ClientNetworkModule())

        bind<ClientInitialization>() with eagerSingleton { ClientInitialization(instance(), factory(), factory(), factory(), instance(), instance(), instance(), instance(), instance()) }
        bind<PokemonRenderer>() with factory { manager: EntityRendererManager -> PokemonRenderer(manager, instance()) }
        bind<PokeBallRenderer<DefaultEmptyPokeBallEntity>>() with factory { manager: EntityRendererManager -> PokeBallRenderer(manager, instance(tag = "async"), instance()) }
        bind<PokeBallRenderer<DefaultOccupiedPokeBallEntity>>() with factory { manager: EntityRendererManager -> PokeBallRenderer(manager, instance(tag = "async"), instance()) }
        bind<PokeBallItemRenderer>() with singleton { PokeBallItemRenderer(instance(tag = "async"), instance()) }
        bind<ItemStackTileEntityRenderer>("pokeball") with singleton { instance<PokeBallItemRenderer>() }
        bind<PokemonPartyOverlay>() with singleton { PokemonPartyOverlay(instance()) }
        bind<ModelRepository>("async") with singleton { AsyncModelRepository() }
        bind<ModelRepository>("sync") with singleton { SyncModelRepository() }
        bind<ClientPartySynchronizer>() with singleton { ClientPartySynchronizer(instance(), instance()) }
        bind<ClientPartyStorage>() with singleton { ClientPartyStorage() }
        bind<PokemonDTODecoder>() with singleton { PokemonDTODecoder(instance()) }
        bind<SmdModelRenderer>() with singleton { SmdModelRenderer() }
    }

}