package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import java.util.*

class DefaultPokemon(
    override val species: PokemonSpecies,
    override var form: PokemonForm = species.defaultForm,
    override var owner: UUID? = null
): Pokemon {

    // TODO

}