package ca.landonjw.kotlinmon.server.command

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.server.command.arguments.PokeBallArgument
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.entity.player.ServerPlayerEntity

class GivePokeBall: Command<CommandSource> {

    private val pokeBallFactory: PokeBallFactory by KotlinmonDI.inject()

    override fun run(context: CommandContext<CommandSource>?): Int {
        val player = context?.source?.entity as? ServerPlayerEntity ?: return 0

        val pokeBall = context.getArgument("type", PokeBall::class.java)
        val pokeBallItem = pokeBallFactory.createItem(pokeBall)
        player.entityDropItem(pokeBallItem)

        return 0
    }

    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSource>) {
            val command = Commands.literal("givepokeball")
                .then(
                    Commands.argument("type", PokeBallArgument())
                        .executes(GivePokeBall())
                )
                .requires { it.hasPermissionLevel(0) }

            dispatcher.register(command)
        }
    }

}