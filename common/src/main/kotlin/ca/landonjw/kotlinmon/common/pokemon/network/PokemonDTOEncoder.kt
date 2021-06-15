package ca.landonjw.kotlinmon.common.pokemon.network

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.common.pokemon.PokemonDTO
import net.minecraft.network.PacketBuffer

class PokemonDTOEncoder {

    fun encode(buf: PacketBuffer, pokemon: Pokemon) {
        buf.writeString(pokemon.species.name)
        buf.writeString(pokemon.form.name)
        buf.writeString(pokemon.texture ?: "")
    }

    fun encode(buf: PacketBuffer, pokemon: PokemonDTO) {
        buf.writeString(pokemon.species.name)
        buf.writeString(pokemon.form.name)
        buf.writeString(pokemon.texture ?: "")
    }

}