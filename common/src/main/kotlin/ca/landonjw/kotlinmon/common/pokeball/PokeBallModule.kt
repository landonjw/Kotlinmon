package ca.landonjw.kotlinmon.common.pokeball

import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRepository
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.api.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.api.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.common.EntityFactoryParams
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultEmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultOccupiedPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.OrientationController
import ca.landonjw.kotlinmon.common.pokeball.entity.PokeBallTypeController
import ca.landonjw.kotlinmon.common.pokeball.item.PokeBallItem
import net.minecraft.entity.Entity
import org.kodein.di.*

object PokeBallModule {

    operator fun invoke() = DI.Module(name = "Poke Ball") {
        bind<PokeBallTypeController>() with factory { entity: Entity -> PokeBallTypeController(entity.dataManager, entity.javaClass, instance()) }
        bind<OrientationController>() with factory { entity: Entity -> OrientationController(entity.dataManager, entity.javaClass) }
        bind<EmptyPokeBallEntity>() with factory { params: EmptyPokeBallFactoryParams -> DefaultEmptyPokeBallEntity(params.type, params.world, params.pokeBall, factory(), factory()) }
        bind<OccupiedPokeBallEntity>() with factory { params: OccupiedPokeBallFactoryParams -> DefaultOccupiedPokeBallEntity(params.type, params.world, params.pokeBall, params.occupant, factory(), factory(), instance()) }
        bind<PokeBallFactory>() with singleton { DefaultPokeBallFactory(instance(), instance(), factory(), factory()) }
        bind<PokeBallRepository>() with singleton { DefaultPokeBallRepository(instance()) }
        bind<PokeBallItem>() with singleton { PokeBallItem(instance(), instance()) }
        bind<DefaultEmptyPokeBallEntity>() with factory { params: EntityFactoryParams<DefaultEmptyPokeBallEntity> -> DefaultEmptyPokeBallEntity(params.type, params.world, ProvidedPokeBall.PokeBall, factory(), factory()) }
        bind<DefaultOccupiedPokeBallEntity>() with factory { params: EntityFactoryParams<DefaultOccupiedPokeBallEntity> -> DefaultOccupiedPokeBallEntity(params.type, params.world, ProvidedPokeBall.PokeBall, null, factory(), factory(), instance()) }
    }

}