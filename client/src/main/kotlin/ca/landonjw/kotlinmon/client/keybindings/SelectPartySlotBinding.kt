package ca.landonjw.kotlinmon.client.keybindings

import ca.landonjw.kotlinmon.client.party.ClientPartyStorage
import net.minecraft.client.settings.KeyBinding
import net.minecraft.client.util.InputMappings
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.settings.KeyConflictContext
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.lwjgl.glfw.GLFW

class SelectPartySlotBinding(
  private val direction: Direction,
  private val clientParty: ClientPartyStorage
) : KeyBinding(
    "key.kotlinmon.${direction.name.toLowerCase()}",
    KeyConflictContext.UNIVERSAL,
    InputMappings.Type.KEYSYM,
    direction.defaultKeyCode,
    "key.categories.kotlinmon"
) {

    enum class Direction(val defaultKeyCode: Int) {
        Next(GLFW.GLFW_KEY_UP), Previous(GLFW.GLFW_KEY_DOWN)
    }

    @SubscribeEvent
    fun onKeyInput(event: InputEvent.KeyInputEvent) {
        if (isDown) {
            when (direction) {
                Direction.Next -> clientParty.selectNextPokemon()
                Direction.Previous -> clientParty.selectPreviousPokemon()
            }
        }
    }

}