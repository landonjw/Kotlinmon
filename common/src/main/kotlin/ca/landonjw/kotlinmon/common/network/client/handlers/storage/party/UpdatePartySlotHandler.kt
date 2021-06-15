package ca.landonjw.kotlinmon.common.network.client.handlers.storage.party

import ca.landonjw.kotlinmon.api.network.PacketHandler
import ca.landonjw.kotlinmon.client.party.ClientPartyStorage
import ca.landonjw.kotlinmon.common.network.packets.party.UpdatePartySlot
import net.minecraftforge.fml.network.NetworkEvent

class UpdatePartySlotHandler(
    private val clientPartyStorage: ClientPartyStorage
) : PacketHandler<UpdatePartySlot> {

    override fun handle(packet: UpdatePartySlot, ctx: NetworkEvent.Context) {
        clientPartyStorage[packet.slot] = packet.pokemonDTO
    }

}