package ca.landonjw.kotlinmon.api.pokeball.entity

import ca.landonjw.kotlinmon.api.pokemon.Pokemon

interface OccupiedPokeBallEntity : PokeBallEntity {

    /** The pokemon occupying the poke ball. */
    val occupant: Pokemon

}