package ca.landonjw.kotlinmon.api.pokemon

import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntity
import net.minecraft.world.World

/**
 * A factory for creating [Pokemon] related instances.
 *
 * @author landonjw
 */
interface PokemonFactory {

    /**
     * Creates a new instance of a [Pokemon]
     *
     * @param species the species of the pokemon
     * @param form the form of the pokemon, default form if empty
     *
     * @return a new instance of a pokemon
     */
    fun create(species: PokemonSpecies, form: PokemonForm? = null): Pokemon

    /**
     * Creates an entity for a [Pokemon] instance
     *
     * **Warning: A [Pokemon] instance may only have one associated entity at a time.**
     * This will only create the entity, it will not spawn it.
     * To actually spawn the entity, use `World#addEntity`.
     *
     * Example:
     * ```
     * val world: World = // initialized somewhere
     * val pokemonEntity: PokemonEntity = // initialized somewhere
     * world.addEntity(pokemonEntity)
     * ```
     * TODO: Make convenience method to check if a Pokemon has an entity
     *
     * @param pokemon the pokemon to create an entity for
     * @param world the world to create the entity in
     *
     * @return a new entity instance for the pokemon in the given world
     */
    fun createEntity(pokemon: Pokemon, world: World): PokemonEntity

}