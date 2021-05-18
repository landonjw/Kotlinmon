package ca.landonjw.kotlinmon.api.pokeball

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.common.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.PokeBallEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.World

interface PokeBallFactory {

    fun createItem(pokeBall: PokeBall, amount: Int = 1): ItemStack

    /**
     * Creates an entity instance of a [PokeBall].
     *
     * @param pokeBall the type of poke ball to create entity instance of
     * @param world the world to create the entity in
     *
     * @return an entity of a poke ball
     */
    fun createEntity(pokeBall: PokeBall, world: World): EmptyPokeBallEntity

    /**
     * Creates an entity instance of a [PokeBall] with a [Pokemon] contained in it.
     *
     * @param pokeBall the type of poke ball to create entity instance of
     * @param world the world to create the entity in
     * @param occupier the pokemon occupying the poke ball
     *
     * @return an entity of a poke ball
     */
    fun createEntity(pokeBall: PokeBall, world: World, occupier: Pokemon): OccupiedPokeBallEntity

}