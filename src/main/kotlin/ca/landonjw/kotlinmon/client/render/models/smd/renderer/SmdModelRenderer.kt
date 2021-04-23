package ca.landonjw.kotlinmon.client.render.models.smd.renderer

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.api.renderer.ModelRenderer
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.SmdCache
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.MeshVertex
import com.google.common.collect.ImmutableList
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Quaternion
import net.minecraft.util.math.vector.Vector3f
import org.lwjgl.opengl.GL11

object SmdModelRenderer: ModelRenderer<SmdModel> {

    private val textureManager = Minecraft.getInstance().textureManager

    private val vertexFormat = VertexFormat(ImmutableList.of(
        DefaultVertexFormats.POSITION_3F,
        DefaultVertexFormats.TEX_2F,
        DefaultVertexFormats.NORMAL_3B,
        DefaultVertexFormats.PADDING_1B
    ))

    override fun render(matrix: MatrixStack, model: SmdModel) {
        model.currentAnimation?.apply() ?: model.tPose()
//        model.tPose()
        val vertices = model.mesh.getVertices()

        val buffer = Tessellator.getInstance().buffer
        RenderSystem.enableDepthTest()
        buffer.begin(GL11.GL_TRIANGLES, vertexFormat)

        // Apply translation & orientation offsets so models are positioned properly by default
        addPositionOffset(matrix, model)
        addOrientationOffset(matrix, model)

        textureManager.bindTexture(model.mesh.texture.resourceLocation)
        vertices.forEach { vertex -> renderVertex(matrix, buffer, vertex, model.scale) }
        Tessellator.getInstance().draw()
    }

    private fun renderVertex(matrix: MatrixStack, buffer: BufferBuilder, vertex: MeshVertex, modelScale: Float) {
        val translation = vertex.translation?.copy()
        val rotation = vertex.rotation?.copy()

        val vertexPos = vertex.position.copy()
//        if (translation != null) vertexPos.add(translation)
        vertexPos.mul(modelScale)

        if (rotation != null) applyRotation(matrix, rotation)

        val u = vertex.uvMap.a
        val v = vertex.uvMap.b

        val normal = vertexPos.copy()
        normal.normalize()

        buffer
            .pos(matrix.last.matrix, vertexPos.x, vertexPos.y, vertexPos.z)
            .tex(u, v)
            .normal(matrix.last.normal, normal.x, normal.y, normal.z)
            .endVertex()

        if (rotation != null) revertRotation(matrix, rotation)
    }

    private fun applyRotation(matrix: MatrixStack, rotation: Vector3f) {
        matrix.rotate(Quaternion(Vector3f.XP, rotation.x, true))
        matrix.rotate(Quaternion(Vector3f.YP, rotation.y, true))
        matrix.rotate(Quaternion(Vector3f.ZP, rotation.z, true))
    }

    private fun revertRotation(matrix: MatrixStack, rotation: Vector3f) {
        matrix.rotate(Quaternion(Vector3f.ZP, -1 * rotation.z, true))
        matrix.rotate(Quaternion(Vector3f.YP, -1 * rotation.y, true))
        matrix.rotate(Quaternion(Vector3f.XP, -1 * rotation.x, true))
    }

    private fun addOrientationOffset(matrix: MatrixStack, model: SmdModel) {
        if (model.orientationOffset == null) return

        val xRot = model.orientationOffset.x
        val yRot = model.orientationOffset.y
        val zRot = model.orientationOffset.z

        matrix.rotate(Quaternion(Vector3f.XP, xRot, true))
        matrix.rotate(Quaternion(Vector3f.YP, yRot, true))
        matrix.rotate(Quaternion(Vector3f.ZP, zRot, true))
    }

    private fun addPositionOffset(matrix: MatrixStack, model: SmdModel) {
        if (model.positionOffset == null) return

        val xPos = model.positionOffset.x.toDouble()
        val yPos = model.positionOffset.y.toDouble()
        val zPos = model.positionOffset.z.toDouble()

        matrix.translate(xPos, yPos, zPos)
    }

}