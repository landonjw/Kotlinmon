package ca.landonjw.kotlinmon.client.keybindings

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.client.party.ClientPartyStorage
import ca.landonjw.kotlinmon.common.network.server.packets.ThrowPartyPokemon
import net.minecraft.client.settings.KeyBinding
import net.minecraft.client.util.InputMappings
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.settings.KeyConflictContext
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.lwjgl.glfw.GLFW

class ThrowPartyPokemonBinding : KeyBinding(
    "key.kotlinmon.throwpartypokemon",
    KeyConflictContext.UNIVERSAL,
    InputMappings.Type.KEYSYM,
    GLFW.GLFW_KEY_R,
    "key.categories.kotlinmon"
) {

    private val networkChannel: KotlinmonNetworkChannel by KotlinmonDI.inject()
    private val clientParty: ClientPartyStorage by KotlinmonDI.inject()

    @SubscribeEvent
    fun onKeyInput(event: InputEvent.KeyInputEvent) {
        if (isPressed) {
            val selectedSlot = clientParty.selectedSlot ?: return
            networkChannel.sendToServer(ThrowPartyPokemon(selectedSlot))
        }
    }

}