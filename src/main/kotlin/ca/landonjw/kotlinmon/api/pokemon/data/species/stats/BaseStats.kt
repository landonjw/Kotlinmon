package ca.landonjw.kotlinmon.api.pokemon.data.species.stats

class BaseStats(
    val health: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
) {

    fun getBaseStatTotal(): Int = health + attack + defense + specialAttack + specialDefense + speed

    fun getBaseStatAverage(): Double = getBaseStatTotal() / 6.0

    companion object {

        operator fun invoke(init: Builder.() -> Unit): BaseStats {
            val builder = Builder()
            builder.init()
            return builder.build()
        }

        class Builder internal constructor() {
            var health: Int? = null
            var attack: Int? = null
            var defense: Int? = null
            var specialAttack: Int? = null
            var specialDefense: Int? = null
            var speed: Int? = null

            fun build(): BaseStats {
                validate()
                return BaseStats(health!!, attack!!, defense!!, specialAttack!!, specialDefense!!, speed!!)
            }

            private fun validate() {
                requireNotNull(health) { "health stat must be defined" }
                requireNotNull(attack) { "attack stat must be defined" }
                requireNotNull(defense) { "defense stat must be defined" }
                requireNotNull(specialAttack) { "special attack stat must be defined" }
                requireNotNull(specialDefense) { "special defense stat must be defined" }
                requireNotNull(speed) { "speed stat must be defined" }
            }

        }

    }

}