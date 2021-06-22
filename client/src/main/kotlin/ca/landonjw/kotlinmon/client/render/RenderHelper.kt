package ca.landonjw.kotlinmon.client.render

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

fun renderImage(texture: ResourceLocation, x: Double, y: Double, height: Double, width: Double) {
    val textureManager = Minecraft.getInstance().textureManager

    val buffer = Tessellator.getInstance().builder
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
    textureManager.bind(texture)

    buffer.vertex(x, y + height, 0.0).uv(0f, 1f).endVertex()
    buffer.vertex(x + width, y + height, 0.0).uv(1f, 1f).endVertex()
    buffer.vertex(x + width, y, 0.0).uv(1f, 0f).endVertex()
    buffer.vertex(x, y, 0.0).uv(0f, 0f).endVertex()

    Tessellator.getInstance().end()
}