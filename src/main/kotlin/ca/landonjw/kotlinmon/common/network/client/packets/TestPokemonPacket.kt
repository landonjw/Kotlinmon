package ca.landonjw.kotlinmon.common.network.client.packets

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.client.party.ClientPartyStorage
import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonData
import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonDecoder
import ca.landonjw.kotlinmon.common.network.client.ClientPacket
import ca.landonjw.kotlinmon.common.pokemon.network.PokemonToClientDataEncoder
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent

class TestPokemonPacket: ClientPacket {

    private val decoder: ClientPokemonDecoder by KotlinmonDI.inject()
    private val encoder: PokemonToClientDataEncoder by KotlinmonDI.inject()

    private val clientParty: ClientPartyStorage by KotlinmonDI.inject()

    lateinit var clientData: ClientPokemonData
    lateinit private var pokemon: Pokemon

    constructor()

    constructor(pokemon: Pokemon) {
        this.pokemon = pokemon
    }

    override fun readPacketData(buf: PacketBuffer) {
        clientData = decoder.decode(buf)
    }

    override fun writePacketData(buf: PacketBuffer) {
        encoder.encode(buf, pokemon)
    }

    override fun processPacket(ctx: NetworkEvent.Context) {
        ctx.enqueueWork {
            clientParty.set(0, clientData)
        }
    }

}