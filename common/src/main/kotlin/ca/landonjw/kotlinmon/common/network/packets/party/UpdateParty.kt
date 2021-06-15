package ca.landonjw.kotlinmon.common.network.packets.party

import ca.landonjw.kotlinmon.api.network.PacketToClient
import ca.landonjw.kotlinmon.api.storage.StorageSlot
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorage
import ca.landonjw.kotlinmon.common.pokemon.PokemonDTO
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonDTODecoder
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonDTOEncoder
import ca.landonjw.kotlinmon.common.pokemon.toDTO
import net.minecraft.network.PacketBuffer

/**
 * Used to synchronize the client's party when they initially connect to a server.
 *
 * @author landonjw
 */
class UpdateParty : PacketToClient {

    private val encoder: PokemonDTOEncoder
    private val decoder: PokemonDTODecoder

    lateinit var slotData: List<StorageSlot<PokemonDTO>>
        private set

    constructor(
        party: PartyStorage,
        encoder: PokemonDTOEncoder,
        decoder: PokemonDTODecoder
    ) : this(encoder, decoder) {
        val slots: MutableList<StorageSlot<PokemonDTO>> = mutableListOf()
        for (slot in 0 until party.capacity) {
            val pokemon = party[slot] ?: continue
            slots.add(StorageSlot(slot, pokemon.toDTO()))
        }
    }

    /** Default constructor for internal use. Do not use. */
    constructor(
        encoder: PokemonDTOEncoder,
        decoder: PokemonDTODecoder
    ) {
        this.encoder = encoder
        this.decoder = decoder
    }

    override fun encodeData(buf: PacketBuffer) {
        buf.writeInt(slotData.size)
        for (slot in slotData) {
            buf.writeInt(slot.index)
            encoder.encode(buf, slot.item)
        }
    }

    override fun decodeData(buf: PacketBuffer) {
        val slots: MutableList<StorageSlot<PokemonDTO>> = mutableListOf()
        val numElements = buf.readInt()
        for (slot in 0 until numElements) {
            val slotIndex = buf.readInt()
            val pokemon: PokemonDTO = decoder.decode(buf)
            slots.add(StorageSlot(slotIndex, pokemon))
        }
        this.slotData = slots
    }

}