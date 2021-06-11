package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.PokemonTypeRepository
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import ca.landonjw.kotlinmon.common.pokemon.data.species.DefaultPokemonSpeciesRepository
import ca.landonjw.kotlinmon.common.pokemon.data.species.loader.PokemonSpeciesLoader
import ca.landonjw.kotlinmon.common.pokemon.data.species.loader.PokemonTypeAdapter
import ca.landonjw.kotlinmon.common.pokemon.data.species.type.DefaultPokemonTypeRepository
import ca.landonjw.kotlinmon.common.pokemon.entity.DefaultPokemonEntity
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntityClient
import net.minecraft.network.datasync.EntityDataManager
import org.kodein.di.*

object PokemonModule {

    val bindings = DI.Module(name = "Pokemon") {
        bind<PokemonSpeciesRepository> { singleton { DefaultPokemonSpeciesRepository(instance(), instance()) } }
        bind<PokemonTypeRepository> { singleton { DefaultPokemonTypeRepository(instance()) } }
        bindFactory<PokemonEntityFactoryParams, PokemonEntity> { params -> DefaultPokemonEntity(params.world, params.pokemon, instance(), factory()) }
        bindFactory<EntityDataManager, PokemonEntityClient> { dataManager -> PokemonEntityClient(dataManager, instance(), instance()) }
        bind<PokemonTypeAdapter> { singleton { PokemonTypeAdapter(instance()) } }
        bind<PokemonSpeciesLoader> { singleton { PokemonSpeciesLoader(instance()) } }
        bind<PokemonFactory> { singleton { DefaultPokemonFactory(factory()) } }
    }

}