package ca.landonjw.kotlinmon.api.pokeball.entity

import ca.landonjw.kotlinmon.api.pokemon.Pokemon

/**
 * A poke ball that currently has a [Pokemon] occupying it.
 *
 * @author landonjw
 */
interface OccupiedPokeBallEntity : PokeBallEntity {

    /** The pokemon occupying the poke ball. */
    val occupant: Pokemon

}