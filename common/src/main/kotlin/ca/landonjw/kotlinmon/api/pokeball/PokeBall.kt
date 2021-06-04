package ca.landonjw.kotlinmon.api.pokeball

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.ResourceLocation

/**
 * A poke ball that can be used to store or capture [Pokemon].
 *
 * A single instance of a [PokeBall] should ever be created for a given poke ball type,
 * and should be registered on [PokeBallRegistrationEvent].
 *
 * To get a poke ball instance, see [PokeBallRepository] or [ProvidedPokeBall].
 *
 * @author landonjw
 */
interface PokeBall {
    /** A unique name to identify the poke ball. */
    val name: String
    /** The location to fetch assets for the poke ball from. */
    val modelLocation: ResourceLocation

    /**
     * Gets the catch rate when this Poke Ball is used on a [Pokemon].
     *
     * @param thrower the player that threw the poke ball
     * @param pokemon the pokemon that is attempted to be captured
     *
     * @return a value between 1 and 255. The higher the value, the greater the chance of capture
     */
    fun getCatchRate(thrower: ServerPlayerEntity, pokemon: Pokemon): Int
}