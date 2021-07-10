package ca.landonjw.kotlinmon.dev.integration.testrunner

import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.entity.player.ServerPlayerEntity

class RunTestCommand(
    private val testArgInterpretor: TestArgumentInterpretor,
    private val testRunner: TestRunner,
    private val networkChannel: KotlinmonNetworkChannel
) : Command<CommandSource> {

    override fun run(context: CommandContext<CommandSource>?): Int {
        val args: List<String> = TODO()

        val serverSideTests = testArgInterpretor.getTestsToRun(args, Side.Server)
        if (context!!.source.entity is ServerPlayerEntity) {
            val clientSideTests = testArgInterpretor.getTestsToRun(args, Side.Client)
            val clientTestPacket = SendTestsPacket(clientSideTests)
            networkChannel.sendToClient(clientTestPacket, context!!.source.playerOrException)
        }
        testRunner.runTests(serverSideTests)
        return 0
    }

}