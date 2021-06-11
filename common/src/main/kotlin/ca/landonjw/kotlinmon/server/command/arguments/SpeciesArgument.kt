package ca.landonjw.kotlinmon.server.command.arguments

import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.CommandException
import net.minecraft.util.text.StringTextComponent
import java.util.concurrent.CompletableFuture

class SpeciesArgument(
    private val speciesRepository: PokemonSpeciesRepository
): KotlinmonCommandArgument<PokemonSpecies> {

    override val name: String = "species"

    override fun parse(reader: StringReader?): PokemonSpecies {
        if (reader == null) throw CommandException(StringTextComponent("expected input to parse"))
        val type = reader.readUnquotedString()
        return speciesRepository[type] ?: throw CommandException(StringTextComponent("there is no species of supplied name"))
    }

    override fun <S : Any?> listSuggestions(
        context: CommandContext<S>?,
        builder: SuggestionsBuilder?
    ): CompletableFuture<Suggestions> {
        speciesRepository.getAll().forEach { builder?.suggest(it.name) }
        return builder?.buildFuture() ?: Suggestions.empty()
    }

}