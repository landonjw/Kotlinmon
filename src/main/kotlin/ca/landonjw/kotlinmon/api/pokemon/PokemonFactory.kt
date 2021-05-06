package ca.landonjw.kotlinmon.api.pokemon

import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm

interface PokemonFactory {

    fun create(species: PokemonSpecies, form: PokemonForm? = null): Pokemon

}