package ca.landonjw.kotlinmon.api.network

import ca.landonjw.kotlinmon.common.network.client.ClientPacket
import ca.landonjw.kotlinmon.common.network.server.ServerPacket
import net.minecraft.entity.player.ServerPlayerEntity

/**
 * Allows for communication between client and server for Kotlinmon packets.
 *
 * @author landonjw
 */
interface KotlinmonNetworkChannel {

    fun sendToServer(packet: ServerPacket)

    fun sendToClient(packet: ClientPacket, target: ServerPlayerEntity)

}