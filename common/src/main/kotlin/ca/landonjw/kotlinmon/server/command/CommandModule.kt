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
        bind<List<KotlinmonCommand>> { singleton {
            listOf(
                AddToPartyCommand(instance()),
                CreatePokemon(instance(), instance()),
                GivePokeBall(instance(), instance()),
                PopulateDevPartyCommand(instance(), instance()),
                SetToPartyCommand(instance())
            )
        } }
        bind<PokeBallArgument> { singleton { PokeBallArgument(instance()) } }
        bind<SpeciesArgument> { singleton { SpeciesArgument(instance()) } }
        bind<List<KotlinmonCommandArgument<out Any>>> { singleton {
            listOf(
                instance<PokeBallArgument>(),
                instance<SpeciesArgument>()
            )
        } }
    }

}