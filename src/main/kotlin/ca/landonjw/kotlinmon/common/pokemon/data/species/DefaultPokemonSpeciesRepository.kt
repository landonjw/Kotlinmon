package ca.landonjw.kotlinmon.common.pokemon.data.species

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.ProvidedSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.SpeciesRegistrationEvent
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.ProvidedType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus

class DefaultPokemonSpeciesRepository: PokemonSpeciesRepository {

    private val eventBus: IEventBus by KotlinmonDI.inject(tag = Kotlinmon.MODID)
    private val species: MutableMap<String, PokemonSpecies> = mutableMapOf()

    init {
        getProvidedSpecies().forEach { species[it.name] = it }
        getCustomSpecies().forEach { species[it.name] = it }
    }

    // TODO: Actually register from enum, loading from json assets
    private fun getProvidedSpecies(): List<PokemonSpecies> {
        val species: MutableList<PokemonSpecies> = mutableListOf()
        val corphish = PokemonSpecies {
            name = "Corphish"
            defaultForm {
                types(ProvidedType.WATER)
                modelLocation = ResourceLocation(Kotlinmon.MODID,"pokemon/corphish/corphish.pqc")
                baseStats {
                    health = 43
                    attack = 80
                    defense = 65
                    specialAttack = 50
                    specialDefense = 35
                    speed = 35
                }
            }
        }
        species.add(corphish)
        return species
    }

    private fun getCustomSpecies(): List<PokemonSpecies> {
        val registerEvent = SpeciesRegistrationEvent()
        eventBus.post(registerEvent)
        return registerEvent.customSpecies.values.toList()
    }

    override fun getByName(name: String): PokemonSpecies? = species[name]

    override fun getProvided(species: ProvidedSpecies): PokemonSpecies = this.species[species.name]!!

    override fun getWhere(predicate: (species: PokemonSpecies) -> Boolean): List<PokemonSpecies> {
        return species.values.filter(predicate)
    }

    override fun getAll(): List<PokemonSpecies> = species.values.toList()

}