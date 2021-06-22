package ca.landonjw.kotlinmon.server.storage.party

import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorage
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorageRepository
import net.minecraft.entity.player.ServerPlayerEntity
import java.util.*

class DefaultPartyStorageRepository(
    private val storageFactory: (UUID) -> PartyStorage
): PartyStorageRepository {

    // TODO: Make this cache async later probably
    private val playerStorage: MutableMap<UUID, PartyStorage> = mutableMapOf()

    override fun get(player: ServerPlayerEntity): PartyStorage = get(player.uuid)

    override fun get(uuid: UUID): PartyStorage {
        val storage = playerStorage[uuid] ?: storageFactory(uuid)
        if (playerStorage[uuid] == null) playerStorage[uuid] = storage
        return storage
    }

}