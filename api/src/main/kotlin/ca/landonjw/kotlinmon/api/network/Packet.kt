package ca.landonjw.kotlinmon.api.network

import net.minecraft.network.PacketBuffer

interface Packet {

    /**
     * Encodes the information of the packet into a packet buffer.
     *
     * @param buf The buffer to encode data into.
     */
    fun encodeData(buf: PacketBuffer)

    /**
     * Decodes the data for the packet from a packet buffer.
     *
     * @param buf The buffer to decode data from.
     */
    fun decodeData(buf: PacketBuffer)

}