package ca.landonjw.kotlinmon.common

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.common.network.NetworkModule
import ca.landonjw.kotlinmon.common.pokeball.PokeBallModule
import ca.landonjw.kotlinmon.common.pokemon.PokemonModule
import net.minecraft.entity.EntityType
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.IForgeRegistry
import org.kodein.di.*

object CommonModule {

    operator fun invoke() = DI.Module(name = "Common") {
        import(PokeBallModule())
        import(PokemonModule())
        import(NetworkModule())

        // Events
        bind<IEventBus>() with singleton { Kotlinmon.EVENT_BUS }

        // Registries
        bind<EntityRegistry>() with eagerSingleton { EntityRegistry(factory(), factory(), factory()) }
        bind<ItemRegistry>() with eagerSingleton { ItemRegistry(provider()) }
    }

}