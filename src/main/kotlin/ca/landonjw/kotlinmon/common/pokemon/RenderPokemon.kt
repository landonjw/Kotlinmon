package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.registry.SmdModelRegistry
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import com.mojang.blaze3d.matrix.MatrixStack
import kotlinx.coroutines.Deferred
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11

class RenderPokemon(
    manager: EntityRendererManager
) : EntityRenderer<PokemonEntity>(manager) {

    override fun render(
        entity: PokemonEntity,
        entityYaw: Float,
        partialTicks: Float,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        packedLight: Int
    ) {
        val model: Deferred<SmdModel> = SmdModelRegistry.getOrLoad(entity.form.modelLocation)
        if (model.isCompleted && !model.isCancelled) {
            val actual = model.getCompleted()
            actual.setAnimation("idle")
            SmdModelRenderer.render(matrixStack, actual)
        }
//        renderBoundingBox(entity, matrixStack, buffer)
    }

//    private fun renderBoundingBox(entity: PokemonEntity, matrixStack: MatrixStack, buffer: IRenderTypeBuffer) {
//        val lineBuffer = Tessellator.getInstance().buffer
//        lineBuffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
//
//        val boundingBox = entity.boundingBox
//        val maxX = (boundingBox.maxX - boundingBox.minX).toFloat()
//        val maxY = (boundingBox.maxY - boundingBox.minY).toFloat()
//        val maxZ = (boundingBox.maxZ - boundingBox.minZ).toFloat()
//
//        matrixStack.translate(-maxX / 2.0, 0.0, -maxZ / 2.0)
//
//        lineBuffer
//            .pos(matrixStack.last.matrix, 0f, 0f, 0f)
//            .color(255, 255, 255, 255)
//            .endVertex()
//
//        lineBuffer
//            .pos(matrixStack.last.matrix, maxX, 0f, 0f)
//            .color(255, 255, 255, 255)
//            .endVertex()
//
//        lineBuffer
//            .pos(matrixStack.last.matrix, 0f, 0f, 0f)
//            .color(255, 255, 255, 255)
//            .endVertex()
//
//        lineBuffer
//            .pos(matrixStack.last.matrix, 0f, maxY, 0f)
//            .color(255, 255, 255, 255)
//            .endVertex()
//
//        lineBuffer
//            .pos(matrixStack.last.matrix, 0f, 0f, 0f)
//            .color(255, 255, 255, 255)
//            .endVertex()
//
//        lineBuffer
//            .pos(matrixStack.last.matrix, 0f, 0f, maxZ)
//            .color(255, 255, 255, 255)
//            .endVertex()
//
//        Tessellator.getInstance().draw()
//    }

    override fun getEntityTexture(entity: PokemonEntity) = null

    // TODO: Make better. Should take into account player frustum to prevent unnecessary rendering.
    override fun shouldRender(
        livingEntityIn: PokemonEntity,
        camera: ClippingHelper,
        camX: Double,
        camY: Double,
        camZ: Double
    ): Boolean {
        return true
    }

}