package ca.landonjw.kotlinmon.client.keybindings

import net.minecraft.client.settings.KeyBinding
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

object KeyBindingModule {

    operator fun invoke() = DI.Module(name = "Keybindings") {
        bind<List<KeyBinding>>() with singleton {
            listOf(
                SelectPartySlotBinding(SelectPartySlotBinding.Direction.Previous, instance()),
                SelectPartySlotBinding(SelectPartySlotBinding.Direction.Next, instance()),
                ThrowPartyPokemonBinding(instance(), instance())
            )
        }
        bind<KeyBindingController>() with singleton { KeyBindingController(instance()) }
    }

}