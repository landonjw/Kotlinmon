package ca.landonjw.kotlinmon.api.pokemon.data.species.type

import net.minecraftforge.eventbus.api.Event

class TypeRegistrationEvent(private val repository: PokemonTypeRepository): Event() {

    private val _customTypes: MutableMap<String, PokemonType> = mutableMapOf()
    val customTypes: Map<String, PokemonType>
        get() = _customTypes.toMap()

    fun register(type: PokemonType) {
        check(!isRegistered(type.name)) { "type already registered to given type name" }
        _customTypes[type.name] = type
    }

    fun isRegistered(name: String): Boolean = _customTypes[name] != null || repository[name] != null
}