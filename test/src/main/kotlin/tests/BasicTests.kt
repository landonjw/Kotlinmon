package tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BasicTests {

    @Test
    fun `true is true`() {
        assertEquals(true, true)
    }

    @Test
    fun `true is false`() {
        assertEquals(true, false)
    }

}