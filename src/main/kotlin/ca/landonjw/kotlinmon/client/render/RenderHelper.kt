package ca.landonjw.kotlinmon.client.render

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

fun renderImage(texture: ResourceLocation, x: Double, y: Double, height: Double, width: Double) {
    val textureManager = Minecraft.getInstance().textureManager

    val buffer = Tessellator.getInstance().buffer
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
    textureManager.bindTexture(texture)

    buffer.pos(x, y + height, 0.0).tex(0f, 1f).endVertex()
    buffer.pos(x + width, y + height, 0.0).tex(1f, 1f).endVertex()
    buffer.pos(x + width, y, 0.0).tex(1f, 0f).endVertex()
    buffer.pos(x, y, 0.0).tex(0f, 0f).endVertex()

    Tessellator.getInstance().draw()
}