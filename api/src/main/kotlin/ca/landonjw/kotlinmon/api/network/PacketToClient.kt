package ca.landonjw.kotlinmon.api.network

/**
 * A packet that is to be distributed to a client.
 *
 * This is the basis of all packets sent to clients in Kotlinmon.
 * To communicate between client and server, see [KotlinmonNetworkChannel]
 *
 * @author landonjw
 */
interface PacketToClient : Packet