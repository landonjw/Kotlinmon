package ca.landonjw.kotlinmon.server.storage.party

import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyNetworkService
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorage
import ca.landonjw.kotlinmon.common.network.UpdatePartySlotParams
import ca.landonjw.kotlinmon.common.network.packets.party.UpdateParty
import ca.landonjw.kotlinmon.common.network.packets.party.UpdatePartySlot
import net.minecraft.entity.player.ServerPlayerEntity

class DefaultPartyNetworkService(
    private val networkChannel: KotlinmonNetworkChannel,
    private val updatePartyPacketFactory: (PartyStorage) -> UpdateParty,
    private val updatePartySlotPacketFactory: (UpdatePartySlotParams) -> UpdatePartySlot
) : PartyNetworkService {

    override fun sendParty(player: ServerPlayerEntity, party: PartyStorage) {
        networkChannel.sendToClient(updatePartyPacketFactory(party), player)
    }

    override fun updatePartySlot(player: ServerPlayerEntity, party: PartyStorage, slot: Int) {
        networkChannel.sendToClient(updatePartySlotPacketFactory(UpdatePartySlotParams(slot, party[slot])), player)
    }

}