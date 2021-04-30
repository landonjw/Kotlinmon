package ca.landonjw.kotlinmon.pokemon.registry

interface PokemonType {
    val name: String
    fun getDefensiveEffectiveness(type: PokemonType): Float
}