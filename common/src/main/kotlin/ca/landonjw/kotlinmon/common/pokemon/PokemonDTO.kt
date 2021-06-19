package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm

data class PokemonDTO(
    val species: PokemonSpecies,
    val form: PokemonForm,
    val texture: String? = null
    /* TODO: Add this shit when I eventually implement them
     *    EVs & IVs (maybe? is this ever rendered?)
     *    Ability
     *    Nickname
     *    Moves
     */
)