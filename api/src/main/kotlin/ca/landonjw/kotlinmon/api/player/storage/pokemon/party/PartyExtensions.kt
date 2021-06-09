package ca.landonjw.kotlinmon.api.player.storage.pokemon.party

import ca.landonjw.kotlinmon.KotlinmonAPI
import net.minecraft.entity.player.ServerPlayerEntity
import org.kodein.di.instance

object PartyExtensions {
    private val storageRepository: PartyStorageRepository by KotlinmonAPI.DI.instance()

    val ServerPlayerEntity.party
        get() = storageRepository[this]
}