package ca.landonjw.kotlinmontests

import ca.landonjw.kotlinmontests.tests.BasicTests
import ca.landonjw.kotlinmontests.tests.KotlinmonTests
import ca.landonjw.kotlinmontests.tests.MockTests

val unitTests: Array<Class<*>> = arrayOf(
    BasicTests::class.java,
    KotlinmonTests::class.java,
    MockTests::class.java
)