package ca.landonjw.kotlinmon.pokemon.species

object SpeciesRegistry {
    private val _species: MutableMap<String, PokemonSpecies> = mutableMapOf()
    val species: Map<String, PokemonSpecies>
        get() = _species.toMap()

    operator fun get(species: String) = _species[species]

    init {
        registerDefaultSpecies()
    }

    fun register(species: PokemonSpecies) {
        if (_species.containsKey(species.name)) throw IllegalArgumentException("species is already registered")
        _species[species.name] = species
    }

    fun registerDefaultSpecies() {
        // TODO
    }
}