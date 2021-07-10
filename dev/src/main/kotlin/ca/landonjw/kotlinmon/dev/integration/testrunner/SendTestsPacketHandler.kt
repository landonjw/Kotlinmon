package ca.landonjw.kotlinmon.dev.integration.testrunner

import ca.landonjw.kotlinmon.api.network.PacketHandler
import net.minecraftforge.fml.network.NetworkEvent

class SendTestsPacketHandler(
    private val testRunner: TestRunner
) : PacketHandler<SendTestsPacket> {

    override fun handle(packet: SendTestsPacket, ctx: NetworkEvent.Context) {
        testRunner.runTests(packet.tests)
    }

}