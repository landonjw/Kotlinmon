package ca.landonjw.kotlinmon.client.render.party

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.KotlinmonBootstrap
import ca.landonjw.kotlinmon.client.party.ClientPartyStorage
import ca.landonjw.kotlinmon.common.pokemon.PokemonDTO
import ca.landonjw.kotlinmon.client.pokemon.getSpriteLocation
import ca.landonjw.kotlinmon.client.render.renderImage
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.AbstractGui
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class PokemonPartyOverlay(
    private val clientParty: ClientPartyStorage
) : AbstractGui() {

    private val backgroundTexture: ResourceLocation = ResourceLocation(Kotlinmon.MOD_ID, "party/background.png")
    private val backgroundStartX: Double = 0.0
    private val backgroundStartY: Double = 70.0
    private val backgroundPaddingX: Double = 8.0
    private val backgroundPaddingY: Double = 0.0

    private val spriteSize: Double = 24.0
    private val spritePadding: Double = 8.0

    private val selectedSlotTexture: ResourceLocation = ResourceLocation(Kotlinmon.MOD_ID, "party/selected_slot.png")
    private val selectedSlotSize: Double = 32.0

    @SubscribeEvent
    fun onOverlayRender(event: RenderGameOverlayEvent.Pre) {
        render()
    }

    private fun render() {
        // Render the background of the party
        renderPartyBackground()
        // Render the slot that's currently selected
        renderSelectedSlotBorder()
        // Render all the pokemon in the party, in their appropriate slot positions
        for (slot in 0 until clientParty.capacity) {
            renderPartySlot(slot)
        }
    }

    private fun renderPartyBackground() {
        RenderSystem.enableAlphaTest()
        renderImage(
            texture = backgroundTexture,
            x = backgroundStartX,
            y = backgroundStartY,
            height = getBackgroundHeight(),
            width = getBackgroundWidth()
        )
        RenderSystem.disableAlphaTest()
    }

    private fun getBackgroundHeight(): Double {
        val slotPartyHeight = (spriteSize + spritePadding) * (clientParty.capacity)
        return slotPartyHeight + spritePadding + backgroundPaddingY * 2
    }

    private fun getBackgroundWidth(): Double {
        return spriteSize + backgroundPaddingX * 2
    }

    private fun renderPartySlot(slot: Int) {
        val pokemonData = clientParty[slot] ?: return

        val texture = getSpriteTexture(pokemonData)

        val x = backgroundStartX + backgroundPaddingX
        val y = backgroundStartY + spritePadding + backgroundPaddingY + ((spriteSize + spritePadding) * slot)

        RenderSystem.enableAlphaTest()
        renderImage(texture, x, y, spriteSize, spriteSize)
        RenderSystem.disableAlphaTest()
    }

    private fun renderSelectedSlotBorder() {
        val selectedSlot = clientParty.selectedSlot ?: return

        val x = backgroundStartX + (spritePadding / 2)
        val y = backgroundStartY + (spritePadding / 2) + backgroundPaddingY + ((spriteSize + spritePadding) * selectedSlot)

        RenderSystem.enableAlphaTest()
        renderImage(selectedSlotTexture, x, y, selectedSlotSize, selectedSlotSize)
        RenderSystem.disableAlphaTest()
    }

    private fun getSpriteTexture(pokemonData: PokemonDTO): ResourceLocation {
        // If the pokemon has a custom texture, try to fetch a sprite for it, otherwise fall back on default sprite
        if (pokemonData.texture != null) {
            val customSprite = getSpriteLocation(pokemonData.species, pokemonData.form, pokemonData.texture)
            if (spriteTextureExists(customSprite)) {
                return customSprite
            }
        }
        return getSpriteLocation(pokemonData.species, pokemonData.form)
    }

    private fun spriteTextureExists(location: ResourceLocation): Boolean {
        // TODO: Does this have performance implications?
        return KotlinmonBootstrap::class.java.getResource("/assets/${location.namespace}/${location.path}") != null
    }

}