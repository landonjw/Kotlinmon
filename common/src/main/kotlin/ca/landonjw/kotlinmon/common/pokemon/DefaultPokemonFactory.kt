package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.pokemon.entity.DefaultPokemonEntity
import net.minecraft.world.World

class DefaultPokemonFactory: PokemonFactory {

    override fun create(species: PokemonSpecies, form: PokemonForm?): Pokemon {
        return if (form == null) DefaultPokemon(species) else DefaultPokemon(species, form)
    }

    override fun createEntity(pokemon: Pokemon, world: World): DefaultPokemonEntity {
        // TODO: Validate if pokemon already has an entity associated with it
        return DefaultPokemonEntity(EntityRegistry.POKEMON.get(), world).apply { setPokemon(pokemon) }
    }

}