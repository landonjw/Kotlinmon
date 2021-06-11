package ca.landonjw.kotlinmon.server.storage.party

import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyNetworkService
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorageRepository
import ca.landonjw.kotlinmon.common.network.client.packets.storage.party.UpdateParty
import ca.landonjw.kotlinmon.common.network.client.packets.storage.party.UpdatePartySlot
import net.minecraft.entity.player.ServerPlayerEntity

class DefaultPartyNetworkService(
    private val networkChannel: KotlinmonNetworkChannel,
    private val partyStorageRepository: PartyStorageRepository
) : PartyNetworkService {

    override fun sendParty(player: ServerPlayerEntity) {
        val party = partyStorageRepository[player]
        networkChannel.sendToClient(UpdateParty(party), player)
    }

    override fun updatePartySlot(player: ServerPlayerEntity, slot: Int) {
        val party = partyStorageRepository[player]
        networkChannel.sendToClient(UpdatePartySlot(slot, party[slot]), player)
    }

}