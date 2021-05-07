package ca.landonjw.kotlinmon.api.pokemon

import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntity
import net.minecraft.world.World

interface PokemonFactory {

    fun create(species: PokemonSpecies, form: PokemonForm? = null): Pokemon

    fun createEntity(pokemon: Pokemon, world: World): PokemonEntity

}