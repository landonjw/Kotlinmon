package ca.landonjw.kotlinmon.common.network.server.packets.storage.party

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorageRepository
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.api.network.PacketToServer
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import org.kodein.di.instance

class ThrowPartyPokemon : PacketToServer {

    private var slot: Int

    private val partyStorageRepository: PartyStorageRepository by Kotlinmon.DI.instance()
    private val pokeBallFactory: PokeBallFactory by Kotlinmon.DI.instance()

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

            // Get the pokemon in the player's selected party.
            val playerParty = partyStorageRepository[player]
            val selectedPokemon = playerParty[slot] ?: return@enqueueWork

            // Create the entity.
            val pokeBallEntity = pokeBallFactory.createEntity(
                pokeBall = ProvidedPokeBall.PokeBall,
                world = player.serverWorld,
                occupant = selectedPokemon
            )
            // Set it's motion to fly forwards from the player.
            pokeBallEntity.asMinecraftEntity().apply {
                setPosition(player.posX, player.posY, player.posZ)
                setDirectionAndMovement(player, player.rotationPitch - 7, player.rotationYawHead, 0.0f, 1.5f, 1.0f)
            }
            // Spawn it in the world.
            player.serverWorld.addEntity(pokeBallEntity.asMinecraftEntity())
        }
    }

}