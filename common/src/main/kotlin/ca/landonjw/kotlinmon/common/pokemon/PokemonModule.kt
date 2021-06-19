package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.PokemonTypeRepository
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import ca.landonjw.kotlinmon.common.EntityFactoryParams
import ca.landonjw.kotlinmon.common.pokemon.data.species.DefaultPokemonSpeciesRepository
import ca.landonjw.kotlinmon.common.pokemon.data.species.loader.PokemonSpeciesLoader
import ca.landonjw.kotlinmon.common.pokemon.data.species.loader.PokemonTypeAdapter
import ca.landonjw.kotlinmon.common.pokemon.data.species.type.DefaultPokemonTypeRepository
import ca.landonjw.kotlinmon.common.pokemon.entity.DefaultPokemonEntity
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntityClient
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonDTOEncoder
import net.minecraft.entity.EntityType
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.world.World
import org.kodein.di.*

object PokemonModule {

    operator fun invoke() = DI.Module(name = "Pokemon") {
        bind<PokemonSpeciesRepository>() with singleton { DefaultPokemonSpeciesRepository(instance(), instance()) }
        bind<PokemonTypeRepository>() with singleton { DefaultPokemonTypeRepository(instance()) }
        bind<PokemonEntity>() with factory { params: PokemonEntityFactoryParams -> DefaultPokemonEntity(params.type, params.world, params.pokemon, factory()) }
        bind<PokemonEntityClient>() with factory { dataManager: EntityDataManager -> PokemonEntityClient(dataManager, instance()) }
        bind<PokemonTypeAdapter>() with singleton { PokemonTypeAdapter(instance()) }
        bind<PokemonSpeciesLoader>() with singleton { PokemonSpeciesLoader(instance()) }
        bind<PokemonFactory>() with singleton { DefaultPokemonFactory(instance(), factory()) }
        bind<PokemonDTOEncoder>() with singleton { PokemonDTOEncoder() }
        bind<DefaultPokemonEntity>() with factory { params: EntityFactoryParams<DefaultPokemonEntity> -> DefaultPokemonEntity(params.type, params.world, null, factory()) }
    }

}

data class PokemonEntityFactoryParams(
    val type: EntityType<out DefaultPokemonEntity>,
    val pokemon: Pokemon,
    val world: World
)