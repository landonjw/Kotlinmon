package ca.landonjw.kotlinmon.common.network.client

import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent

/**
 * A packet that is received and handled by the client.
 *
 * This is the basis of all packets responded by the client in Kotlinmon.
 * To communicate between client and server, see [KotlinmonNetworkChannel]
 *
 * @author landonjw
 */
interface PacketToClient {

    fun readPacketData(buf: PacketBuffer)

    fun writePacketData(buf: PacketBuffer)

    fun processPacket(ctx: NetworkEvent.Context)

}