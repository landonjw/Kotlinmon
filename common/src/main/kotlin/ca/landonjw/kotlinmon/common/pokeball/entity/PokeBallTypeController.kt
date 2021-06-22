package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRepository
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import net.minecraft.entity.Entity
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager

open class PokeBallTypeController constructor(
    private val dataManager: EntityDataManager,
    private val clazz: Class<out Entity>,
    private val pokeBallRepository: PokeBallRepository
) {

    init {
        this.dataManager.define(getOrCreateDataParam(), ProvidedPokeBall.PokeBall.name)
    }

    private fun getOrCreateDataParam(): DataParameter<String> {
        if (pokeBallTypeParams[clazz] == null) {
            pokeBallTypeParams[clazz] = EntityDataManager.defineId(clazz, DataSerializers.STRING)
        }
        return pokeBallTypeParams[clazz]!!
    }

    fun get(): PokeBall {
        return pokeBallRepository[dataManager.get(getOrCreateDataParam())] ?: ProvidedPokeBall.PokeBall
    }

    fun set(value: PokeBall) {
        dataManager.set(getOrCreateDataParam(), value.name)
    }

    companion object {
        private val pokeBallTypeParams: MutableMap<Class<*>, DataParameter<String>> = mutableMapOf()
    }

}