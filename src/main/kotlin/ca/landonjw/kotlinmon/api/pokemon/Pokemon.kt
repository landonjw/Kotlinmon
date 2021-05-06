package ca.landonjw.kotlinmon.api.pokemon

import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import java.util.*

interface Pokemon {
    val species: PokemonSpecies
    val form: PokemonForm

    val owner: UUID?
}