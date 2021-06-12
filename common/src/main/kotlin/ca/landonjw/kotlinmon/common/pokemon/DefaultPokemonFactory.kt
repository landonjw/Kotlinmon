package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.pokemon.entity.DefaultPokemonEntity
import net.minecraft.entity.EntityType
import net.minecraft.world.World

class DefaultPokemonFactory(
    private val entityRegistry: EntityRegistry,
    private val pokemonEntityFactory: (PokemonEntityFactoryParams) -> PokemonEntity
): PokemonFactory {

    override fun create(species: PokemonSpecies, form: PokemonForm?): Pokemon {
        return if (form == null) DefaultPokemon(species) else DefaultPokemon(species, form)
    }

    override fun createEntity(pokemon: Pokemon, world: World): PokemonEntity {
        // TODO: Validate if pokemon already has an entity associated with it
        val params = PokemonEntityFactoryParams(entityRegistry.POKEMON.get(), pokemon, world)
        return pokemonEntityFactory(params)
    }

}

data class PokemonEntityFactoryParams(
    val type: EntityType<out DefaultPokemonEntity>,
    val pokemon: Pokemon,
    val world: World
)