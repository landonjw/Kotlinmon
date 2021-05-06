package ca.landonjw.kotlinmon.api.pokemon.data.species.type

import ca.landonjw.kotlinmon.KotlinmonDI
import net.minecraftforge.eventbus.api.Event

class TypeRegistrationEvent: Event() {
    private val repository: PokemonTypeRepository by KotlinmonDI.inject()

    private val _customTypes: MutableMap<String, PokemonType> = mutableMapOf()
    val customTypes: Map<String, PokemonType>
        get() = _customTypes.toMap()

    fun register(type: PokemonType) {
        check(!isRegistered(type.name)) { "type already registered to given type name" }
        _customTypes[type.name] = type
    }

    fun isRegistered(name: String): Boolean = _customTypes[name] != null || repository[name] != null
}