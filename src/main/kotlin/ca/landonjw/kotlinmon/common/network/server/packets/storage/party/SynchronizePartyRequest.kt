package ca.landonjw.kotlinmon.common.network.server.packets.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.common.network.client.packets.storage.party.SynchronizeParty
import ca.landonjw.kotlinmon.common.network.server.PacketToServer
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent

class SynchronizePartyRequest : PacketToServer {

    private val networkChannel: KotlinmonNetworkChannel by KotlinmonDI.inject()

    override fun readPacketData(buf: PacketBuffer) {
    }

    override fun writePacketData(buf: PacketBuffer) {
    }

    override fun processPacket(ctx: NetworkEvent.Context) {
        ctx.enqueueWork {
            // Synchronize the party for the player requesting
            val sender: ServerPlayerEntity = ctx.sender ?: return@enqueueWork
            networkChannel.sendToClient(SynchronizeParty(sender), sender)
        }
    }

}