package ca.landonjw.kotlinmon.common.network.server.packets.storage.party

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorageRepository
import ca.landonjw.kotlinmon.common.network.client.packets.storage.party.UpdateParty
import ca.landonjw.kotlinmon.api.network.PacketToServer
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import org.kodein.di.instance

class SynchronizePartyRequest : PacketToServer {

    private val networkChannel: KotlinmonNetworkChannel by Kotlinmon.DI.instance()
    private val partyStorageRepository: PartyStorageRepository by Kotlinmon.DI.instance()

    override fun readPacketData(buf: PacketBuffer) {
    }

    override fun writePacketData(buf: PacketBuffer) {
    }

    override fun processPacket(ctx: NetworkEvent.Context) {
        ctx.enqueueWork {
            // Synchronize the party for the player requesting
            val sender: ServerPlayerEntity = ctx.sender ?: return@enqueueWork
            val party = partyStorageRepository[sender]
            networkChannel.sendToClient(UpdateParty(party), sender)
        }
    }

}