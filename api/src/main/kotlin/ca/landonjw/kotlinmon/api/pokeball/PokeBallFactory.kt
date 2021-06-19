package ca.landonjw.kotlinmon.api.pokeball

import ca.landonjw.kotlinmon.api.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.api.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import net.minecraft.item.ItemStack
import net.minecraft.world.World

interface PokeBallFactory {

    /**
     * Creates an item instance of a [PokeBall].
     *
     * @param pokeBall The type of poke ball to create item of
     * @param amount The amount of poke balls to create in the stack. Must be between 1 and 64.
     */
    fun createItem(pokeBall: PokeBall, amount: Int = 1): ItemStack

    /**
     * Creates an entity instance of a [PokeBall].
     *
     * @param pokeBall The type of poke ball to create entity instance of.
     * @param world The world to create the entity in.
     *
     * @return An entity of a poke ball.
     */
    fun createEntity(pokeBall: PokeBall, world: World): EmptyPokeBallEntity

    /**
     * Creates an entity instance of a [PokeBall].
     *
     * @param pokeBall The type of poke ball to create entity instance of.
     * @param world The world to create the entity in.
     * @param occupant The pokemon occupying the poke ball
     *
     * @return An entity of a poke ball.
     */
    fun createEntity(pokeBall: PokeBall, world: World, occupant: Pokemon): OccupiedPokeBallEntity

}