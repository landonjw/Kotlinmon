package ca.landonjw.kotlinmon.server.player.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyStorage
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyStorageRepository
import net.minecraft.entity.player.ServerPlayerEntity
import java.util.*

class DefaultPartyStorageRepository: PartyStorageRepository {

    private val storageProvider: (UUID) -> PartyStorage by KotlinmonDI.injectFactory()
    // TODO: Make this cache async later probably
    private val playerStorage: MutableMap<UUID, PartyStorage> = mutableMapOf()

    override fun get(player: ServerPlayerEntity): PartyStorage = get(player.uniqueID)

    override fun get(uuid: UUID): PartyStorage {
        val storage = playerStorage[uuid] ?: storageProvider(uuid)
        if (playerStorage[uuid] == null) playerStorage[uuid] = storage
        return storage
    }

}