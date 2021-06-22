package ca.landonjw.kotlinmon.client.render.models.smd.renderer

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.SmdMeshVertex
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import com.google.common.collect.ImmutableList
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.util.math.vector.Quaternion
import net.minecraft.util.math.vector.Vector3f
import org.lwjgl.opengl.GL11

/**
 * The renderer used to render `.smd` models.
 *
 * @author landonjw
 */
class SmdModelRenderer {

    private val meshFormat = VertexFormat(
        ImmutableList.of(
            DefaultVertexFormats.ELEMENT_POSITION,
            DefaultVertexFormats.ELEMENT_UV0,
            DefaultVertexFormats.ELEMENT_NORMAL,
            DefaultVertexFormats.ELEMENT_PADDING
        )
    )

    fun render(matrix: MatrixStack, model: SmdModel) {
        // Get the next frame of current animation, if there is one
        // TODO: Remove this and make a separate animation handler
        if (model.currentAnimation != null) model.currentAnimation?.animate()

        /* Apply transformations that apply to every vertex in the model
         *
         * Transformations of this fashion are done using the origin matrix rather
         * than our in-house TransformationMatrix in order to save on computation of
         * several matrix multiplication operations, resulting in higher frames,
         * and it also makes more sense to apply once than on every vertex. - landonjw
         */
        applyGlobalTransforms(matrix, model.renderProperties)

        // TODO: Check if this has performance impact
        Minecraft.getInstance().textureManager.bind(model.skeleton.mesh.texture)

        // Start drawing every vertex in the model's mesh
        val buffer = Tessellator.getInstance().builder
        RenderSystem.enableDepthTest()
        buffer.begin(GL11.GL_TRIANGLES, meshFormat)

        model.skeleton.mesh.vertices.forEach { vertex ->
            renderVertex(matrix, buffer, vertex)
        }
        Tessellator.getInstance().end()
    }

    private fun renderVertex(
        matrix: MatrixStack,
        buffer: BufferBuilder,
        vertex: SmdMeshVertex
    ) {
        buffer
            .vertex(matrix.last().pose(), vertex.position.x, vertex.position.y, vertex.position.z)
            .uv(vertex.u, vertex.v)
            .normal(matrix.last().normal(), vertex.normal.x, vertex.normal.y, vertex.normal.z)
            .endVertex()
    }

    private fun applyGlobalTransforms(matrix: MatrixStack, properties: List<SmdRenderProperty<*>>) {
        val globalTranslation = getProperty<PositionOffset>(properties)?.value
        if (globalTranslation != null) applyGlobalTranslation(matrix, globalTranslation)

        val globalRotation = getProperty<RotationOffset>(properties)?.value
        if (globalRotation != null) applyGlobalRotation(matrix, globalRotation)

        val globalScalars = getProperty<Scale>(properties)?.value
        if (globalScalars != null) applyGlobalScale(matrix, globalScalars)
    }

    private fun applyGlobalTranslation(matrix: MatrixStack, translation: GeometricPoint) {
        matrix.translate(translation.x.toDouble(), translation.y.toDouble(), translation.z.toDouble())
    }

    private fun applyGlobalRotation(matrix: MatrixStack, rotation: Vector3f) {
        matrix.mulPose(Quaternion(Vector3f.ZP, rotation.z(), false))
        matrix.mulPose(Quaternion(Vector3f.YP, rotation.y(), false))
        matrix.mulPose(Quaternion(Vector3f.XP, rotation.x(), false))
    }

    private fun applyGlobalScale(matrix: MatrixStack, scalars: Vector3f) {
        matrix.scale(scalars.x(), scalars.y(), scalars.z())
    }

    private inline fun <reified T : SmdRenderProperty<*>> getProperty(properties: List<SmdRenderProperty<*>>): T? {
        return properties.firstOrNull { it is T } as? T
    }

}