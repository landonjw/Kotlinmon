package ca.landonjw.kotlinmon.api.player.storage.pokemon.party

import net.minecraft.entity.player.ServerPlayerEntity
import java.util.*

interface PartyStorageRepository {

    operator fun get(player: ServerPlayerEntity): ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyStorage

    operator fun get(uuid: UUID): ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyStorage

}