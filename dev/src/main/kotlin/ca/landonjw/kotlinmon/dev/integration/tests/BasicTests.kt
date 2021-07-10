package ca.landonjw.kotlinmon.dev.integration.tests

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.dev.integration.testrunner.Side
import ca.landonjw.kotlinmon.dev.integration.testrunner.SidedTest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions

class BasicTests {

    @SidedTest(Side.Client)
    fun `true is true`() {
        Assertions.assertEquals(true, true)
    }

    @SidedTest(Side.Server)
    fun `true is false`() {
        Assertions.assertEquals(true, false)
    }

    @SidedTest(Side.Server)
    fun `mod id is reachable`() {
        val modId = Kotlinmon.MOD_ID
        Assertions.assertEquals("kotlinmon", modId)
    }

    @SidedTest(Side.Both)
    fun `mocking works`() {
        val foo: Foo = mockk(relaxed = true)
        every { foo.bar() } returns 0
        Assertions.assertEquals(foo.bar(), 0)
    }

    interface Foo {
        fun bar(): Int
    }

}