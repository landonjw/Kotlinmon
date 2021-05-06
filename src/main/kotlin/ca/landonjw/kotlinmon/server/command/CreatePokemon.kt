package ca.landonjw.kotlinmon.server.command

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntity
import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.entity.player.ServerPlayerEntity

// TODO: Refactor
class CreatePokemon : Command<CommandSource> {

    val speciesRepository: PokemonSpeciesRepository by KotlinmonDI.inject()
    val pokemonFactory: PokemonFactory by KotlinmonDI.inject()

    override fun run(context: CommandContext<CommandSource>?): Int {
        // Get player or return out early
        val player = context?.source?.entity as? ServerPlayerEntity ?: return 0

        // Get species input from argument and parse into species
        val speciesStr = context.getArgument("species", String::class.java)

        speciesRepository

        val species = speciesRepository[speciesStr] ?: return 0

        val pokemon = PokemonEntity(EntityRegistry.POKEMON.get(), player.world).apply {
            setPokemon(pokemonFactory.create(species))
        }
        pokemon.setPosition(player.posX, player.posY, player.posZ)

        player.world.addEntity(pokemon)
        return 0
    }

}