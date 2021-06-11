package ca.landonjw.kotlinmon.api.storage.pokemon.party

import net.minecraft.entity.player.ServerPlayerEntity

interface PartyNetworkService {

    fun sendParty(player: ServerPlayerEntity)

    fun updatePartySlot(player: ServerPlayerEntity, slot: Int)

}