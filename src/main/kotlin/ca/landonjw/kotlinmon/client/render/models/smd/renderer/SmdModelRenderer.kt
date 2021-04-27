package ca.landonjw.kotlinmon.client.render.models.smd.renderer

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.api.renderer.ModelRenderer
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.MeshVertex
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelBone
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
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
import net.minecraft.util.math.vector.Vector3f
import org.lwjgl.opengl.GL11

// TODO: Refactor all of this
object SmdModelRenderer : ModelRenderer<SmdModel> {

    private val textureManager = Minecraft.getInstance().textureManager

    private val wireFrameFormat = VertexFormat(
        ImmutableList.of(
            DefaultVertexFormats.POSITION_3F,
            DefaultVertexFormats.NORMAL_3B,
            DefaultVertexFormats.PADDING_1B,
            DefaultVertexFormats.COLOR_4UB
        )
    )

    private val meshFormat = VertexFormat(
        ImmutableList.of(
            DefaultVertexFormats.POSITION_3F,
            DefaultVertexFormats.TEX_2F,
            DefaultVertexFormats.NORMAL_3B,
            DefaultVertexFormats.PADDING_1B
        )
    )

    private var dirty: Boolean = false
    private var count: Int = 0

    override fun render(matrix: MatrixStack, model: SmdModel) {
        model.currentAnimation?.apply() ?: model.tPose()
//        model.skeleton.resetPosture()
//        model.currentAnimation?.apply(0)
//        model.currentAnimation?.apply(1)
//        model.currentAnimation?.apply()
//        val bone = model.skeleton[9]!!
//        val rotation = bone.lastRotation!!.copy()
//        rotation.add(0f, 0f, count.toFloat().toRadians())
//        bone.move(rotation = rotation)
//        val translation = bone.jointLocation + GeometricPoint(0f, 0f, count.toFloat().toRadians())
//        bone.move(translation = translation)
        count = (count + 3) % 360
//        if (dirty) {
//            model.skeleton[9]?.move(GeometricPoint(0f, 0f, 0f), Vector3f(1f.toRadians(), 0f, 0f))
//        }

        val buffer = Tessellator.getInstance().buffer
        RenderSystem.enableDepthTest()

        renderMesh(buffer, matrix, model)
        renderSkeleton(matrix, buffer, model.skeleton)
    }

    private fun renderMesh(buffer: BufferBuilder, matrix: MatrixStack, model: SmdModel) {
        textureManager.bindTexture(model.mesh.texture.resourceLocation)
        buffer.begin(GL11.GL_TRIANGLES, meshFormat)
        val vertices = model.mesh.getVertices()
        vertices.forEach { vertex -> renderVertex(matrix, buffer, vertex, model.scale) }
        Tessellator.getInstance().draw()
    }

    private fun renderSkeleton(matrix: MatrixStack, buffer: BufferBuilder, skeleton: SmdModelSkeleton) {
        textureManager.bindTexture(ResourceLocation(Kotlinmon.MODID, "pokemon/triangle/white.png"))
        for (bone in skeleton.bones) {
            val bonePos = bone.jointLocation
            val origin = bonePos + GeometricPoint(-0.125f, 0.125f, -0.125f)

            if (bone.id == 10) {
                renderCube(buffer, matrix, origin, 0.25f, RGBA(255, 0, 0, 255))
            } else {
                renderCube(buffer, matrix, origin, 0.25f)
            }

            renderLocalRotationAxis(buffer, matrix, bone)
            if (bone.parent != null) renderLineBetweenBones(buffer, matrix, bone, bone.parent)
        }
    }

    fun renderLineBetweenBones(
        buffer: BufferBuilder,
        matrix: MatrixStack,
        a: SmdModelBone,
        b: SmdModelBone,
        rgba: RGBA = RGBA(0, 0, 0, 255)
    ) {
        buffer.begin(GL11.GL_LINES, wireFrameFormat)

        buffer
            .pos(matrix.last.matrix, a.jointLocation.x, a.jointLocation.y, a.jointLocation.z)
            .normal(matrix.last.normal, a.jointLocation.x, a.jointLocation.y, a.jointLocation.z)
            .color(rgba.red, rgba.green, rgba.blue, rgba.alpha)
            .endVertex()

        buffer
            .pos(matrix.last.matrix, b.jointLocation.x, b.jointLocation.y, b.jointLocation.z)
            .normal(matrix.last.normal, b.jointLocation.x, b.jointLocation.y, b.jointLocation.z)
            .color(rgba.red, rgba.green, rgba.blue, rgba.alpha)
            .endVertex()

        Tessellator.getInstance().draw()
    }

    private fun renderLocalRotationAxis(buffer: BufferBuilder, matrix: MatrixStack, bone: SmdModelBone) {
        val xAxis = bone.jointLocation + bone.orientation * GeometricPoint(0.5f, 0f, 0f)
        val yAxis = bone.jointLocation + bone.orientation * GeometricPoint(0f, 0.5f, 0f)
        val zAxis = bone.jointLocation + bone.orientation * GeometricPoint(0f, 0f, 0.5f)

        buffer.begin(GL11.GL_LINES, wireFrameFormat)

        buffer
            .pos(matrix.last.matrix, bone.jointLocation.x, bone.jointLocation.y, bone.jointLocation.z)
            .normal(matrix.last.normal, bone.jointLocation.x, bone.jointLocation.y, bone.jointLocation.z)
            .color(255, 0, 0, 255)
            .endVertex()

        buffer
            .pos(matrix.last.matrix, xAxis.x, xAxis.y, xAxis.z)
            .normal(matrix.last.normal, xAxis.x, xAxis.y, xAxis.z)
            .color(255, 0, 0, 255)
            .endVertex()

        buffer
            .pos(matrix.last.matrix, bone.jointLocation.x, bone.jointLocation.y, bone.jointLocation.z)
            .normal(matrix.last.normal, bone.jointLocation.x, bone.jointLocation.y, bone.jointLocation.z)
            .color(0, 255, 0, 255)
            .endVertex()

        buffer
            .pos(matrix.last.matrix, yAxis.x, yAxis.y, yAxis.z)
            .normal(matrix.last.normal, yAxis.x, yAxis.y, yAxis.z)
            .color(0, 255, 0, 255)
            .endVertex()

        buffer
            .pos(matrix.last.matrix, bone.jointLocation.x, bone.jointLocation.y, bone.jointLocation.z)
            .normal(matrix.last.normal, bone.jointLocation.x, bone.jointLocation.y, bone.jointLocation.z)
            .color(0, 0, 255, 255)
            .endVertex()

        buffer
            .pos(matrix.last.matrix, zAxis.x, zAxis.y, zAxis.z)
            .normal(matrix.last.normal, zAxis.x, zAxis.y, zAxis.z)
            .color(0, 0, 255, 255)
            .endVertex()

        Tessellator.getInstance().draw()
    }

    private fun renderRectangle(
        buffer: BufferBuilder,
        matrix: MatrixStack,
        origin: GeometricPoint,
        length: Float,
        width: Float,
        height: Float,
        rgba: RGBA = RGBA(0, 0, 0, 255)
    ) {
        var a = origin
        var b = origin + GeometricPoint(length, 0f, 0f)
        var c = origin + GeometricPoint(length, -height, 0f)
        var d = origin + GeometricPoint(0f, -height, 0f)
        renderSquareFace(buffer, matrix, a, b, c, d, rgba)

        a = origin
        b = origin + GeometricPoint(0f, 0f, width)
        c = origin + GeometricPoint(length, 0f, width)
        d = origin + GeometricPoint(length, 0f, 0f)
        renderSquareFace(buffer, matrix, a, b, c, d, rgba)

        a = origin
        b = origin + GeometricPoint(0f, 0f, width)
        c = origin + GeometricPoint(0f, -height, width)
        d = origin + GeometricPoint(0f, -height, 0f)
        renderSquareFace(buffer, matrix, a, b, c, d, rgba)

        a = origin + GeometricPoint(length, 0f, 0f)
        b = a + GeometricPoint(0f, 0f, width)
        c = a + GeometricPoint(0f, -height, width)
        d = a + GeometricPoint(0f, -height, 0f)
        renderSquareFace(buffer, matrix, a, b, c, d, rgba)

        a = origin + GeometricPoint(0f, 0f, width)
        b = a + GeometricPoint(length, 0f, 0f)
        c = a + GeometricPoint(length, -height, 0f)
        d = a + GeometricPoint(0f, -height, 0f)
        renderSquareFace(buffer, matrix, a, b, c, d, rgba)

        a = origin + GeometricPoint(0f, -height, 0f)
        b = a + GeometricPoint(length, 0f, 0f)
        c = a + GeometricPoint(length, 0f, width)
        d = a + GeometricPoint(0f, 0f, width)
        renderSquareFace(buffer, matrix, a, b, c, d, rgba)
    }

    private fun renderCube(
        buffer: BufferBuilder,
        matrix: MatrixStack,
        origin: GeometricPoint,
        size: Float,
        rgba: RGBA = RGBA(0, 0, 0, 255)
    ) {
        renderRectangle(buffer, matrix, origin, size, size, size, rgba)
    }

    private fun renderSquareFace(
        buffer: BufferBuilder,
        matrix: MatrixStack,
        a: GeometricPoint,
        b: GeometricPoint,
        c: GeometricPoint,
        d: GeometricPoint,
        rgba: RGBA = RGBA(0, 0, 0, 255)
    ) {
        renderTriangle(buffer, matrix, a, b, c, rgba)
        renderTriangle(buffer, matrix, c, b, a, rgba)
        renderTriangle(buffer, matrix, a, d, c, rgba)
        renderTriangle(buffer, matrix, c, d, a, rgba)
    }

    private fun renderTriangle(
        buffer: BufferBuilder,
        matrix: MatrixStack,
        a: GeometricPoint,
        b: GeometricPoint,
        c: GeometricPoint,
        rgba: RGBA = RGBA(0, 0, 0, 255)
    ) {
        buffer.begin(GL11.GL_TRIANGLES, wireFrameFormat)

        buffer
            .pos(matrix.last.matrix, a.x, a.y, a.z)
            .normal(matrix.last.normal, a.x, a.y, a.z)
            .color(rgba.red, rgba.green, rgba.blue, rgba.alpha)
            .endVertex()

        buffer
            .pos(matrix.last.matrix, b.x, b.y, b.z)
            .normal(matrix.last.normal, b.x, b.y, b.z)
            .color(rgba.red, rgba.green, rgba.blue, rgba.alpha)
            .endVertex()

        buffer
            .pos(matrix.last.matrix, c.x, c.y, c.z)
            .normal(matrix.last.normal, c.x, c.y, c.z)
            .color(rgba.red, rgba.green, rgba.blue, rgba.alpha)
            .endVertex()

        Tessellator.getInstance().draw()
    }

    private fun renderVertex(matrix: MatrixStack, buffer: BufferBuilder, vertex: MeshVertex, modelScale: Float) {
        val u = vertex.uvMap.a
        val v = vertex.uvMap.b

        val transformation = TransformationBuilder()
            .rotate(90f.toRadians(), Axis.X_AXIS)
//            .translate(GeometricPoint(0f, 20f, 0f))
//            .scale(5f, 1f, f)
            .build()

        val originalPoint = GeometricPoint(vertex.position.x, vertex.position.y, vertex.position.z)
        val point = TransformationMatrix.transform(transformation, originalPoint)

        buffer
            .pos(matrix.last.matrix, point.x, point.y, point.z)
            .tex(u, v)
            .normal(matrix.last.normal, vertex.normal.x, vertex.normal.y, vertex.normal.z)
            .endVertex()
    }

}

data class RGBA(val red: Int, val green: Int, val blue: Int, val alpha: Int)