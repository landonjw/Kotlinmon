package ca.landonjw.kotlinmon.server.player.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.player.storage.party.PartyStorage
import ca.landonjw.kotlinmon.api.player.storage.party.PartyStorageRepository
import net.minecraft.entity.player.ServerPlayerEntity
import java.util.*

class DefaultPartyStorageRepository: PartyStorageRepository {

    // TODO: Make this cache async later maybe?
    private val storageProvider: () -> PartyStorage by KotlinmonDI.injectProvider()
    private val playerStorage: MutableMap<UUID, PartyStorage> = mutableMapOf()

    override fun get(player: ServerPlayerEntity): PartyStorage = get(player.uniqueID)

    override fun get(uuid: UUID): PartyStorage {
        val storage = playerStorage[uuid] ?: storageProvider()
        if (playerStorage[uuid] == null) playerStorage[uuid] = storage
        return storage
    }

}