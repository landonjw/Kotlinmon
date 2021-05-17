package ca.landonjw.kotlinmon.client.render.party

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.client.party.ClientPartyStorage
import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonData
import ca.landonjw.kotlinmon.client.pokemon.getSpriteLocation
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.lwjgl.opengl.GL11

class PokemonPartyOverlay: AbstractGui() {

    private val textureManager = Minecraft.getInstance().textureManager

    private val clientParty: ClientPartyStorage by KotlinmonDI.inject()

    @SubscribeEvent
    fun onOverlayRender(event: RenderGameOverlayEvent.Pre) {
        render()
    }

    private fun render() {
        renderSelectedSlotBorder()
        for (slot in 0 until clientParty.capacity) {
            renderPartySlot(slot)
        }
    }

    private fun renderPartySlot(slot: Int) {
        val pokemonData = clientParty[slot] ?: return

        val buffer = Tessellator.getInstance().buffer
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)

        bindSpriteTexture(pokemonData)

        // TODO: Use this later
        val height = Minecraft.getInstance().mainWindow.scaledHeight
        val width = Minecraft.getInstance().mainWindow.scaledWidth

        val x = 8.0
        val y = 30.0 + (28 * slot)

        buffer.pos(x, y + 24f, 0.0).tex(0f, 1f).endVertex()
        buffer.pos(x + 24f, y + 24f, 0.0).tex(1f, 1f).endVertex()
        buffer.pos(x + 24f, y, 0.0).tex(1f, 0f).endVertex()
        buffer.pos(x, y, 0.0).tex(0f, 0f).endVertex()

        RenderSystem.enableAlphaTest()
        Tessellator.getInstance().draw()
        RenderSystem.disableAlphaTest()
    }

    private fun renderSelectedSlotBorder() {
        val selectedSlot = clientParty.selectedSlot ?: return

        val buffer = Tessellator.getInstance().buffer
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)

        val x = 8.0
        val y = 30.0 + (28 * selectedSlot)

        buffer.pos(x, y + 24f, 0.0).color(255, 255, 255, 255).endVertex()
        buffer.pos(x + 24f, y + 24f, 0.0).color(255, 255, 255, 255).endVertex()
        buffer.pos(x + 24f, y, 0.0).color(255, 255, 255, 255).endVertex()
        buffer.pos(x, y, 0.0).color(255, 255, 255, 255).endVertex()

        Tessellator.getInstance().draw()
    }

    private fun bindSpriteTexture(pokemonData: ClientPokemonData) {
        // If the pokemon has a custom texture, try to fetch a sprite for it, otherwise fall back on default sprite
        if (pokemonData.texture != null) {
            val customSprite = getSpriteLocation(pokemonData.species, pokemonData.form, pokemonData.texture)
            if (spriteTextureExists(customSprite)) {
                return textureManager.bindTexture(customSprite)
            }
        }

        val defaultSprite = getSpriteLocation(pokemonData.species, pokemonData.form)
        textureManager.bindTexture(defaultSprite)
    }

    private fun spriteTextureExists(location: ResourceLocation): Boolean {
        // TODO: Does this have performance implications?
        return Kotlinmon::class.java.getResource("/assets/${location.namespace}/${location.path}") != null
    }

}