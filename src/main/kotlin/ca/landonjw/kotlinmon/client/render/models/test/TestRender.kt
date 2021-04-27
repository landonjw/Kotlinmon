package ca.landonjw.kotlinmon.client.render.models.test

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import ca.landonjw.kotlinmon.util.math.geometry.*
import com.google.common.collect.ImmutableList
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

object TestRender {

    val textureManager = Minecraft.getInstance().textureManager

    val meshFormat = VertexFormat(
        ImmutableList.of(
            DefaultVertexFormats.POSITION_3F,
            DefaultVertexFormats.TEX_2F,
            DefaultVertexFormats.NORMAL_3B,
            DefaultVertexFormats.PADDING_1B
        )
    )

    fun render(stack: MatrixStack, model: TestModel, animation: TestAnimation) {
        animation.apply()

        textureManager.bindTexture(ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/textures/kingler.png"))
        val buffer = Tessellator.getInstance().buffer
        RenderSystem.enableDepthTest()
        buffer.begin(GL11.GL_TRIANGLES, meshFormat)

        model.vertices.forEach { vertex ->
            renderVertex(stack, buffer, vertex)
        }
        Tessellator.getInstance().draw()
    }

    fun renderVertex(matrix: MatrixStack, buffer: BufferBuilder, vertex: TestMeshVertex) {
        val u = vertex.u
        val v = vertex.v

        val transformation = TransformationBuilder()
            .rotate(-90f.toRadians(), Axis.X_AXIS)
//            .translate(GeometricPoint(0f, 20f, 0f))
//            .scale(5f, 1f, f)
            .build()

        val originalPoint = GeometricPoint(vertex.location.x, vertex.location.y, vertex.location.z)
        val point = TransformationMatrix.transform(transformation, originalPoint)

        val originalNorm = GeometricPoint(vertex.normal.x, vertex.normal.y, vertex.normal.z)
        val norm = TransformationMatrix.transform(transformation, originalNorm)

        buffer
            .pos(matrix.last.matrix, point.x, point.y, point.z)
            .tex(u, v)
            .normal(matrix.last.normal, norm.x, norm.y, norm.z)
            .endVertex()
    }

}