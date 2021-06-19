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

    operator fun invoke() = DI.Module(name = "Storage") {
        bind<PartyStorageRepository>() with singleton { DefaultPartyStorageRepository(factory()) }
        bind<PartyStorage>() with factory { owner: UUID -> DefaultPartyStorage(owner, instance()) }
        bind<PartyNetworkService>() with singleton { DefaultPartyNetworkService(instance(), factory(), factory()) }
    }

}