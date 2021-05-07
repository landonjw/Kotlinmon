package ca.landonjw.kotlinmon.server

import ca.landonjw.kotlinmon.server.command.CreatePokemon
import ca.landonjw.kotlinmon.server.command.GivePokeBall
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object ServerInitialization {

    @SubscribeEvent
    fun onCommandRegistration(event: RegisterCommandsEvent) {
        CreatePokemon.register(event.dispatcher)
        GivePokeBall.register(event.dispatcher)
    }

}