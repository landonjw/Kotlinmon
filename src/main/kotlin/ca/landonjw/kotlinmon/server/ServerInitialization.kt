package ca.landonjw.kotlinmon.server

import ca.landonjw.kotlinmon.server.command.CreatePokemon
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.command.Commands
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object ServerInitialization {

    @SubscribeEvent
    fun onCommandRegistration(event: RegisterCommandsEvent) {
        val test = Commands.literal("pokecreate")
            .then(
                Commands.argument("species", StringArgumentType.word())
                    .executes(CreatePokemon())
            )
            .requires { it.hasPermissionLevel(0) }
            .executes {
                println("No argument supplied")
                return@executes 0
            }
        event.dispatcher.register(test)
    }

}