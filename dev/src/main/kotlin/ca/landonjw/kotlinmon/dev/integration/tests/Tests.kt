package ca.landonjw.kotlinmon.dev.integration.server.command

import ca.landonjw.kotlinmon.dev.integration.testrunner.Side
import ca.landonjw.kotlinmon.dev.integration.testrunner.SidedTest
import ca.landonjw.kotlinmon.dev.integration.tests.BasicTests
import java.lang.reflect.Method

val unitTests: Array<Class<*>> = arrayOf(
    BasicTests::class.java
)