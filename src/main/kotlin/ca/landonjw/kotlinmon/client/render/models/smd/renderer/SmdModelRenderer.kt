package ca.landonjw.kotlinmon.client.render.models.smd.renderer

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.SmdMeshVertex
import ca.landonjw.kotlinmon.common.util.math.geometry.GeometricNormal
import ca.landonjw.kotlinmon.common.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.common.util.math.geometry.TransformationMatrix
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

/**
 * The renderer used to render `.smd` models.
 *
 * @author landonjw
 */
object SmdModelRenderer {

    /** Allows us to bind the model's texture to the model. */
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

    fun render(matrix: MatrixStack, model: SmdModel) {
        // Get the next frame of current animation, if there is one
        // TODO: Remove this and make a separate animation handler
        if (model.currentAnimation != null) model.currentAnimation?.animate()
        val globalTransforms = getGlobalTransforms(model.renderProperties)

        textureManager.bindTexture(model.skeleton.mesh.texture)

        // Start drawing every vertex in the model's mesh
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
        properties: List<SmdRenderProperty<*>>
    ) {
        val glitchNoise = getProperty<GlitchNoise>(properties)?.value

        val position: GeometricPoint
        val normal: GeometricNormal

        if (glitchNoise != null) {
            val shakeRotation = Vector3f(
                glitchNoise.x * random.nextFloat(),
                glitchNoise.y * random.nextFloat(),
                glitchNoise.z * random.nextFloat()
            )
            val shakeMatrix = TransformationMatrix.rotate(shakeRotation)
            position = globalTransforms * shakeMatrix * vertex.position
            normal = globalTransforms * shakeMatrix * vertex.normal
        }
        else {
            position = globalTransforms * vertex.position
            normal = globalTransforms * vertex.normal
        }

        buffer
            .pos(matrix.last.matrix, position.x, position.y, position.z)
            .tex(vertex.u, vertex.v)
            .normal(matrix.last.normal, normal.x, normal.y, normal.z)
            .endVertex()
    }

    private fun getGlobalTransforms(properties: List<SmdRenderProperty<*>>): TransformationMatrix {
        val scale = getProperty<Scale>(properties)?.value ?: Vector3f(1f, 1f, 1f)
        val positionOffset = getProperty<PositionOffset>(properties)?.value ?: GeometricPoint()
        val rotationOffset = getProperty<RotationOffset>(properties)?.value ?: Vector3f(0f, 0f, 0f)

        val scaleMatrix = TransformationMatrix.scale(scale.x, scale.y, scale.z)
        val translationMatrix = TransformationMatrix.translate(positionOffset)
        val rotationMatrix = TransformationMatrix.rotate(rotationOffset)

        return translationMatrix * rotationMatrix * scaleMatrix
    }

    private inline fun <reified T : SmdRenderProperty<*>> getProperty(properties: List<SmdRenderProperty<*>>): T? {
        return properties.firstOrNull { it is T } as? T
    }

}