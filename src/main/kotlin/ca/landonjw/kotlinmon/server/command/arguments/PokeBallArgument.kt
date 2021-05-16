package ca.landonjw.kotlinmon.server.command.arguments

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRepository
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import com.google.gson.JsonObject
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.CommandException
import net.minecraft.command.arguments.IArgumentSerializer
import net.minecraft.network.PacketBuffer
import net.minecraft.util.text.StringTextComponent
import java.util.concurrent.CompletableFuture

class PokeBallArgument : ArgumentType<PokeBall> {

    private val pokeBallRepository: PokeBallRepository by KotlinmonDI.inject()

    override fun parse(reader: StringReader?): PokeBall {
        if (reader == null) return ProvidedPokeBall.PokeBall
        val type = reader.readUnquotedString()
        return pokeBallRepository[type] ?: throw CommandException(StringTextComponent("there is no poke ball of supplied type"))
    }

    override fun <S : Any?> listSuggestions(
        context: CommandContext<S>?,
        builder: SuggestionsBuilder?
    ): CompletableFuture<Suggestions> {
        pokeBallRepository.getAll().forEach { builder?.suggest(it.name) }
        return builder?.buildFuture() ?: Suggestions.empty()
    }

}