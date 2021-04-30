package ca.landonjw.kotlinmon.pokemon

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.registry.SmdModelRegistry
import ca.landonjw.kotlinmon.client.render.models.smd.registry.loaders.SmdPQCLoader
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import com.mojang.blaze3d.matrix.MatrixStack
import kotlinx.coroutines.Deferred
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.ResourceLocation
import net.minecraft.world.biome.Biome

class RenderPokemon(
    manager: EntityRendererManager
) : EntityRenderer<PokemonEntity>(manager) {

    val kinglerPQC = ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/kingler.pqc")

    override fun render(
        entity: PokemonEntity,
        entityYaw: Float,
        partialTicks: Float,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        packedLight: Int
    ) {
        val model: Deferred<SmdModel> = SmdModelRegistry.getOrLoad(kinglerPQC)
        if (model.isCompleted && !model.isCancelled) {
            val actual = model.getCompleted()
            actual.setAnimation("idle")
            SmdModelRenderer.render(matrixStack, actual)
        }
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