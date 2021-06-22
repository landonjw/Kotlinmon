package ca.landonjw.kotlinmon.server.command.executors

import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyStorageRepository
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.ProvidedSpecies
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import java.util.*

class PopulateDevPartyCommand(
    private val partyRepository: PartyStorageRepository,
    private val pokemonFactory: PokemonFactory
) : KotlinmonCommand {

    override fun run(context: CommandContext<CommandSource>): Int {
        val devUUID = UUID.fromString("380df991-f603-344c-a090-369bad2a924a")
        val devParty = partyRepository[devUUID]
        devParty[0] = pokemonFactory.create(ProvidedSpecies.values().random().get())
        devParty[1] = pokemonFactory.create(ProvidedSpecies.values().random().get())
        devParty[2] = pokemonFactory.create(ProvidedSpecies.values().random().get())
        devParty[3] = pokemonFactory.create(ProvidedSpecies.values().random().get())
        devParty[4] = pokemonFactory.create(ProvidedSpecies.values().random().get())
        devParty[5] = pokemonFactory.create(ProvidedSpecies.values().random().get())
        return 0
    }

    override fun register(dispatcher: CommandDispatcher<CommandSource>) {
        val command = Commands.literal("populateparty")
            .executes(this)
            .requires { it.hasPermission(0) }

        dispatcher.register(command)
    }

}