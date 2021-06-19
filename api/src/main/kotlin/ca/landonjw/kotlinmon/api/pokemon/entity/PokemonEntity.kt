package ca.landonjw.kotlinmon.api.pokemon.entity

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import net.minecraft.entity.passive.TameableEntity

interface PokemonEntity {

    /** The pokemon the entity represents. */
    val pokemon: Pokemon

    /** Gets the underlying Minecraft entity instance of the pokemon entity. */
    fun asMinecraftEntity(): TameableEntity

}