package ca.landonjw.kotlinmon.dev.integration.testrunner

import ca.landonjw.kotlinmon.api.network.PacketToClient
import net.minecraft.network.PacketBuffer
import org.junit.platform.engine.DiscoverySelector

/**
 * Used to send information on tests to run to the client.
 *
 * The idea behind the test process is:
 * 1. User executes command targeting which tests to run (side-agnostic).
 * 2. Command gets processed on server, finding tests and sends any client tests back to client via packet.
 * 3. Server executes server-sided tests
 * 4. Client executes client-sided tests.
 */
class SendTestsPacket(tests: List<DiscoverySelector>) : PacketToClient {

    var tests: List<DiscoverySelector> = listOf()
        private set

    private val testEncoder = TestEncoder()
    private val testDecoder = TestDecoder()

    init {
        this.tests = tests
    }

    override fun encodeData(buf: PacketBuffer) {
        testEncoder.encode(buf, tests)
    }

    override fun decodeData(buf: PacketBuffer) {
        this.tests = testDecoder.decode(buf)
    }

}