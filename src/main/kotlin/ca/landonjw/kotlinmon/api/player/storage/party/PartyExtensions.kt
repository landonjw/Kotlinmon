package ca.landonjw.kotlinmon.api.player.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import net.minecraft.entity.player.ServerPlayerEntity

object PartyExtensions {
    private val storageRepository: PartyStorageRepository by KotlinmonDI.inject()

    val ServerPlayerEntity.party
        get() = storageRepository[this]
}