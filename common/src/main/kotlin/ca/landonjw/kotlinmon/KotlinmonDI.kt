package ca.landonjw.kotlinmon

import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyNetworkService
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyStorage
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyStorageRepository
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRepository
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.PokemonTypeRepository
import ca.landonjw.kotlinmon.client.party.ClientPartyStorage
import ca.landonjw.kotlinmon.client.render.models.smd.repository.AsyncModelRepository
import ca.landonjw.kotlinmon.client.render.models.smd.repository.ModelRepository
import ca.landonjw.kotlinmon.client.render.models.smd.repository.SyncModelRepository
import ca.landonjw.kotlinmon.common.network.SimpleChannelWrapper
import ca.landonjw.kotlinmon.common.pokeball.DefaultPokeBallFactory
import ca.landonjw.kotlinmon.common.pokeball.DefaultPokeBallRepository
import ca.landonjw.kotlinmon.common.pokemon.DefaultPokemonFactory
import ca.landonjw.kotlinmon.common.pokemon.data.species.DefaultPokemonSpeciesRepository
import ca.landonjw.kotlinmon.common.pokemon.data.species.type.DefaultPokemonTypeRepository
import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonDecoder
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonToClientDataEncoder
import ca.landonjw.kotlinmon.server.player.storage.party.DefaultPartyNetworkService
import ca.landonjw.kotlinmon.server.player.storage.party.DefaultPartyStorage
import ca.landonjw.kotlinmon.server.player.storage.party.DefaultPartyStorageRepository
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.EventBus
import net.minecraftforge.eventbus.api.BusBuilder
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.loading.FMLEnvironment
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.kodein.di.*
import java.util.*

object KotlinmonDI {

    lateinit var container: DI
        private set

    // TODO: Have different DI container if mod is running just on server
    /**
     * Initializes the dependency injection container.
     *
     * **This should ONLY be called by [Kotlinmon], once.**
     *
     * We run the initialization lazy in order to allow for unit tests to
     * mock the [KotlinmonDI] instance for providing relevant mocked dependencies.
     */
    internal fun initialize() {
        this.container = when (FMLEnvironment.dist!!) {
            Dist.CLIENT -> DI { import(clientModule()) }
            Dist.DEDICATED_SERVER -> DI { import(serverModule()) }
        }
    }

    private fun clientModule(): DI.Module {
        return DI.Module(name = "Client") {
            // Include the server module here because of integrated server, in case of single player worlds.
            import(serverModule())

            // Packets
            bind<ClientPokemonDecoder> { singleton { ClientPokemonDecoder() } }

            // Party
            bind<ClientPartyStorage> { singleton { ClientPartyStorage() } }

            // Models
            bind<ModelRepository>(tag = "async") { singleton { AsyncModelRepository() } }
            bind<ModelRepository>(tag = "sync") { singleton { SyncModelRepository() } }
        }
    }

    private fun serverModule(): DI.Module {
        return DI.Module(name = "Server") {
            // Pokemon
            bind<PokemonFactory> { singleton { DefaultPokemonFactory() } }
            bind<PokemonSpeciesRepository> { singleton { DefaultPokemonSpeciesRepository() } }
            bind<PokemonTypeRepository> { singleton { DefaultPokemonTypeRepository() } }

            // Poke Balls
            bind<PokeBallFactory> { singleton { DefaultPokeBallFactory() } }
            bind<PokeBallRepository> { singleton { DefaultPokeBallRepository() } }

            // Network
            bind<KotlinmonNetworkChannel> { eagerSingleton { SimpleChannelWrapper() } }
            bind<PartyNetworkService> { singleton { DefaultPartyNetworkService() } }
            bind<PokemonToClientDataEncoder> { singleton { PokemonToClientDataEncoder() } }

            // Party
            bind<PartyStorage> { factory { owner: UUID -> DefaultPartyStorage(owner) } }
            bind<PartyStorageRepository> { singleton { DefaultPartyStorageRepository() } }

            // General Utilities
            bind<IEventBus>(tag = Kotlinmon.MODID) { singleton { EventBus(BusBuilder.builder()) } }
            bind<IEventBus>(tag = "forge") { singleton { MinecraftForge.EVENT_BUS } }
            bind<Logger> { singleton { LogManager.getLogger(Kotlinmon.MODID) } }
        }
    }

    inline fun <reified T : Any> inject(tag: Any? = null): DIProperty<T> = container.instance(tag)

    inline fun <reified T : Any> injectProvider(tag: Any? = null): DIProperty<() -> T> = container.provider(tag)

    inline fun <reified A: Any, reified T: Any> injectFactory(tag: Any? = null): DIProperty<(A) -> T> = container.factory(tag)

}