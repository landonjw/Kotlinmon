package ca.landonjw.kotlinmon.server.player.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.player.storage.party.PartyStorage
import ca.landonjw.kotlinmon.api.player.storage.party.PartyStorageRepository
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.ProvidedSpecies
import net.minecraft.entity.player.ServerPlayerEntity
import java.util.*

class DefaultPartyStorageRepository: PartyStorageRepository {

    private val storageProvider: () -> PartyStorage by KotlinmonDI.injectProvider()
    // TODO: Make this cache async later probably
    private val playerStorage: MutableMap<UUID, PartyStorage> = mutableMapOf()

    init {
        // TODO: Remove in-memory data
        val devUUID = UUID.fromString("380df991-f603-344c-a090-369bad2a924a")
        val devParty = storageProvider()

        val pokemonFactory: PokemonFactory by KotlinmonDI.inject()
        devParty[0] = pokemonFactory.create(ProvidedSpecies.values().random().get())

        playerStorage[devUUID] = devParty
    }

    override fun get(player: ServerPlayerEntity): PartyStorage = get(player.uniqueID)

    override fun get(uuid: UUID): PartyStorage {
        val storage = playerStorage[uuid] ?: storageProvider()
        if (playerStorage[uuid] == null) playerStorage[uuid] = storage
        return storage
    }

}