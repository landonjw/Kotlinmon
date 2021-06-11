package ca.landonjw.kotlinmon.common.pokeball

import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRepository
import ca.landonjw.kotlinmon.api.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.api.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultEmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultOccupiedPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.OrientationController
import ca.landonjw.kotlinmon.common.pokeball.entity.PokeBallTypeController
import ca.landonjw.kotlinmon.common.pokeball.item.PokeBallItem
import net.minecraft.entity.Entity
import org.kodein.di.*

object PokeBallModule {

    val bindings = DI.Module(name = "Poke Ball") {
        bindFactory<Entity, PokeBallTypeController> { entity -> PokeBallTypeController(entity.dataManager, entity.javaClass, instance()) }
        bindFactory<Entity, OrientationController> { entity -> OrientationController(entity.dataManager, entity.javaClass) }
        bindFactory<PokeBallFactoryParams, EmptyPokeBallEntity> { params -> DefaultEmptyPokeBallEntity(params.world, params.pokeBall, factory(), factory(), instance(), instance()) }
        bindFactory<PokeBallFactoryParams, OccupiedPokeBallEntity> { params -> DefaultOccupiedPokeBallEntity(params.world, params.pokeBall, params.occupant!!, instance(), factory(), factory(), instance()) }
        bind<PokeBallFactory> { singleton { DefaultPokeBallFactory(instance(), factory(), factory()) } }
        bind<PokeBallRepository> { singleton { DefaultPokeBallRepository(instance()) } }
        bind<PokeBallItem> { singleton { PokeBallItem(instance(), instance()) } }
    }

}