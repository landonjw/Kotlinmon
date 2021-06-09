package ca.landonjw.kotlinmon.server.command

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.server.command.arguments.SpeciesArgument
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.entity.player.ServerPlayerEntity

// TODO: Refactor
class CreatePokemon : Command<CommandSource> {

    private val pokemonFactory: PokemonFactory by KotlinmonDI.inject()

    override fun run(context: CommandContext<CommandSource>?): Int {
        // Get player or return out early
        val player = context?.source?.entity as? ServerPlayerEntity ?: return 0

        val species = context.getArgument("species", PokemonSpecies::class.java)
        val pokemon = pokemonFactory.create(species)

        // Create entity and spawn it at player's position
        val pokemonEntity = pokemonFactory.createEntity(pokemon, player.world)
        pokemonEntity.asMinecraftEntity().setPosition(player.posX, player.posY, player.posZ)
        player.world.addEntity(pokemonEntity.asMinecraftEntity())
        return 0
    }

    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSource>) {
            val command = Commands.literal("pokecreate")
                .then(
                    Commands.argument("species", SpeciesArgument())
                        .executes(CreatePokemon())
                )
                .requires { it.hasPermissionLevel(0) }

            dispatcher.register(command)
        }
    }

}