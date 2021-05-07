package ca.landonjw.kotlinmon.api.pokeball

interface PokeBallRepository {

    operator fun get(name: String): PokeBall? = getByName(name)

    operator fun get(pokeBall: ProvidedPokeBall): PokeBall = getProvided(pokeBall)

    fun getByName(name: String): PokeBall?

    fun getProvided(pokeBall: ProvidedPokeBall): PokeBall

    fun getWhere(predicate: (pokeBall: PokeBall) -> Boolean): List<PokeBall>

    fun getAll(): List<PokeBall>

}