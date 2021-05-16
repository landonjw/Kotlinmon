package ca.landonjw.kotlinmon.server.command

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.api.player.storage.party.PartyStorageRepository
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.ProvidedSpecies
import ca.landonjw.kotlinmon.common.network.client.packets.storage.party.SynchronizeParty
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands

class SendPokemonCommand: Command<CommandSource> {

    private val pokemonFactory: PokemonFactory by KotlinmonDI.inject()
    private val speciesRepository: PokemonSpeciesRepository by KotlinmonDI.inject()

    private val partyStorageRepository: PartyStorageRepository by KotlinmonDI.inject()

    private val network: KotlinmonNetworkChannel by KotlinmonDI.inject()

    override fun run(context: CommandContext<CommandSource>): Int {
        val player = context.source.asPlayer()

        val storage = partyStorageRepository[player]

        val bulbasaurSpecies = speciesRepository[ProvidedSpecies.Bulbasaur]
        val bulbasaur = pokemonFactory.create(species = bulbasaurSpecies)

        val ivysaurSpecies = speciesRepository[ProvidedSpecies.Ivysaur]
        val ivysaur = pokemonFactory.create(species = ivysaurSpecies)

        val venusaurSpecies = speciesRepository[ProvidedSpecies.Venusaur]
        val venusaur = pokemonFactory.create(species = venusaurSpecies)

        storage[0] = bulbasaur
        storage[1] = ivysaur
        storage[3] = venusaur

        try{
            network.sendToClient(SynchronizeParty(player), player)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSource>) {
            val command = Commands.literal("sendpokemon")
                .executes(SendPokemonCommand())
                .requires { it.hasPermissionLevel(0) }

            dispatcher.register(command)
        }
    }

}