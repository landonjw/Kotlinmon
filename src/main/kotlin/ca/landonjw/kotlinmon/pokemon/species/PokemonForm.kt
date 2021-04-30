package ca.landonjw.kotlinmon.pokemon.species

import ca.landonjw.kotlinmon.pokemon.registry.PokemonType
import net.minecraft.util.ResourceLocation

data class PokemonForm(
    val name: String,
    val modelLocation: ResourceLocation,
    val baseStats: BaseStats,
    val type1: PokemonType,
    val type2: PokemonType?
)