package ca.landonjw.kotlinmon.client.render.pokeball

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.RotationOffset
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.Scale
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import ca.landonjw.kotlinmon.client.render.models.smd.repository.ModelRepository
import ca.landonjw.kotlinmon.common.pokeball.entity.PokeBallEntity
import ca.landonjw.kotlinmon.util.math.geometry.toRadians
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.math.vector.Vector3f

class PokeBallRenderer(manager: EntityRendererManager): EntityRenderer<PokeBallEntity>(manager) {

    private val modelRepository: ModelRepository by KotlinmonDI.inject(tag = "async")
    private val modelRenderer: SmdModelRenderer by KotlinmonDI.inject()

    override fun render(
        entity: PokeBallEntity,
        entityYaw: Float,
        partialTicks: Float,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        packedLight: Int
    ) {
        val pokeBall: PokeBall = entity.type
        val pokeBallModel = modelRepository[pokeBall.modelLocation] ?: return

        // TODO: Replace with entity orientation variables
        pokeBallModel.replaceProperty(RotationOffset(Vector3f(
            90f.toRadians() + (entity.ticksExisted.toFloat() * 4f).toRadians(),
            0f + (entity.ticksExisted.toFloat() * 4f).toRadians(),
            0f
        )))

        pokeBallModel.replaceProperty(Scale(Vector3f(0.1f, 0.1f, 0.1f)))
        modelRenderer.render(matrixStack, pokeBallModel)
    }

    override fun getEntityTexture(entity: PokeBallEntity) = null

    override fun shouldRender(
        livingEntityIn: PokeBallEntity,
        camera: ClippingHelper,
        camX: Double,
        camY: Double,
        camZ: Double
    ): Boolean {
        return true
    }

}