package ca.landonjw.kotlinmon.server.command.arguments

import com.mojang.brigadier.arguments.ArgumentType

interface KotlinmonCommandArgument<T> : ArgumentType<T> {
    val name: String
}