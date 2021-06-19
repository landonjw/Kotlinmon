package ca.landonjw.kotlinmon.server

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.common.network.PacketHandlerRegistrationEvent
import ca.landonjw.kotlinmon.common.network.packets.party.SynchronizePartyRequest
import ca.landonjw.kotlinmon.common.network.packets.party.ThrowPartyPokemon
import ca.landonjw.kotlinmon.server.command.arguments.KotlinmonCommandArgument
import ca.landonjw.kotlinmon.server.command.executors.KotlinmonCommand
import ca.landonjw.kotlinmon.server.network.party.SynchronizePartyRequestHandler
import ca.landonjw.kotlinmon.server.network.party.ThrowPartyPokemonHandler
import com.mojang.brigadier.arguments.ArgumentType
import net.minecraft.command.arguments.ArgumentSerializer
import net.minecraft.command.arguments.ArgumentTypes
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class ServerInitialization(
    private val commandArguments: List<KotlinmonCommandArgument<out Any>>,
    private val commands: List<KotlinmonCommand>,
    private val synchronizePartyRequestHandler: SynchronizePartyRequestHandler,
    private val throwPartyPokemonHandler: ThrowPartyPokemonHandler
) {

    init {
        // TODO: Remove on module split
        Kotlinmon.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onCommandRegistration(event: RegisterCommandsEvent) {
        commandArguments.forEach { argument -> registerCommandArgument(argument.name) { argument } }
        commands.forEach { command -> command.register(event.dispatcher) }
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

    @SubscribeEvent
    fun onPacketRegistration(event: PacketHandlerRegistrationEvent) {
        event.registerServerPacketHandler(SynchronizePartyRequest::class.java, synchronizePartyRequestHandler)
        event.registerServerPacketHandler(ThrowPartyPokemon::class.java, throwPartyPokemonHandler)
    }

}