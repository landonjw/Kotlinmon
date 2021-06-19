package ca.landonjw.kotlinmon.common.network.packets.party

import ca.landonjw.kotlinmon.api.network.PacketToServer
import net.minecraft.network.PacketBuffer

class SynchronizePartyRequest : PacketToServer {

    override fun encodeData(buf: PacketBuffer) { }

    override fun decodeData(buf: PacketBuffer) { }

}