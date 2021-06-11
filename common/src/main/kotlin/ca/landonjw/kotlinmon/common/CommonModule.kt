package ca.landonjw.kotlinmon.common

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.common.network.NetworkModule
import ca.landonjw.kotlinmon.common.pokeball.PokeBallModule
import ca.landonjw.kotlinmon.common.pokemon.PokemonModule
import net.minecraftforge.eventbus.api.IEventBus
import org.kodein.di.*

object CommonModule {

    val bindings = DI.Module(name = "Common") {
        import(PokeBallModule.bindings)
        import(PokemonModule.bindings)
        import(NetworkModule.bindings)

        // Events
        bind<IEventBus>() with singleton { Kotlinmon.EVENT_BUS }

        // Registries
        bind<EntityRegistry>() with singleton { EntityRegistry() }
        bind<ItemRegistry>() with singleton { ItemRegistry(provider()) }
    }

}