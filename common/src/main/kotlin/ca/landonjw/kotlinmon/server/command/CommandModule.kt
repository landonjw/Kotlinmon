package ca.landonjw.kotlinmon.server.command

import ca.landonjw.kotlinmon.server.command.arguments.KotlinmonCommandArgument
import ca.landonjw.kotlinmon.server.command.arguments.PokeBallArgument
import ca.landonjw.kotlinmon.server.command.arguments.SpeciesArgument
import ca.landonjw.kotlinmon.server.command.executors.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

object CommandModule {

    val bindings = DI.Module(name = "Command") {
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
        bind<List<KotlinmonCommandArgument<out Any>>>() with singleton {
            listOf(
                PokeBallArgument(instance()),
                SpeciesArgument(instance())
            )
        }
    }

}