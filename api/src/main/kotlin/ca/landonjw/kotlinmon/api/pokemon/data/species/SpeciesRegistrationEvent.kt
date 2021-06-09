package ca.landonjw.kotlinmon.api.pokemon.data.species

import net.minecraftforge.eventbus.api.Event

class SpeciesRegistrationEvent: Event() {
    private val repository: PokemonSpeciesRepository = TODO("figure out API dependency injection")

    private val _customSpecies: MutableMap<String, PokemonSpecies> = mutableMapOf()
    val customSpecies: Map<String, PokemonSpecies>
        get() = _customSpecies.toMap()

    fun register(species: PokemonSpecies) {
        check(!isRegistered(species.name)) { "species already registered to given species name" }
        _customSpecies[species.name] = species
    }

    fun isRegistered(name: String): Boolean = _customSpecies[name] != null || repository[name] != null
}