package ca.landonjw.kotlinmon.server.command

import ca.landonjw.kotlinmon.server.command.arguments.KotlinmonCommandArgument
import ca.landonjw.kotlinmon.server.command.arguments.PokeBallArgument
import ca.landonjw.kotlinmon.server.command.arguments.SpeciesArgument
import ca.landonjw.kotlinmon.server.command.executors.*
import org.kodein.di.*

object CommandModule {

    operator fun invoke() = DI.Module(name = "Command") {
        // Commands
        bind<List<KotlinmonCommand>>() with singleton {
            listOf(
                AddToPartyCommand(instance()),
                CreatePokemon(instance(), instance()),
                GivePokeBall(instance(), instance()),
                PopulateDevPartyCommand(instance(), instance()),
                SetToPartyCommand(instance())
            )
        }

        // Command Arguments
        bind<SpeciesArgument>() with provider { SpeciesArgument(instance()) }
        bind<PokeBallArgument>() with provider { PokeBallArgument(instance()) }
        bind<List<KotlinmonCommandArgument<out Any>>>() with singleton {
            listOf(
                instance<SpeciesArgument>(),
                instance<PokeBallArgument>()
            )
        }
    }

}