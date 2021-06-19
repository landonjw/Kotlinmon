package ca.landonjw.kotlinmon.server.command.executors

import ca.landonjw.kotlinmon.api.storage.pokemon.party.PartyExtensions.party
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.ProvidedSpecies
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands

class AddToPartyCommand(
    private val pokemonFactory: PokemonFactory
) : KotlinmonCommand {

    override fun run(context: CommandContext<CommandSource>): Int {
        val player = context.source.asPlayer()
        val bulbasaur = pokemonFactory.create(species = ProvidedSpecies.Bulbasaur)
        player.party.add(bulbasaur)
        return 0
    }

    override fun register(dispatcher: CommandDispatcher<CommandSource>) {
        val command = Commands.literal("addtoparty")
            .executes(this)
            .requires { it.hasPermissionLevel(0) }

        dispatcher.register(command)
    }

}