package ca.landonjw.kotlinmon.dev.integration.testrunner

annotation class SidedTest(
    val side: Side
)

enum class Side {
    Client, Server, Both
}
