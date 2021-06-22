package ca.landonjw.kotlinmontests

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory
import org.junit.platform.launcher.listeners.SummaryGeneratingListener
import java.io.PrintWriter
import java.util.*

class RunUnitTestCommand : Command<CommandSource> {

    override fun run(context: CommandContext<CommandSource>?): Int {
        val listener = SummaryGeneratingListener()

        val request = LauncherDiscoveryRequestBuilder.request()
            .selectors(unitTests.map { clazz -> selectClass(clazz) })
            .build()

        val launcher = LauncherFactory.create()
        launcher.discover(request)
        launcher.registerTestExecutionListeners(listener)
        launcher.execute(request)

        val summary = listener.summary
        PrintWriter(System.out).use { writer ->
            summary.printTo(writer)
            summary.printFailuresTo(writer)
        }

        context?.source?.playerOrException?.sendMessage(StringTextComponent("""
            ${TextFormatting.GREEN}${summary.testsSucceededCount} test(s) succeeded.
            ${TextFormatting.RED}${summary.testsFailedCount} test(s) failed.
        """.trimIndent()), UUID.randomUUID())

        return 0
    }

}