package ca.landonjw.kotlinmon.server.command

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyExtensions.party
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.ProvidedSpecies
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands

class SetToPartyCommand: Command<CommandSource> {

    private val pokemonFactory: PokemonFactory by KotlinmonDI.inject()

    override fun run(context: CommandContext<CommandSource>): Int {
        val player = context.source.asPlayer()
        val bulbasaur = pokemonFactory.create(species = ProvidedSpecies.Bulbasaur)
        player.party.set(0, bulbasaur)
        player.party.set(1, null)
        return 0
    }

    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSource>) {
            val command = Commands.literal("settoparty")
                .executes(SetToPartyCommand())
                .requires { it.hasPermissionLevel(0) }

            dispatcher.register(command)
        }
    }

}