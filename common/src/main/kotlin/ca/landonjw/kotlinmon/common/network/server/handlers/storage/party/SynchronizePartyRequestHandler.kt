package ca.landonjw.kotlinmon.common.network.server.handlers.storage.party

import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.api.network.PacketHandler
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorage
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorageRepository
import ca.landonjw.kotlinmon.common.network.packets.party.UpdateParty
import ca.landonjw.kotlinmon.common.network.packets.party.SynchronizePartyRequest
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.fml.network.NetworkEvent

class SynchronizePartyRequestHandler(
    private val networkChannel: KotlinmonNetworkChannel,
    private val partyStorageRepository: PartyStorageRepository,
    private val updatePartyFactory: (PartyStorage) -> UpdateParty
) : PacketHandler<SynchronizePartyRequest> {

    override fun handle(packet: SynchronizePartyRequest, ctx: NetworkEvent.Context) {
        ctx.enqueueWork {
            // Synchronize the party for the player requesting
            val sender: ServerPlayerEntity = ctx.sender ?: return@enqueueWork
            val party = partyStorageRepository[sender]
            networkChannel.sendToClient(updatePartyFactory(party), sender)
        }
    }

}