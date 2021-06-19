package ca.landonjw.kotlinmon.common.network.packets.party

import ca.landonjw.kotlinmon.api.network.PacketToServer
import net.minecraft.network.PacketBuffer

class ThrowPartyPokemon(
    slot: Int
) : PacketToServer {

    var slot: Int
        private set

    init {
        this.slot = slot
    }

    override fun encodeData(buf: PacketBuffer) {
        buf.writeInt(slot)
    }

    override fun decodeData(buf: PacketBuffer) {
        this.slot = buf.readInt()
    }

}