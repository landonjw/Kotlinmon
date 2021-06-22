package ca.landonjw.kotlinmon.common.network

import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.api.network.Packet
import ca.landonjw.kotlinmon.api.network.PacketToClient
import ca.landonjw.kotlinmon.api.network.PacketToServer
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.PacketBuffer
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.function.BiConsumer

class SimpleChannelWrapper(
    private val channel: SimpleChannel,
    private val eventBus: IEventBus,
    private val packetRegistrations: List<(SimpleChannelWrapper) -> Unit>,
) : KotlinmonNetworkChannel {

    val version = "1.0.0"
    private var packetId: Int = 0

    private val packetBuildersByClass: MutableMap<Class<out Packet>, SimpleChannel.MessageBuilder<out Packet>> = mutableMapOf()

    init {
        eventBus.register(this)
    }

    @SubscribeEvent
    fun onPacketRegistration(event: PacketRegistrationEvent) {
        // Register all the packets in Kotlinmon.
        packetRegistrations.forEach { it(this) }

        // Send an event to signal the registration of packets into the channel, allowing them to bind handlers.
        val registerEvent = PacketHandlerRegistrationEvent(packetBuildersByClass)
        eventBus.post(registerEvent)
        registerEvent.packetBuildersByClass.values.forEach { builder -> builder.add() }
    }

    override fun sendToServer(packet: PacketToServer) {
        channel.sendToServer(packet)
    }

    override fun sendToClient(packet: PacketToClient, target: ServerPlayerEntity) {
        channel.sendTo(packet, target.connection.connection, NetworkDirection.PLAY_TO_CLIENT)
    }

    fun <T : PacketToClient> registerClientPacket(clazz: Class<T>, provider: () -> T) {
        val builder = getOrCreateBuilder(clazz, NetworkDirection.PLAY_TO_CLIENT)
        builder.encoder { msg: T, buf: PacketBuffer -> msg.encodeData(buf) }
        builder.decoder { buf: PacketBuffer -> provider().apply { decodeData(buf) } }
        builder.consumer(BiConsumer { msg, ctx -> ctx.get().packetHandled = true })
    }

    fun <T : PacketToServer> registerServerPacket(clazz: Class<T>, provider: () -> T) {
        val builder = getOrCreateBuilder(clazz, NetworkDirection.PLAY_TO_SERVER)
        builder.encoder { msg: T, buf: PacketBuffer -> msg.encodeData(buf) }
        builder.decoder { buf: PacketBuffer -> provider().apply { decodeData(buf) } }
        builder.consumer(BiConsumer { msg, ctx -> ctx.get().packetHandled = true })
    }

    fun <T : Packet> getOrCreateBuilder(clazz: Class<T>, direction: NetworkDirection): SimpleChannel.MessageBuilder<T> {
        // Return the builder if it's already populated in the map.
        if (packetBuildersByClass[clazz] != null) return packetBuildersByClass[clazz] as SimpleChannel.MessageBuilder<T>

        // If it's not populated in the map, create a builder, add it to the map, and then return it.
        val builder = channel.messageBuilder(clazz, packetId++, direction)
        packetBuildersByClass[clazz] = builder
        return builder
    }

}