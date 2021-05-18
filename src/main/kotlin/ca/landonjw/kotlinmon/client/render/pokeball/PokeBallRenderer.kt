package ca.landonjw.kotlinmon.client.render.pokeball

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.RotationOffset
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.Scale
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import ca.landonjw.kotlinmon.client.render.models.smd.repository.ModelRepository
import ca.landonjw.kotlinmon.common.pokeball.entity.PokeBallEntity
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.math.vector.Vector3f

class PokeBallRenderer<T: PokeBallEntity>(manager: EntityRendererManager) : EntityRenderer<T>(manager) {

    private val modelRepository: ModelRepository by KotlinmonDI.inject(tag = "async")

    override fun render(
        entity: T,
        entityYaw: Float,
        partialTicks: Float,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        packedLight: Int
    ) {
        val pokeBall: PokeBall = entity.type
        val pokeBallModel = modelRepository[pokeBall.modelLocation] ?: return

        pokeBallModel.replaceProperty(RotationOffset(entity.orientationController.orientation))
        pokeBallModel.replaceProperty(Scale(Vector3f(0.1f, 0.1f, 0.1f)))
        SmdModelRenderer.render(matrixStack, pokeBallModel)
    }

    override fun getEntityTexture(entity: T) = null

    override fun shouldRender(
        livingEntityIn: T,
        camera: ClippingHelper,
        camX: Double,
        camY: Double,
        camZ: Double
    ): Boolean {
        return true
    }

}