package ca.landonjw.kotlinmon.common.pokemon.network

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import net.minecraft.network.PacketBuffer

class PokemonToClientDataEncoder {

    fun encode(buf: PacketBuffer, pokemon: Pokemon) {
        buf.writeString(pokemon.species.name)
        buf.writeString(pokemon.form.name)
        buf.writeString(pokemon.texture ?: "")
    }

}