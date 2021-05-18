package ca.landonjw.kotlinmon.client.keybindings

import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry

object KeyBindingController {

    fun registerBindings() {
        registerBinding(SelectPartySlotBinding(SelectPartySlotBinding.Direction.Previous))
        registerBinding(SelectPartySlotBinding(SelectPartySlotBinding.Direction.Next))
        registerBinding(ThrowPartyPokemonBinding())
    }

    private fun registerBinding(binding: KeyBinding) {
        ClientRegistry.registerKeyBinding(binding)
        MinecraftForge.EVENT_BUS.register(binding)
    }

}