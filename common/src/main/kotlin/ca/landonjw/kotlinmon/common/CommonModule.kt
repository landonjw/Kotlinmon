package ca.landonjw.kotlinmon.common

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.common.network.NetworkModule
import ca.landonjw.kotlinmon.common.pokeball.PokeBallModule
import ca.landonjw.kotlinmon.common.pokemon.PokemonModule
import net.minecraftforge.eventbus.api.IEventBus
import org.kodein.di.*

object CommonModule {

    val bindings = DI.Module(name = "Common") {
        // Events
        bind<IEventBus> { singleton { Kotlinmon.EVENT_BUS } }

        // Registries
        bind<EntityRegistry> { singleton { EntityRegistry() } }
        bind<ItemRegistry> { singleton { ItemRegistry(provider()) } }

        import(PokeBallModule.bindings)
        import(PokemonModule.bindings)
        import(NetworkModule.bindings)
    }

}