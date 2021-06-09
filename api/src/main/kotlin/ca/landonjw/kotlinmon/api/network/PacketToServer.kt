package ca.landonjw.kotlinmon.api.network

import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent

/**
 * A packet that is received and handled by the server.
 *
 * This is the basis of all packets responded by the server in Kotlinmon.
 * To communicate between client and server, see [KotlinmonNetworkChannel]
 *
 * @author landonjw
 */
interface PacketToServer {

    fun readPacketData(buf: PacketBuffer)

    fun writePacketData(buf: PacketBuffer)

    fun processPacket(ctx: NetworkEvent.Context)

}