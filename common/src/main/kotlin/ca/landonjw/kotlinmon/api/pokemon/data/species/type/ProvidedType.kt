package ca.landonjw.kotlinmon.api.pokemon.data.species.type

import java.lang.IllegalStateException

enum class ProvidedType: PokemonType{
    NORMAL,
    FIGHTING,
    FLYING,
    POISON,
    GROUND,
    ROCK,
    BUG,
    GHOST,
    STEEL,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    PSYCHIC,
    ICE,
    DRAGON,
    DARK,
    FAIRY;

    /**
     * Represents the type effectiveness table according to [Bulbapedia](https://bulbapedia.bulbagarden.net/wiki/Type#Type_effectiveness)
     * Row represents the attacking type, column represents defending type.
     */
    val typeEffectiveness: Array<Double> = arrayOf(
//              Normal  Fighting  Flying  Poison  Ground  Rock   Bug   Ghost  Steel  Fire  Water  Grass  Electric  Psychic  Ice   Dragon  Dark  Fairy
/* Normal   */  1.0,    1.0,      1.0,    1.0,    1.0,    0.5,   1.0,  0.0,   0.5,   1.0,  1.0,   1.0,   1.0,      1.0,     1.0,  1.0,    1.0,  1.0,
/* Fighting */  2.0,    1.0,      0.5,    0.5,    1.0,    2.0,   0.5,  0.0,   2.0,   1.0,  1.0,   1.0,   1.0,      0.5,     2.0,  1.0,    2.0,  0.5,
/* Flying   */  1.0,    2.0,      1.0,    1.0,    1.0,    0.5,   2.0,  1.0,   0.5,   1.0,  1.0,   2.0,   0.5,      1.0,     1.0,  1.0,    1.0,  1.0,
/* Poison   */  1.0,    1.0,      1.0,    0.5,    0.5,    0.5,   1.0,  0.5,   0.0,   1.0,  1.0,   2.0,   1.0,      1.0,     1.0,  1.0,    1.0,  2.0,
/* Ground   */  1.0,    1.0,      0.0,    2.0,    1.0,    2.0,   0.5,  1.0,   2.0,   2.0,  1.0,   0.5,   2.0,      1.0,     1.0,  1.0,    1.0,  1.0,
/* Rock     */  1.0,    0.5,      2.0,    1.0,    0.5,    1.0,   2.0,  1.0,   0.5,   2.0,  1.0,   1.0,   1.0,      1.0,     2.0,  1.0,    1.0,  1.0,
/* Bug      */  1.0,    0.5,      0.5,    0.5,    1.0,    1.0,   1.0,  0.5,   0.5,   0.5,  1.0,   2.0,   1.0,      2.0,     1.0,  1.0,    2.0,  0.5,
/* Ghost    */  0.0,    1.0,      1.0,    1.0,    1.0,    1.0,   1.0,  2.0,   1.0,   1.0,  1.0,   1.0,   1.0,      2.0,     1.0,  1.0,    0.5,  1.0,
/* Steel    */  1.0,    1.0,      1.0,    1.0,    1.0,    2.0,   1.0,  1.0,   0.5,   0.5,  0.5,   1.0,   0.5,      1.0,     2.0,  1.0,    1.0,  2.0,
/* Fire     */  1.0,    1.0,      1.0,    1.0,    1.0,    0.5,   2.0,  1.0,   2.0,   0.5,  0.5,   2.0,   1.0,      1.0,     2.0,  0.5,    1.0,  1.0,
/* Water    */  1.0,    1.0,      1.0,    1.0,    2.0,    2.0,   1.0,  1.0,   1.0,   2.0,  0.5,   0.5,   1.0,      1.0,     1.0,  0.5,    1.0,  1.0,
/* Grass    */  1.0,    1.0,      0.5,    0.5,    2.0,    2.0,   0.5,  1.0,   0.5,   0.5,  2.0,   0.5,   1.0,      1.0,     1.0,  0.5,    1.0,  1.0,
/* Electric */  1.0,    1.0,      2.0,    1.0,    0.0,    1.0,   1.0,  1.0,   1.0,   1.0,  2.0,   0.5,   0.5,      1.0,     1.0,  0.5,    1.0,  1.0,
/* Psychic  */  1.0,    2.0,      1.0,    2.0,    1.0,    1.0,   1.0,  1.0,   0.5,   1.0,  1.0,   1.0,   1.0,      0.5,     1.0,  1.0,    0.0,  1.0,
/* Ice      */  1.0,    1.0,      2.0,    1.0,    2.0,    1.0,   1.0,  1.0,   0.5,   0.5,  0.5,   2.0,   1.0,      1.0,     0.5,  2.0,    1.0,  1.0,
/* Dragon   */  1.0,    1.0,      1.0,    1.0,    1.0,    1.0,   1.0,  1.0,   0.5,   1.0,  1.0,   1.0,   1.0,      1.0,     1.0,  2.0,    1.0,  0.0,
/* Dark     */  1.0,    0.5,      1.0,    1.0,    1.0,    1.0,   1.0,  0.0,   1.0,   1.0,  1.0,   1.0,   1.0,      2.0,     1.0,  1.0,    0.5,  0.5,
/* Fairy    */  1.0,    2.0,      1.0,    0.5,    1.0,    1.0,   1.0,  1.0,   0.5,   0.5,  1.0,   1.0,   1.0,      1.0,     1.0,  2.0,    2.0,  1.0
    )

    private val effectiveness: MutableMap<String, PokemonType.Effectiveness> by lazy { getDefaultEffectivenessMap() }
    private val NUM_TYPES = 18

    private fun getDefaultEffectivenessMap(): MutableMap<String, PokemonType.Effectiveness> {
        val effectivenessByType: MutableMap<String, PokemonType.Effectiveness> = mutableMapOf()

        val col = ordinal
        for (row in 0 until NUM_TYPES) {
            val typeName = values()[row].name
            effectivenessByType[typeName] = typeEffectiveness[row * NUM_TYPES + col].toEffectiveness()
        }

        return effectivenessByType
    }

    private fun Double.toEffectiveness(): PokemonType.Effectiveness = when(this) {
        0.0 -> PokemonType.Effectiveness.Immune
        0.5 -> PokemonType.Effectiveness.NotVeryEffective
        1.0 -> PokemonType.Effectiveness.Effective
        2.0 -> PokemonType.Effectiveness.SuperEffective
        else -> throw IllegalStateException("effectiveness could not be parsed")
    }

    fun setDefensiveEffectiveness(type: PokemonType, effectiveness: PokemonType.Effectiveness) {
        this.effectiveness[type.name] = effectiveness
    }

    override fun getDefensiveEffectiveness(attackingType: PokemonType): PokemonType.Effectiveness {
        return effectiveness[attackingType.name] ?: PokemonType.Effectiveness.Effective
    }

}