package ca.landonjw.kotlinmontests.tests

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MockTests {

    @Test
    fun `mocking works`() {
        val foo: Foo = mockk(relaxed = true)
        every { foo.bar() } returns 0
        assertEquals(foo.bar(), 0)
    }

}

interface Foo {
    fun bar(): Int
}