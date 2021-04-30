package ca.landonjw.kotlinmon.pokemon.registry

object PokemonTypeRegistry {
    private val _types: MutableMap<String, PokemonType> = mutableMapOf()
    val types: Map<String, PokemonType>
        get() = _types.toMap()

    operator fun get(type: String) = _types[type]

    init {
        registerDefaultTypes()
    }

    fun register(types: PokemonType) {
        if (_types.containsKey(types.name)) throw IllegalArgumentException("types is already registered")
        _types[types.name] = types
    }

    fun registerDefaultTypes() {
        for (default in PokemonTypes.values()) {
            register(default.type)
        }
    }
}