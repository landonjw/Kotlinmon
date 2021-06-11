package ca.landonjw.kotlinmon.api.storage.pokemon.party

import net.minecraft.entity.player.ServerPlayerEntity
import java.util.*

interface PartyStorageRepository {

    operator fun get(player: ServerPlayerEntity): PartyStorage

    operator fun get(uuid: UUID): PartyStorage

}