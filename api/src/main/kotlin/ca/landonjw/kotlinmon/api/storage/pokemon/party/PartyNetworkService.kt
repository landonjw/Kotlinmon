package ca.landonjw.kotlinmon.api.storage.pokemon.party

import net.minecraft.entity.player.ServerPlayerEntity

interface PartyNetworkService {

    fun sendParty(player: ServerPlayerEntity, party: PartyStorage)

    fun updatePartySlot(player: ServerPlayerEntity, party: PartyStorage, slot: Int)

}