package ca.landonjw.kotlinmon.server.network.party

import ca.landonjw.kotlinmon.api.network.PacketHandler
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorageRepository
import ca.landonjw.kotlinmon.common.network.packets.party.ThrowPartyPokemon
import net.minecraftforge.fml.network.NetworkEvent

class ThrowPartyPokemonHandler(
    private val partyStorageRepository: PartyStorageRepository,
    private val pokeBallFactory: PokeBallFactory
) : PacketHandler<ThrowPartyPokemon> {

    override fun handle(packet: ThrowPartyPokemon, ctx: NetworkEvent.Context) {
        ctx.enqueueWork {
            if (packet.slot !in 0..5) return@enqueueWork
            val player = ctx.sender ?: return@enqueueWork

            // Get the pokemon in the player's selected party.
            val playerParty = partyStorageRepository[player]
            val selectedPokemon = playerParty[packet.slot] ?: return@enqueueWork

            // Create the entity.
            val pokeBallEntity = pokeBallFactory.createEntity(
                pokeBall = ProvidedPokeBall.PokeBall,
                world = player.serverWorld,
                occupant = selectedPokemon
            )
            // Set it's motion to fly forwards from the player.
            pokeBallEntity.asMinecraftEntity().apply {
                setPosition(player.posX, player.posY, player.posZ)
                setDirectionAndMovement(player, player.rotationPitch - 7, player.rotationYawHead, 0.0f, 1.5f, 1.0f)
            }
            // Spawn it in the world.
            player.serverWorld.addEntity(pokeBallEntity.asMinecraftEntity())
        }
    }

}