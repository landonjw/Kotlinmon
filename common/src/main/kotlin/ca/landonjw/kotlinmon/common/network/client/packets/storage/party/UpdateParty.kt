package ca.landonjw.kotlinmon.common.network.client.packets.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyStorage
import ca.landonjw.kotlinmon.client.party.ClientPartySynchronizer
import ca.landonjw.kotlinmon.client.party.ClientPokemonSlot
import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonDecoder
import ca.landonjw.kotlinmon.common.network.client.PacketToClient
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonToClientDataEncoder
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent

/**
 * Used to synchronize the client's party when they initially connect to a server.
 *
 * @author landonjw
 */
class UpdateParty: PacketToClient {

    // Client Side -------------------------------------------------------------
    private val decoder: ClientPokemonDecoder by KotlinmonDI.inject()
    private val slotData: MutableList<ClientPokemonSlot> = mutableListOf()
    // -------------------------------------------------------------------------

    // Server Side -------------------------------------------------------------
    private val encoder: PokemonToClientDataEncoder by KotlinmonDI.inject()
    private lateinit var serverParty: PartyStorage
    // -------------------------------------------------------------------------

    constructor()

    constructor(party: PartyStorage) {
        this.serverParty = party
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
        buf.writeInt(serverParty.getAll().size)
        for (slot in 0 until serverParty.capacity) {
            val pokemon = serverParty[slot] ?: continue
            buf.writeInt(slot)
            encoder.encode(buf, pokemon)
        }
    }

    override fun processPacket(ctx: NetworkEvent.Context) {
        ClientPartySynchronizer.synchronizeClientParty(this.slotData)
    }

}