package ca.landonjw.kotlinmon.server.storage

import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyNetworkService
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorage
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorageRepository
import ca.landonjw.kotlinmon.server.storage.party.DefaultPartyNetworkService
import ca.landonjw.kotlinmon.server.storage.party.DefaultPartyStorage
import ca.landonjw.kotlinmon.server.storage.party.DefaultPartyStorageRepository
import org.kodein.di.*
import java.util.*

object StorageModule {

    val bindings = DI.Module(name = "Storage") {
        bind<PartyStorageRepository> { singleton { DefaultPartyStorageRepository(factory()) } }
        bindFactory<UUID, PartyStorage> { owner -> DefaultPartyStorage(owner, instance()) }
        bind<PartyNetworkService> { singleton { DefaultPartyNetworkService(instance(), instance()) } }
    }

}