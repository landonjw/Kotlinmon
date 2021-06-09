package ca.landonjw.kotlinmon.api.pokemon.entity

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.world.World

abstract class PokemonEntity(
    type: EntityType<out PokemonEntity>,
    world: World
) : TameableEntity(type, world) {

    /** The pokemon the entity represents. */
    abstract val pokemon: Pokemon

}