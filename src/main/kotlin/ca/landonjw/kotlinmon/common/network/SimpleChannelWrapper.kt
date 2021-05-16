package ca.landonjw.kotlinmon.common.network

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.common.network.client.ClientPacket
import ca.landonjw.kotlinmon.common.network.client.packets.TestPokemonPacket
import ca.landonjw.kotlinmon.common.network.client.packets.storage.party.SynchronizeParty
import ca.landonjw.kotlinmon.common.network.server.ServerPacket
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.function.BiConsumer
import java.util.function.Supplier

class SimpleChannelWrapper: KotlinmonNetworkChannel {

    val version = "1.0.0"
    private var packetId = 0;
    private val channel: SimpleChannel

    init {
        channel = NetworkRegistry.ChannelBuilder.named(ResourceLocation(Kotlinmon.MODID, "main"))
            .networkProtocolVersion { version }
            .clientAcceptedVersions { it == version }
            .serverAcceptedVersions { it == version }
            .simpleChannel()

        registerPackets()
    }

    private fun registerPackets() {
        registerClientPacket { TestPokemonPacket() }
        registerClientPacket { SynchronizeParty() }
    }

    private inline fun <reified T: ClientPacket> registerClientPacket(crossinline packetFactory: () -> T) {
        channel.messageBuilder(T::class.java, packetId++, NetworkDirection.PLAY_TO_CLIENT)
            .encoder { msg: T, buf: PacketBuffer -> msg.writePacketData(buf) }
            .decoder { buf: PacketBuffer -> packetFactory().apply { readPacketData(buf) } }
            .consumer(BiConsumer { msg: T, ctx: Supplier<NetworkEvent.Context> ->
                msg.processPacket(ctx.get())
                ctx.get().packetHandled = true
            })
            .add()
    }

    private inline fun <reified T: ServerPacket> registerServerPacket(crossinline packetFactory: () -> T) {
        channel.messageBuilder(T::class.java, packetId++, NetworkDirection.PLAY_TO_SERVER)
            .encoder { msg: T, buf: PacketBuffer -> msg.writePacketData(buf) }
            .decoder { buf -> packetFactory().apply { readPacketData(buf) } }
            .consumer(BiConsumer { msg: T, ctx: Supplier<NetworkEvent.Context> ->
                msg.processPacket(ctx.get())
                ctx.get().packetHandled = true
            })
            .add()
    }

    override fun sendToServer(packet: ServerPacket) {
        channel.sendToServer(packet)
    }

    override fun sendToClient(packet: ClientPacket, target: ServerPlayerEntity) {
        channel.sendTo(packet, target.connection.netManager, NetworkDirection.PLAY_TO_CLIENT)
    }

}