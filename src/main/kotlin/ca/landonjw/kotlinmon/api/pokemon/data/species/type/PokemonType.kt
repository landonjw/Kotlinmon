package ca.landonjw.kotlinmon.api.pokemon.data.species.type

interface PokemonType {

    val name: String

    fun getDefensiveEffectiveness(attackingType: PokemonType): Effectiveness

    enum class Effectiveness(val modifier: Float) {
        SuperEffective(2f), Effective(1f), NotVeryEffective(0.5f), Immune(0f)
    }

}