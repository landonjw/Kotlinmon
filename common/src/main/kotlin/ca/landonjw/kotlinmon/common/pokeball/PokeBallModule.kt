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
        bind<PokeBallTypeController>() with factory { entity: Entity -> PokeBallTypeController(entity.dataManager, entity.javaClass, instance()) }
        bind<OrientationController>() with factory { entity: Entity -> OrientationController(entity.dataManager, entity.javaClass) }
        bind<EmptyPokeBallEntity>() with factory { params: PokeBallFactoryParams -> DefaultEmptyPokeBallEntity(params.world, params.pokeBall, factory(), factory(), instance(), instance()) }
        bind<OccupiedPokeBallEntity>() with factory { params: PokeBallFactoryParams -> DefaultOccupiedPokeBallEntity(params.world, params.pokeBall, params.occupant!!, instance(), factory(), factory(), instance()) }
        bind<PokeBallFactory>() with singleton { DefaultPokeBallFactory(instance(), factory(), factory()) }
        bind<PokeBallRepository>() with singleton { DefaultPokeBallRepository(instance()) }
        bind<PokeBallItem>() with singleton { PokeBallItem(instance(), instance()) }
    }

}