package ca.landonjw.kotlinmon.api.pokeball.capture.modifiers

import ca.landonjw.kotlinmon.api.pokemon.Pokemon

internal fun Pokemon.catchRate(): Int {
    return this.species.catchRate
}