package ca.landonjw.kotlinmon.api.pokeball

import ca.landonjw.kotlinmon.KotlinmonDI
import net.minecraftforge.eventbus.api.Event

class PokeBallRegistrationEvent: Event() {
    private val repository: PokeBallRepository by KotlinmonDI.inject()

    private val _customPokeBalls: MutableMap<String, PokeBall> = mutableMapOf()
    val customPokeBalls: Map<String, PokeBall>
        get() = _customPokeBalls.toMap()

    fun register(pokeBall: PokeBall) {
        check(!isRegistered(pokeBall.name)) { "poke ball already registered to given name: ${pokeBall.name}" }
        _customPokeBalls[pokeBall.name] = pokeBall
    }

    fun isRegistered(name: String): Boolean = _customPokeBalls[name] != null || repository[name] != null
}