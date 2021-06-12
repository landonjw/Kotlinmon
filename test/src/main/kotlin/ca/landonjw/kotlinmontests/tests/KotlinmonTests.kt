package ca.landonjw.kotlinmontests.tests

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.KotlinmonBootstrap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KotlinmonTests {

    @Test
    fun `id is reachable`() {
        val modId = Kotlinmon.MOD_ID
        assertEquals(modId, "kotlinmon")
    }

}