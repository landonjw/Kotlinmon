package ca.landonjw.kotlinmon.client.keybindings

import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry

class KeyBindingController(
    private val bindings: List<KeyBinding>
) {

    fun registerBindings() {
        bindings.forEach { binding -> registerBinding(binding) }
    }

    private fun registerBinding(binding: KeyBinding) {
        ClientRegistry.registerKeyBinding(binding)
        MinecraftForge.EVENT_BUS.register(binding)
    }

}