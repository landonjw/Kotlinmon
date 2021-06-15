package ca.landonjw.kotlinmon.common.network

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.api.network.Packet
import ca.landonjw.kotlinmon.api.network.PacketToClient
import ca.landonjw.kotlinmon.api.network.PacketToServer
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorage
import ca.landonjw.kotlinmon.common.network.client.handlers.storage.party.UpdatePartyHandler
import ca.landonjw.kotlinmon.common.network.client.handlers.storage.party.UpdatePartySlotHandler
import ca.landonjw.kotlinmon.common.network.packets.party.SynchronizePartyRequest
import ca.landonjw.kotlinmon.common.network.packets.party.ThrowPartyPokemon
import ca.landonjw.kotlinmon.common.network.packets.party.UpdateParty
import ca.landonjw.kotlinmon.common.network.packets.party.UpdatePartySlot
import ca.landonjw.kotlinmon.common.network.server.handlers.storage.party.SynchronizePartyRequestHandler
import ca.landonjw.kotlinmon.common.network.server.handlers.storage.party.ThrowPartyPokemonHandler
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.simple.SimpleChannel
import org.kodein.di.*

object NetworkModule {

    operator fun invoke() = DI.Module(name = "Network") {
        // Channel
        bind<KotlinmonNetworkChannel>() with singleton { instance<SimpleChannelWrapper>() }
        bind<SimpleChannelWrapper>() with singleton { SimpleChannelWrapper(instance(), instance(), instance()) }
        bind<SimpleChannel>() with singleton {
            NetworkRegistry.ChannelBuilder.named(ResourceLocation(Kotlinmon.MOD_ID, "main"))
                .networkProtocolVersion { "1.0.0" }
                .clientAcceptedVersions { it == "1.0.0" }
                .serverAcceptedVersions { it == "1.0.0" }
                .simpleChannel()
        }

        // Packet Handlers
        bind<UpdatePartyHandler>() with singleton { UpdatePartyHandler(instance()) }
        bind<UpdatePartySlotHandler>() with singleton { UpdatePartySlotHandler(instance()) }
        bind<SynchronizePartyRequestHandler>() with singleton { SynchronizePartyRequestHandler(instance(), instance(), factory()) }
        bind<ThrowPartyPokemonHandler>() with singleton { ThrowPartyPokemonHandler(instance(), instance()) }

        // Packet Factories
        bind<SynchronizePartyRequest>() with provider { SynchronizePartyRequest() }
        bind<ThrowPartyPokemon>() with factory { slot: Int -> ThrowPartyPokemon(slot) }
        bind<UpdateParty>() with factory { party: PartyStorage -> UpdateParty(party, instance(), instance()) }
        bind<UpdatePartySlot>() with factory { params: UpdatePartySlotParams -> UpdatePartySlot(params.slot, params.pokemon, instance(), instance()) }

        // Packet Registrations
        bind<List<(SimpleChannelWrapper) -> Unit>>() with singleton {
            listOf(
                // Packets to Client
                { it.registerClientPacket(UpdateParty::class.java) { UpdateParty(instance(), instance()) } },
                { it.registerClientPacket(UpdatePartySlot::class.java) { UpdatePartySlot(instance(), instance()) } },

                // Packets to Server
                { it.registerServerPacket(ThrowPartyPokemon::class.java) { ThrowPartyPokemon(-1) } },
                { it.registerServerPacket(SynchronizePartyRequest::class.java) { SynchronizePartyRequest() } }
            )
        }
    }

}

data class UpdatePartySlotParams(
    val slot: Int,
    val pokemon: Pokemon?
)