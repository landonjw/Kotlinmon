package ca.landonjw.kotlinmon.common.pokemon.data.type

interface PokemonType {
    val name: String
    fun getDefensiveEffectiveness(type: PokemonType): Float
}