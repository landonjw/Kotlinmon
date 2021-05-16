package ca.landonjw.kotlinmon.api.pokemon.data.species.form

import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.stats.BaseStats
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.PokemonType
import net.minecraft.util.ResourceLocation

class PokemonForm(
    val name: String,
    val baseStats: BaseStats,
    val type1: PokemonType,
    val type2: PokemonType?
) {

    companion object {

        operator fun invoke(init: PokemonSpecies.Companion.Builder.() -> Unit): PokemonSpecies {
            val builder = PokemonSpecies.Companion.Builder()
            builder.init()
            return builder.build()
        }

        class Builder internal constructor() {
            var name: String? = null
            var baseStats: BaseStats? = null
            private var type1: PokemonType? = null
            private var type2: PokemonType? = null

            fun types(type1: PokemonType, type2: PokemonType? = null) {
                this.type1 = type1
                this.type2 = type2
            }

            fun baseStats(init: BaseStats.Companion.Builder.() -> Unit) {
                val builder = BaseStats.Companion.Builder()
                builder.init()
                baseStats = builder.build()
            }

            fun build(): PokemonForm {
                validate()
                return PokemonForm(name!!, baseStats!!, type1!!, type2)
            }

            private fun validate() {
                checkNotNull(name) { "name must be defined" }
                checkNotNull(baseStats) { "base stats must be defined" }
                checkNotNull(type1) { "at least one type must be defined" }
            }
        }

    }

}