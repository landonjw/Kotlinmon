package ca.landonjw.kotlinmon.dev.integration.testrunner

import ca.landonjw.kotlinmon.dev.integration.server.command.unitTests
import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import org.apache.logging.log4j.LogManager
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass

class RunTestCommand : Command<CommandSource> {

    override fun run(context: CommandContext<CommandSource>?): Int {
        val testRunner = TestRunner(LogManager.getLogger("Kotlinmon Integration Tests"))
        testRunner.runTests(unitTests.map { clazz -> selectClass(clazz) })
        return 0
    }

}