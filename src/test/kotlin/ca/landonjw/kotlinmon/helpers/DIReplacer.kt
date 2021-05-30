package ca.landonjw.kotlinmon

import io.mockk.every
import io.mockk.mockkObject
import org.kodein.di.DI

fun createDIHarness(provider: () -> DI) {
    val container = provider()
    mockkObject(KotlinmonDI)
    every { KotlinmonDI.container } returns container
}