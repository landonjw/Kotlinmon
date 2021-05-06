package ca.landonjw.kotlinmon.api.pokemon.data.species.type

interface PokemonTypeRepository {

    operator fun get(name: String) = getByName(name)

    operator fun get(type: ProvidedType) = getProvided(type)

    fun getByName(name: String): PokemonType?

    fun getProvided(type: ProvidedType): PokemonType

    fun getWhere(predicate: (type: PokemonType) -> Boolean): List<PokemonType>

    fun getAll(): List<PokemonType>

}