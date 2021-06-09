package ca.landonjw.kotlinmon.common.network.client.packets.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.client.party.ClientPartyStorage
import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonData
import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonDecoder
import ca.landonjw.kotlinmon.api.network.PacketToClient
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonToClientDataEncoder
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent

/**
 * Updates a party slot on the client.
 *
 * Note that this does not modify the server-side storage, and setting Pokemon that are not stored server-side
 * may cause issues. It is advised to use this purely for synchronization across client-server.
 *
 * @author landonjw
 */
class UpdatePartySlot : PacketToClient {

    // Client Side -------------------------------------------------------------
    private val decoder: ClientPokemonDecoder by KotlinmonDI.inject()
    private var clientPokemon: ClientPokemonData? = null
    private val clientParty: ClientPartyStorage by KotlinmonDI.inject()
    // -------------------------------------------------------------------------

    // Server Side -------------------------------------------------------------
    private val encoder: PokemonToClientDataEncoder by KotlinmonDI.inject()
    private var pokemon: Pokemon? = null
    // -------------------------------------------------------------------------

    // Common ------------------------------------------------------------------
    private var slot: Int = -1
    // -------------------------------------------------------------------------

    /** Used purely for Forge packet decoding. Do not use. */
    constructor()

    /**
     * Primary constructor.
     * This takes a slot and a pokemon (or null) to populate the client's party.
     *
     * @param slot A non-negative integer for the slot that is being updated
     * @param pokemon The pokemon to populate the slot with, or null for empty slot
     */
    constructor(slot: Int, pokemon: Pokemon?) {
        if (slot < 0) throw IllegalArgumentException("party slot must not be negative, received $slot")

        this.slot = slot
        this.pokemon = pokemon
    }

    override fun readPacketData(buf: PacketBuffer) {
        slot = buf.readInt()
        val pokemonNotNull = buf.readBoolean()
        if (pokemonNotNull) {
            clientPokemon = decoder.decode(buf)
        }
    }

    override fun writePacketData(buf: PacketBuffer) {
        buf.writeInt(slot)
        buf.writeBoolean(pokemon != null)
        if (pokemon != null) {
            encoder.encode(buf, pokemon!!)
        }
    }

    override fun processPacket(ctx: NetworkEvent.Context) {
        if (slot < 0) return
        clientParty[slot] = clientPokemon
    }

}