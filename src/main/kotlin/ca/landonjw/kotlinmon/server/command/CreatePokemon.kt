package ca.landonjw.kotlinmon.server.command

import ca.landonjw.kotlinmon.common.init.EntityRegistry
import ca.landonjw.kotlinmon.common.pokemon.PokemonEntity
import ca.landonjw.kotlinmon.common.pokemon.data.species.SpeciesRegistry
import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.entity.player.ServerPlayerEntity

// TODO: Refactor
class CreatePokemon : Command<CommandSource> {

    override fun run(context: CommandContext<CommandSource>?): Int {
        val player: ServerPlayerEntity = context?.source?.entity as? ServerPlayerEntity ?: return 0
        val speciesStr = context.getArgument("species", String::class.java)
        val species = SpeciesRegistry[speciesStr] ?: return 0
        val form = species.forms.first()

        val pokemon = PokemonEntity(EntityRegistry.POKEMON.get(), player.world).apply {
            this.species = species
            this.form = form
        }
        pokemon.setPosition(player.posX, player.posY, player.posZ)
        player.world.addEntity(pokemon)
        return 0
    }

}