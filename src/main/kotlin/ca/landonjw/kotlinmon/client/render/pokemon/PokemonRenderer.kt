package ca.landonjw.kotlinmon.client.render.pokemon

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntity
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager

class PokemonRenderer(
    manager: EntityRendererManager
) : EntityRenderer<PokemonEntity>(manager) {

    private val modelRenderer: SmdModelRenderer by KotlinmonDI.inject()

    override fun render(
        entity: PokemonEntity,
        entityYaw: Float,
        partialTicks: Float,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        packedLight: Int
    ) {
        val model = entity.clientComponent.model ?: return
        model.setAnimation("idle")
        modelRenderer.render(matrixStack, model)
    }

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