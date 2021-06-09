package ca.landonjw.kotlinmon.api.player.storage.pokemon.party

import net.minecraft.entity.player.ServerPlayerEntity

object PartyExtensions {
    private val storageRepository: PartyStorageRepository = TODO("figure out API dependency injection")

    val ServerPlayerEntity.party
        get() = storageRepository[this]
}