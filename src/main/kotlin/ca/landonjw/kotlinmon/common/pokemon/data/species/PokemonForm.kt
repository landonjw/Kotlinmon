package ca.landonjw.kotlinmon.common.pokemon.data.species

import ca.landonjw.kotlinmon.common.pokemon.data.type.PokemonType
import net.minecraft.util.ResourceLocation

data class PokemonForm(
    val name: String,
    val modelLocation: ResourceLocation,
    val baseStats: BaseStats,
    val type1: PokemonType,
    val type2: PokemonType?
)