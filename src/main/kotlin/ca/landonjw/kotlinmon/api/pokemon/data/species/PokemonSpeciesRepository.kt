package ca.landonjw.kotlinmon.api.pokemon.data.species

interface PokemonSpeciesRepository {

    operator fun get(name: String) = getByName(name)

    operator fun get(species: ProvidedSpecies) = getProvided(species)

    fun getByName(name: String): PokemonSpecies?

    fun getProvided(species: ProvidedSpecies): PokemonSpecies

    fun getWhere(predicate: (species: PokemonSpecies) -> Boolean): List<PokemonSpecies>

    fun getAll(): List<PokemonSpecies>

}