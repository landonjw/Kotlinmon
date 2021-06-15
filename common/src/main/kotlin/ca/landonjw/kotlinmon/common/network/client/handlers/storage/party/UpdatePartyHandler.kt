package ca.landonjw.kotlinmon.common.network.client.handlers.storage.party

import ca.landonjw.kotlinmon.api.network.PacketHandler
import ca.landonjw.kotlinmon.client.party.ClientPartySynchronizer
import ca.landonjw.kotlinmon.common.network.packets.party.UpdateParty
import net.minecraftforge.fml.network.NetworkEvent

class UpdatePartyHandler(
    private val clientPartySynchronizer: ClientPartySynchronizer
) : PacketHandler<UpdateParty> {

    override fun handle(packet: UpdateParty, ctx: NetworkEvent.Context) {
        clientPartySynchronizer.synchronizeClientParty(packet.slotData)
    }

}