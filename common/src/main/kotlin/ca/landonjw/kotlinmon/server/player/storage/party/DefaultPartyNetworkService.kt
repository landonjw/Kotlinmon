package ca.landonjw.kotlinmon.server.player.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyNetworkService
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyStorageRepository
import ca.landonjw.kotlinmon.common.network.client.packets.storage.party.UpdateParty
import ca.landonjw.kotlinmon.common.network.client.packets.storage.party.UpdatePartySlot
import net.minecraft.entity.player.ServerPlayerEntity

class DefaultPartyNetworkService : PartyNetworkService {

    private val networkChannel: KotlinmonNetworkChannel by KotlinmonDI.inject()
    private val partyRepository: PartyStorageRepository by KotlinmonDI.inject()

    override fun sendParty(player: ServerPlayerEntity) {
        val party = partyRepository[player]
        networkChannel.sendToClient(UpdateParty(party), player)
    }

    override fun updatePartySlot(player: ServerPlayerEntity, slot: Int) {
        val party = partyRepository[player]
        networkChannel.sendToClient(UpdatePartySlot(slot, party[slot]), player)
    }

}