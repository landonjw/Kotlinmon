package ca.landonjw.kotlinmon.api.network

/**
 * A packet that is to be distributed to the server.
 *
 * This is the basis of all packets sent to the server in Kotlinmon.
 * To communicate between client and server, see [KotlinmonNetworkChannel]
 *
 * @author landonjw
 */
interface PacketToServer : Packet