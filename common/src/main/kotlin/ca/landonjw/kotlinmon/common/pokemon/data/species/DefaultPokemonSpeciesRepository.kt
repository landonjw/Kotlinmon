package ca.landonjw.kotlinmon.common.pokemon.data.species

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.ProvidedSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.SpeciesRegistrationEvent
import ca.landonjw.kotlinmon.common.pokemon.data.species.loader.PokemonSpeciesLoader
import net.minecraft.util.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus

class DefaultPokemonSpeciesRepository(
    private val eventBus: IEventBus,
    private val speciesLoader: PokemonSpeciesLoader
): PokemonSpeciesRepository {

    private val species: MutableMap<String, PokemonSpecies> = mutableMapOf()

    init {
        getProvidedSpecies().forEach { species[it.name.toLowerCase()] = it }
        getCustomSpecies().forEach { species[it.name.toLowerCase()] = it }
    }

    private fun getProvidedSpecies(): List<PokemonSpecies> {
        return ProvidedSpecies.values()
            .map { species -> speciesLoader.load(getStatResource(species)) }
            .toList()
    }

    private fun getStatResource(species: ProvidedSpecies): ResourceLocation {
        val path = "pokemon/$species/$species.json".toLowerCase()
        return ResourceLocation(Kotlinmon.MOD_ID, path)
    }

    private fun getCustomSpecies(): List<PokemonSpecies> {
        val registerEvent = SpeciesRegistrationEvent(this)
        eventBus.post(registerEvent)
        return registerEvent.customSpecies.values.toList()
    }

    override fun getByName(name: String): PokemonSpecies? = species[name.toLowerCase()]

    override fun getProvided(species: ProvidedSpecies): PokemonSpecies = this.species[species.name.toLowerCase()]!!

    override fun getWhere(predicate: (species: PokemonSpecies) -> Boolean): List<PokemonSpecies> {
        return species.values.filter(predicate)
    }

    override fun getAll(): List<PokemonSpecies> = species.values.toList()

}