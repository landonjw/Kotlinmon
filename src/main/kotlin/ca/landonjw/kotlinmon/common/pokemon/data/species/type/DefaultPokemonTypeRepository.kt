package ca.landonjw.kotlinmon.common.pokemon.data.species.type

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.PokemonType
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.PokemonTypeRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.ProvidedType
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.TypeRegistrationEvent
import net.minecraftforge.eventbus.api.IEventBus

class DefaultPokemonTypeRepository: PokemonTypeRepository {

    private val eventBus: IEventBus by KotlinmonDI.inject(tag = Kotlinmon.MODID)
    private val types: MutableMap<String, PokemonType> = mutableMapOf()

    init {
        getProvidedTypes().forEach { types[it.name.toLowerCase()] = it }
        getCustomTypes().forEach { types[it.name.toLowerCase()] = it }
    }

    private fun getProvidedTypes(): List<PokemonType> = ProvidedType.values().toList()

    private fun getCustomTypes(): List<PokemonType> {
        val registerEvent = TypeRegistrationEvent()
        eventBus.post(registerEvent)
        return registerEvent.customTypes.values.toList()
    }

    override fun getByName(name: String): PokemonType? = types[name.toLowerCase()]

    override fun getProvided(type: ProvidedType): PokemonType = types[type.name.toLowerCase()]!!

    override fun getWhere(predicate: (type: PokemonType) -> Boolean): List<PokemonType> {
        return types.values.filter(predicate)
    }

    override fun getAll(): List<PokemonType> {
        return types.values.toList()
    }

}