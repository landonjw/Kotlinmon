package ca.landonjw.kotlinmon.client

import ca.landonjw.kotlinmon.client.keybindings.KeyBindingModule
import ca.landonjw.kotlinmon.client.party.ClientPartyStorage
import ca.landonjw.kotlinmon.client.party.ClientPartySynchronizer
import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonDecoder
import ca.landonjw.kotlinmon.client.render.models.smd.repository.AsyncModelRepository
import ca.landonjw.kotlinmon.client.render.models.smd.repository.ModelRepository
import ca.landonjw.kotlinmon.client.render.models.smd.repository.SyncModelRepository
import ca.landonjw.kotlinmon.client.render.party.PokemonPartyOverlay
import ca.landonjw.kotlinmon.client.render.pokeball.PokeBallItemRenderer
import ca.landonjw.kotlinmon.client.render.pokeball.PokeBallRenderer
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultEmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultOccupiedPokeBallEntity
import net.minecraft.client.renderer.entity.EntityRendererManager
import org.kodein.di.*

object ClientModule {

    val bindings = DI.Module(name = "Client") {
        import(KeyBindingModule.bindings)
        bind<ClientInitialization>() with singleton { ClientInitialization(instance(), factory(), factory(), instance(), instance(), instance()) }
        bind<PokeBallRenderer<DefaultEmptyPokeBallEntity>>() with factory { manager: EntityRendererManager -> PokeBallRenderer(manager, instance(tag = "async")) }
        bind<PokeBallRenderer<DefaultOccupiedPokeBallEntity>>() with factory { manager: EntityRendererManager -> PokeBallRenderer(manager, instance(tag = "async")) }
        bind<PokeBallItemRenderer>() with singleton { PokeBallItemRenderer(instance(tag = "async")) }
        bind<PokemonPartyOverlay>() with singleton { PokemonPartyOverlay(instance()) }
        bind<ModelRepository>("async") with singleton { AsyncModelRepository() }
        bind<ModelRepository>("sync") with singleton { SyncModelRepository() }
        bind<ClientPartySynchronizer>() with singleton { ClientPartySynchronizer(instance(), instance()) }
        bind<ClientPartyStorage>() with singleton { ClientPartyStorage() }
        bind<ClientPokemonDecoder>() with singleton { ClientPokemonDecoder(instance()) }
    }

}