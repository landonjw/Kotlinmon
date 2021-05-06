package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm

class DefaultPokemonFactory: PokemonFactory {
    override fun create(species: PokemonSpecies, form: PokemonForm?): Pokemon {
        return if (form == null) DefaultPokemon(species) else DefaultPokemon(species, form)
    }
}