package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.api.pokemon.Pokemon

fun Pokemon.toDTO(): PokemonDTO = PokemonDTO(
    species = this.species,
    form = this.form,
    texture = this.texture
)