package ca.landonjw.kotlinmon.dev.integration.testrunner

import org.junit.platform.engine.DiscoverySelector
import java.lang.reflect.Method

class TestArgumentInterpretor {

    fun getTestsToRun(args: List<String>, side: Side): List<DiscoverySelector> {
        TODO()
    }

    private fun Class<*>.getTestMethods(side: Side): List<Method> {
        return this.declaredMethods.filter {
            it.isAnnotationPresent(SidedTest::class.java)
                && it.getAnnotation(SidedTest::class.java).side == side
                || it.getAnnotation(SidedTest::class.java).side == Side.Both
        }
    }

}