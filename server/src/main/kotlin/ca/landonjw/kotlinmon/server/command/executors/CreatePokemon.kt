package ca.landonjw.kotlinmon.server.command.executors

import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.server.command.arguments.SpeciesArgument
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.entity.player.ServerPlayerEntity

// TODO: Refactor
class CreatePokemon(
    private val pokemonFactory: PokemonFactory,
    private val speciesArgument: SpeciesArgument
) : KotlinmonCommand {

    override fun run(context: CommandContext<CommandSource>?): Int {
        // Get player or return out early
        val player = context?.source?.entity as? ServerPlayerEntity ?: return 0

        val species = context.getArgument("species", PokemonSpecies::class.java)
        val pokemon = pokemonFactory.create(species)

        // Create entity and spawn it at player's position
        val pokemonEntity = pokemonFactory.createEntity(pokemon, player.level)
        pokemonEntity.asMinecraftEntity().setPos(player.x, player.y, player.z)
        player.level.addFreshEntity(pokemonEntity.asMinecraftEntity())
        return 0
    }

    override fun register(dispatcher: CommandDispatcher<CommandSource>) {
        val command = Commands.literal("pokecreate")
            .then(
                Commands.argument("species", speciesArgument)
                    .executes(this)
            )
            .requires { it.hasPermission(0) }

        dispatcher.register(command)
    }

}