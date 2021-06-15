package ca.landonjw.kotlinmon.api.network

import net.minecraftforge.fml.network.NetworkEvent

/**
 * The handler for a given [Packet] type.
 *
 * @param T The type of packet that is to be handled.
 * @author landonjw
 */
interface PacketHandler<T : Packet> {

    /**
     * The logic for handling the packet data.
     */
    fun handle(packet: T, ctx: NetworkEvent.Context)

}