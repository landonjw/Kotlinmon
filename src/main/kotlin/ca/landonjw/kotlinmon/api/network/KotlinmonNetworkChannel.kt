package ca.landonjw.kotlinmon.api.network

import ca.landonjw.kotlinmon.common.network.client.PacketToClient
import ca.landonjw.kotlinmon.common.network.server.PacketToServer
import net.minecraft.entity.player.ServerPlayerEntity

/**
 * Allows for communication between client and server for Kotlinmon packets.
 *
 * @author landonjw
 */
interface KotlinmonNetworkChannel {

    fun sendToServer(packet: PacketToServer)

    fun sendToClient(packet: PacketToClient, target: ServerPlayerEntity)

}