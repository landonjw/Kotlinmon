package ca.landonjw.kotlinmon.common.pokemon.network

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.common.pokemon.PokemonDTO
import net.minecraft.network.PacketBuffer

class PokemonDTOEncoder {

    fun encode(buf: PacketBuffer, pokemon: Pokemon) {
        buf.writeUtf(pokemon.species.name)
        buf.writeUtf(pokemon.form.name)
        buf.writeUtf(pokemon.texture ?: "")
    }

    fun encode(buf: PacketBuffer, pokemon: PokemonDTO) {
        buf.writeUtf(pokemon.species.name)
        buf.writeUtf(pokemon.form.name)
        buf.writeUtf(pokemon.texture ?: "")
    }

}