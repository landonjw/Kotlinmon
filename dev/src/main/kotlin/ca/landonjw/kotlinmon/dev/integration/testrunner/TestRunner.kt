package ca.landonjw.kotlinmon.dev.integration.testrunner

import org.apache.logging.log4j.Logger
import org.junit.platform.engine.DiscoverySelector
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory
import org.junit.platform.launcher.listeners.SummaryGeneratingListener
import org.junit.platform.launcher.listeners.TestExecutionSummary

class TestRunner(private val logger: Logger) {

    fun runTests(selectors: List<DiscoverySelector>) {
        val listener = SummaryGeneratingListener()

        val request = LauncherDiscoveryRequestBuilder.request()
            .selectors(selectors)
            .build()

        val launcher = LauncherFactory.create()
        launcher.discover(request)
        launcher.registerTestExecutionListeners(listener)
        launcher.execute(request)

        val summary = listener.summary
        summary.logSummary()
        summary.logFailures()
    }

    private fun TestExecutionSummary.logSummary() {
        logger.info("""
            $testsSucceededCount test(s) succeeded.
            $totalFailureCount test(s) failed.
        """.trimIndent())
    }

    private fun TestExecutionSummary.logFailures() {
        failures.forEach {
            logger.error("""
                ${it.testIdentifier}
                ${it.exception.printStackTrace()}
                
                
            """.trimIndent())
        }
    }

}