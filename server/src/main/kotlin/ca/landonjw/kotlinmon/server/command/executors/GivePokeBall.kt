package ca.landonjw.kotlinmon.server.command.executors

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.server.command.arguments.PokeBallArgument
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.entity.player.ServerPlayerEntity

class GivePokeBall(
    private val pokeBallFactory: PokeBallFactory,
    private val pokeBallArgument: PokeBallArgument
) : KotlinmonCommand {

    override fun run(context: CommandContext<CommandSource>?): Int {
        val player = context?.source?.entity as? ServerPlayerEntity ?: return 0

        val pokeBall = context.getArgument("type", PokeBall::class.java)
        val pokeBallItem = pokeBallFactory.createItem(pokeBall)
        player.entityDropItem(pokeBallItem)

        return 0
    }

    override fun register(dispatcher: CommandDispatcher<CommandSource>) {
        val command = Commands.literal("givepokeball")
            .then(
                Commands.argument("type", pokeBallArgument)
                    .executes(this)
            )
            .requires { it.hasPermissionLevel(0) }

        dispatcher.register(command)
    }

}