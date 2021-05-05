package ca.landonjw.kotlinmon.common.pokemon.data.species.builder

import ca.landonjw.kotlinmon.common.pokemon.data.species.BaseStats
import ca.landonjw.kotlinmon.common.pokemon.data.species.PokemonForm
import ca.landonjw.kotlinmon.common.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.common.pokemon.data.type.PokemonType
import net.minecraft.util.ResourceLocation

fun species(name: String, init: PokemonSpeciesBuilder.() -> Unit): PokemonSpecies {
    val builder = PokemonSpeciesBuilder(name)
    builder.init()
    return builder.build()
}

class PokemonSpeciesBuilder(val name: String) {

    private val forms: MutableList<PokemonForm> = mutableListOf()

    fun form(name: String, init: PokemonFormBuilder.() -> Unit) {
        val formBuilder = PokemonFormBuilder(name)
        formBuilder.init()
        forms.add(formBuilder.build())
    }

    fun build(): PokemonSpecies {
        validate()
        return PokemonSpecies(name, forms)
    }

    private fun validate() {
        if (forms.isEmpty()) throw IllegalStateException("species must have atleast one form")
    }

}

class PokemonFormBuilder(val name: String) {

    lateinit var model: ResourceLocation
    lateinit var type1: PokemonType
    var type2: PokemonType? = null

    private lateinit var baseStats: BaseStats

    fun types(type1: PokemonType, type2: PokemonType? = null) {
        this.type1 = type1
        this.type2 = type2
    }

    fun baseStats(init: BaseStatsBuilder.() -> Unit) {
        val baseStatsBuilder = BaseStatsBuilder()
        baseStatsBuilder.init()
        baseStats = baseStatsBuilder.build()
    }

    fun build(): PokemonForm {
        validate()
        return PokemonForm(name, model, baseStats, type1, type2)
    }

    private fun validate() {

    }

}

class BaseStatsBuilder {

    var health: Int = -1
    var attack: Int = -1
    var defence: Int = -1
    var specialAttack: Int = -1
    var specialDefence: Int = -1
    var speed: Int = -1

    fun build(): BaseStats {
        validate()
        return BaseStats(health, attack, defence, specialAttack, specialDefence, speed)
    }

    private fun validate() {
        validatePositive(health, "health")
        validatePositive(attack, "attack")
        validatePositive(defence, "defence")
        validatePositive(specialAttack, "specialAttack")
        validatePositive(specialDefence, "specialDefence")
        validatePositive(speed, "speed")
    }

    private fun validatePositive(value: Int, stat: String) {
        if (value <= 0) throw IllegalStateException("$stat must be a positive integer")
    }

}