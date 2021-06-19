package ca.landonjw.kotlinmon.common.pokeball

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRegistrationEvent
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRepository
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import net.minecraftforge.eventbus.api.IEventBus

class DefaultPokeBallRepository(
    private val eventBus: IEventBus
): PokeBallRepository {

    private val pokeBalls: MutableMap<String, PokeBall> = mutableMapOf()

    init {
        getProvidedPokeBalls().forEach { pokeBalls[it.name.toLowerCase()] = it }
        getCustomPokeBalls().forEach { pokeBalls[it.name.toLowerCase()] = it }
    }

    private fun getProvidedPokeBalls(): List<PokeBall> = ProvidedPokeBall.values().toList()

    private fun getCustomPokeBalls(): List<PokeBall> {
        val registerEvent = PokeBallRegistrationEvent(this)
        eventBus.post(registerEvent)
        return registerEvent.customPokeBalls.values.toList()
    }

    override fun getByName(name: String): PokeBall? = pokeBalls[name.toLowerCase()]

    override fun getProvided(pokeBall: ProvidedPokeBall): PokeBall = this.pokeBalls[pokeBall.name.toLowerCase()]!!

    override fun getWhere(predicate: (species: PokeBall) -> Boolean): List<PokeBall> {
        return pokeBalls.values.filter(predicate)
    }

    override fun getAll(): List<PokeBall> = pokeBalls.values.toList()

}