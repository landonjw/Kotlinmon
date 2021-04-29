package ca.landonjw.kotlinmon.client.render.models.smd.renderer

import ca.landonjw.kotlinmon.client.render.models.api.renderer.ModelRenderer
import ca.landonjw.kotlinmon.client.render.models.api.renderer.RenderProperty
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.SmdMeshVertex
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix
import com.google.common.collect.ImmutableList
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.util.math.vector.Vector3f
import org.lwjgl.opengl.GL11
import java.util.*

class StandardSmdModelRenderer : ModelRenderer<SmdModel> {

    private val textureManager = Minecraft.getInstance().textureManager
    private val random = Random()

    private val meshFormat = VertexFormat(
        ImmutableList.of(
            DefaultVertexFormats.POSITION_3F,
            DefaultVertexFormats.TEX_2F,
            DefaultVertexFormats.NORMAL_3B,
            DefaultVertexFormats.PADDING_1B
        )
    )

    override fun render(matrix: MatrixStack, model: SmdModel) {
        if (model.currentAnimation != null) model.animations[model.currentAnimation]?.animate()
        val globalTransforms = getGlobalTransforms(model.renderProperties)

        textureManager.bindTexture(model.skeleton.mesh.texture)
        val buffer = Tessellator.getInstance().buffer
        RenderSystem.enableDepthTest()
        buffer.begin(GL11.GL_TRIANGLES, meshFormat)

        model.skeleton.mesh.vertices.forEach { vertex ->
            renderVertex(matrix, buffer, vertex, globalTransforms, model.renderProperties)
        }
        Tessellator.getInstance().draw()
    }

    private fun renderVertex(
        matrix: MatrixStack,
        buffer: BufferBuilder,
        vertex: SmdMeshVertex,
        globalTransforms: TransformationMatrix,
        properties: List<RenderProperty<*>>
    ) {
        val shakeNoise = getProperty<GlitchNoise>(properties)?.value ?: Vector3f(0f, 0f, 0f)
        val shakeRotation = Vector3f(
            shakeNoise.x * random.nextFloat(),
            shakeNoise.y * random.nextFloat(),
            shakeNoise.z * random.nextFloat()
        )
        val shakeMatrix = TransformationMatrix.rotate(shakeRotation)

        val position = globalTransforms * shakeMatrix * vertex.position
        val normal = globalTransforms * shakeMatrix * vertex.normal

        buffer
            .pos(matrix.last.matrix, position.x, position.y, position.z)
            .tex(vertex.u, vertex.v)
            .normal(matrix.last.normal, normal.x, normal.y, normal.z)
            .endVertex()
    }

    private fun getGlobalTransforms(properties: List<RenderProperty<*>>): TransformationMatrix {
        val scale = getProperty<Scale>(properties)?.value ?: Vector3f(1f, 1f, 1f)
        val positionOffset = getProperty<PositionOffset>(properties)?.value ?: GeometricPoint()
        val rotationOffset = getProperty<RotationOffset>(properties)?.value ?: Vector3f(0f, 0f, 0f)

        val scaleMatrix = TransformationMatrix.scale(scale.x, scale.y, scale.z)
        val translationMatrix = TransformationMatrix.translate(positionOffset)
        val rotationMatrix = TransformationMatrix.rotate(rotationOffset)

        return translationMatrix * rotationMatrix * scaleMatrix
    }

    private inline fun <reified T : RenderProperty<*>> getProperty(properties: List<RenderProperty<*>>): T? {
        return properties.firstOrNull { it is T } as? T
    }

}