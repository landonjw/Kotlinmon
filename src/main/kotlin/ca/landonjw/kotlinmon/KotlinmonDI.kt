package ca.landonjw.kotlinmon

import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRepository
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.PokemonTypeRepository
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import ca.landonjw.kotlinmon.client.render.models.smd.repository.AsyncModelRepository
import ca.landonjw.kotlinmon.client.render.models.smd.repository.ModelRepository
import ca.landonjw.kotlinmon.client.render.models.smd.repository.SyncModelRepository
import ca.landonjw.kotlinmon.common.pokeball.DefaultPokeBallFactory
import ca.landonjw.kotlinmon.common.pokeball.DefaultPokeBallRepository
import ca.landonjw.kotlinmon.common.pokemon.DefaultPokemonFactory
import ca.landonjw.kotlinmon.common.pokemon.data.species.DefaultPokemonSpeciesRepository
import ca.landonjw.kotlinmon.common.pokemon.data.species.type.DefaultPokemonTypeRepository
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.EventBus
import net.minecraftforge.eventbus.api.BusBuilder
import net.minecraftforge.eventbus.api.IEventBus
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.kodein.di.*

object KotlinmonDI {

    val container: DI

    init {
        container = DI {
            // General
            bind<IEventBus>(tag = Kotlinmon.MODID) { singleton { EventBus(BusBuilder.builder()) } }
            bind<IEventBus>(tag = "forge") { singleton { MinecraftForge.EVENT_BUS } }
            bind<Logger> { singleton { LogManager.getLogger(Kotlinmon.MODID) } }

            // Models
            bind<ModelRepository>(tag = "async") { singleton { AsyncModelRepository() } }
            bind<ModelRepository>(tag = "sync") { singleton { SyncModelRepository() } }
            bind { singleton { SmdModelRenderer() } }

            // Pokemon
            bind<PokemonFactory> { singleton { DefaultPokemonFactory() } }
            bind<PokemonSpeciesRepository> { singleton { DefaultPokemonSpeciesRepository() } }
            bind<PokemonTypeRepository> { singleton { DefaultPokemonTypeRepository() } }

            // Poke Balls
            bind<PokeBallFactory> { singleton { DefaultPokeBallFactory() } }
            bind<PokeBallRepository> { singleton { DefaultPokeBallRepository() } }
        }
    }

    inline fun <reified T> inject(tag: Any? = null): DIProperty<T> = container.instance(tag)

}