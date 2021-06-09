package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRepository
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import net.minecraft.entity.Entity
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import kotlin.reflect.KProperty

open class PokeBallTypeController protected constructor(
    private val dataManager: EntityDataManager,
    private val clazz: Class<out Entity>
) {

    private val pokeBallRepository: PokeBallRepository by KotlinmonDI.inject()

    init {
        this.dataManager.register(getOrCreateDataParam(), ProvidedPokeBall.PokeBall.name)
    }

    private fun getOrCreateDataParam(): DataParameter<String> {
        if (pokeBallTypeParams[clazz] == null) {
            pokeBallTypeParams[clazz] = EntityDataManager.createKey(clazz, DataSerializers.STRING)
        }
        return pokeBallTypeParams[clazz]!!
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): PokeBall {
        return pokeBallRepository[dataManager.get(getOrCreateDataParam())] ?: ProvidedPokeBall.PokeBall
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: PokeBall) {
        dataManager.set(getOrCreateDataParam(), value.name)
    }

    companion object {
        private val pokeBallTypeParams: MutableMap<Class<*>, DataParameter<String>> = mutableMapOf()

        fun create(entity: Entity): PokeBallTypeController {
            return PokeBallTypeController(entity.dataManager, entity.javaClass)
        }
    }

}