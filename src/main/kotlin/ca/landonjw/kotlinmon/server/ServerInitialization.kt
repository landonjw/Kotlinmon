package ca.landonjw.kotlinmon.server

import ca.landonjw.kotlinmon.server.command.CreatePokemon
import ca.landonjw.kotlinmon.server.command.GivePokeBall
import ca.landonjw.kotlinmon.server.command.SendPokemonCommand
import ca.landonjw.kotlinmon.server.command.arguments.PokeBallArgument
import ca.landonjw.kotlinmon.server.command.arguments.SpeciesArgument
import com.mojang.brigadier.arguments.ArgumentType
import net.minecraft.command.arguments.ArgumentSerializer
import net.minecraft.command.arguments.ArgumentTypes
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object ServerInitialization {

    @SubscribeEvent
    fun onCommandRegistration(event: RegisterCommandsEvent) {
        registerCommandArguments()

        CreatePokemon.register(event.dispatcher)
        GivePokeBall.register(event.dispatcher)
        SendPokemonCommand.register(event.dispatcher)
    }

    private fun registerCommandArguments() {
        registerCommandArgument("poke_ball") { PokeBallArgument() }
        registerCommandArgument("species") { SpeciesArgument() }
    }

    private inline fun <reified T : ArgumentType<*>> registerCommandArgument(name: String, crossinline supplier: () -> T) {
        try {
            ArgumentTypes.register(name, T::class.java, ArgumentSerializer { supplier() })
        }
        catch (e: IllegalArgumentException) {
            // Means argument is already registered, when running only on client side.
            // TODO: More elegant way of handling this? Curious why this is even registered at this point...
        }
    }

}