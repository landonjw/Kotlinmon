package ca.landonjw.kotlinmon.pokemon.registry

enum class PokemonTypes{
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

    val type: PokemonType

    init {
        val effectiveness: MutableMap<String, Float> = mutableMapOf()
        val start = ordinal * 18
        val end = start + 18
        for (index in start until end) {
            effectiveness[values()[index / ordinal].name] = typeEffectiveness[index].toFloat()
        }
        type = SimplePokemonType(this.name.toLowerCase(), effectiveness)
    }

    private class SimplePokemonType(
        override val name: String,
        val effectiveness: Map<String, Float>
    ): PokemonType {
        override fun getDefensiveEffectiveness(type: PokemonType) = effectiveness[type.name] ?: 1.0f
    }

}