package ca.landonjw.kotlinmon.common.network

import ca.landonjw.kotlinmon.api.network.Packet
import ca.landonjw.kotlinmon.api.network.PacketHandler
import ca.landonjw.kotlinmon.api.network.PacketToClient
import ca.landonjw.kotlinmon.api.network.PacketToServer
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.function.BiConsumer
import java.util.function.Supplier

/**
 * Used to register [PacketHandler]s to various [Packet]s in Kotlinmon.
 *
 * This is done to allow for separation of handler logic on client & server side.
 *
 * This allows for the possibility to override packet handling, but it should be done with care
 * to ensure it does not have side effects the implementation does not expect.
 * Trying to add a handler to a packet that has not been registered will fail silently,
 *
 * @author landonjw
 */
class PacketHandlerRegistrationEvent(
    packetBuildersByClass: Map<Class<out Packet>, SimpleChannel.MessageBuilder<out Packet>>
) : Event() {

    internal val packetBuildersByClass: MutableMap<Class<out Packet>, SimpleChannel.MessageBuilder<out Packet>>

    init {
        this.packetBuildersByClass = packetBuildersByClass.toMutableMap()
    }

    fun <T : PacketToClient> registerClientPacketHandler(type: Class<T>, handler: PacketHandler<T>) {
        val builder = packetBuildersByClass[type] as? SimpleChannel.MessageBuilder<T> ?: return
        builder.consumer(BiConsumer { msg: T, ctx: Supplier<NetworkEvent.Context> -> handler.handle(msg, ctx.get()) })
    }

    fun <T : PacketToServer> registerServerPacketHandler(type: Class<T>, handler: PacketHandler<T>) {
        val builder = packetBuildersByClass[type] as? SimpleChannel.MessageBuilder<T> ?: return
        builder.consumer(BiConsumer { msg: T, ctx: Supplier<NetworkEvent.Context> -> handler.handle(msg, ctx.get()) })
    }

}