package ca.landonjw.kotlinmon.common.network.client.packets.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.player.storage.party.PartyStorageRepository
import ca.landonjw.kotlinmon.client.party.ClientPartySynchronizer
import ca.landonjw.kotlinmon.client.party.ClientPokemonSlot
import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonDecoder
import ca.landonjw.kotlinmon.common.network.client.PacketToClient
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonToClientDataEncoder
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.*

/**
 * Used to synchronize the client's party when they initially connect to a server.
 *
 * @author landonjw
 */
class SynchronizeParty: PacketToClient {

    // Client Side -------------------------------------------------------------
    private val decoder: ClientPokemonDecoder by KotlinmonDI.inject()
    /**
     * Map containing the party Pokemon in their respective slot.
     * Key is slot number, value is the pokemon data.
     */
    private val slotData: MutableList<ClientPokemonSlot> = mutableListOf()
    // -------------------------------------------------------------------------

    // Server Side -------------------------------------------------------------
    private val encoder: PokemonToClientDataEncoder by KotlinmonDI.inject()
    private val storageRepository: PartyStorageRepository by KotlinmonDI.inject()
    private lateinit var playerUUID: UUID
    // -------------------------------------------------------------------------

    constructor()

    constructor(player: ServerPlayerEntity) {
        this.playerUUID = player.uniqueID
    }

    override fun readPacketData(buf: PacketBuffer) {
        val numElements = buf.readInt()
        for (slot in 0 until numElements) {
            val slotIndex = buf.readInt()
            val pokemon = decoder.decode(buf)
            slotData.add(ClientPokemonSlot(slotIndex, pokemon))
        }
    }

    override fun writePacketData(buf: PacketBuffer) {
        val playerStorage = storageRepository[playerUUID]
        buf.writeInt(playerStorage.getAll().size)
        for (slot in 0 until playerStorage.capacity) {
            val pokemon = playerStorage[slot] ?: continue
            buf.writeInt(slot)
            encoder.encode(buf, pokemon)
        }
    }

    override fun processPacket(ctx: NetworkEvent.Context) {
        ClientPartySynchronizer.synchronizeClientParty(this.slotData)
    }

}