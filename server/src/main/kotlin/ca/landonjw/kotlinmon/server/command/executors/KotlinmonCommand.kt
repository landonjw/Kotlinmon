package ca.landonjw.kotlinmon.server.command.executors

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.command.CommandSource

interface KotlinmonCommand : Command<CommandSource> {

    fun register(dispatcher: CommandDispatcher<CommandSource>)

}