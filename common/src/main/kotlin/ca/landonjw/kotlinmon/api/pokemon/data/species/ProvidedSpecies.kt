package ca.landonjw.kotlinmon.api.pokemon.data.species

import ca.landonjw.kotlinmon.KotlinmonDI

enum class ProvidedSpecies {
    Bulbasaur,
    Ivysaur,
    Venusaur;

    /**
     * Gets the [PokemonSpecies] that this value represents.
     *
     * This function is required over direct access due to the provided species
     * because the species are loaded from file and initialized in the [PokemonSpeciesRepository],
     * so we need to fetch this lazy.
     *
     * Some alternatives that were considered:
     *  - Converting PokemonSpecies to an interface and implementing in Enum
     *      - This simply adds more complexity and boilerplate, so was decided against.
     *  - Hard-coding the PokemonSpecies for each enum
     *      - Obviously bloats the hell out of this class and isn't very feasible.
     */
    fun get(): PokemonSpecies {
        val speciesRepository: PokemonSpeciesRepository by KotlinmonDI.inject()
        return speciesRepository[this]
    }

}