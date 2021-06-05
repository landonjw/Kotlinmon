package ca.landonjw.kotlinmontests.tests

import ca.landonjw.kotlinmon.Kotlinmon
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KotlinmonTests {

    @Test
    fun `id is reachable`() {
        val modId = Kotlinmon.MODID
        assertEquals(modId, "kotlinmon")
    }

}