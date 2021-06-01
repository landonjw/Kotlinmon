package ca.landonjw.kotlinmon.common.network.server.packets.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyStorageRepository
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.common.network.server.PacketToServer
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent

class ThrowPartyPokemon : PacketToServer {

    private var slot: Int

    private val partyStorageRepository: PartyStorageRepository by KotlinmonDI.inject()
    private val pokeBallFactory: PokeBallFactory by KotlinmonDI.inject()

    constructor() {
        slot = -1
    }

    constructor(slot: Int) {
        this.slot = slot
    }

    override fun readPacketData(buf: PacketBuffer) {
        this.slot = buf.readInt()
    }

    override fun writePacketData(buf: PacketBuffer) {
        buf.writeInt(slot)
    }

    override fun processPacket(ctx: NetworkEvent.Context) {
        ctx.enqueueWork {
            if (slot !in 0..5) return@enqueueWork
            val player = ctx.sender ?: return@enqueueWork
            val playerParty = partyStorageRepository[player]
            val selectedPokemon = playerParty[slot] ?: return@enqueueWork

            val pokeBallEntity = pokeBallFactory.createEntity(
                pokeBall = ProvidedPokeBall.PokeBall,
                world = player.serverWorld,
                occupier = selectedPokemon
            )
            pokeBallEntity.setPosition(player.posX, player.posY, player.posZ)
            pokeBallEntity.setDirectionAndMovement(
                player,
                player.rotationPitch - 7,
                player.rotationYawHead,
                0.0f,
                1.5f,
                1.0f
            )
            player.serverWorld.addEntity(pokeBallEntity)
        }
    }

}