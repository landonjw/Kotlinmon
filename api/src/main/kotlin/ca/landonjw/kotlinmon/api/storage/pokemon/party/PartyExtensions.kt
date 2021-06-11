package ca.landonjw.kotlinmon.api.storage.pokemon.party

import ca.landonjw.kotlinmon.Kotlinmon
import net.minecraft.entity.player.ServerPlayerEntity
import org.kodein.di.instance

object PartyExtensions {

    private val storageRepository: PartyStorageRepository by Kotlinmon.DI.instance()

    val ServerPlayerEntity.party
        get() = storageRepository[this]

}