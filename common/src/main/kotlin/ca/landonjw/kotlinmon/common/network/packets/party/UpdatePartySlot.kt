package ca.landonjw.kotlinmon.common.network.packets.party

import ca.landonjw.kotlinmon.api.network.PacketToClient
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.common.pokemon.PokemonDTO
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonDTODecoder
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonDTOEncoder
import ca.landonjw.kotlinmon.common.pokemon.toDTO
import net.minecraft.network.PacketBuffer

/**
 * Updates a party slot on the client.
 *
 * Note that this does not modify the server-side storage, and setting Pokemon that are not stored server-side
 * may cause issues. It is advised to use this purely for synchronization across client-server.
 *
 * @author landonjw
 */
class UpdatePartySlot : PacketToClient {

    private val encoder: PokemonDTOEncoder
    private val decoder: PokemonDTODecoder

    var slot: Int
        private set

    var pokemonDTO: PokemonDTO? = null
        private set

    constructor(
        slot: Int,
        pokemon: Pokemon?,
        encoder: PokemonDTOEncoder,
        decoder: PokemonDTODecoder
    ) {
        this.slot = slot
        this.pokemonDTO = pokemon?.toDTO()
        this.encoder = encoder
        this.decoder = decoder
    }

    /** Default constructor for internal use. Do not use. */
    constructor(
        encoder: PokemonDTOEncoder,
        decoder: PokemonDTODecoder
    ) : this(-1, null, encoder, decoder)

    override fun encodeData(buf: PacketBuffer) {
        buf.writeInt(slot)
        buf.writeBoolean(pokemonDTO != null)

        val pokemon = pokemonDTO ?: return
        encoder.encode(buf, pokemon)
    }

    override fun decodeData(buf: PacketBuffer) {
        slot = buf.readInt()
        val pokemonNotNull = buf.readBoolean()
        if (pokemonNotNull) {
            pokemonDTO = decoder.decode(buf)
        }
    }

}