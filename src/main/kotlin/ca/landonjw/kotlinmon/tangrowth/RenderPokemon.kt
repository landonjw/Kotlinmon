package ca.landonjw.kotlinmon.tangrowth

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.SmdCache
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import ca.landonjw.kotlinmon.client.render.models.test.*
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.ResourceLocation

class RenderPokemon(
    manager: EntityRendererManager
) : EntityRenderer<PokemonEntity>(manager) {

    var model: TestModel? = null
    var animation: TestAnimation? = null

    override fun render(
        entity: PokemonEntity,
        entityYaw: Float,
        partialTicks: Float,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        packedLight: Int
    ) {
//        val modelLocation = ResourceLocation(Kotlinmon.MODID, "pokemon/crobat/model/crobat.smd")
//        val textureLocation = ResourceLocation(Kotlinmon.MODID, "pokemon/crobat/texture/crobat.png")
//        val model = SmdCache.getModel(modelLocation, textureLocation)
//
//        val animationLocation = ResourceLocation(Kotlinmon.MODID, "pokemon/crobat/animations/idle.smd")
//        val animation = SmdCache.getModelAnimation(animationLocation, model)
//
//        if (model.animations["idle"] == null) {
//            model.addAnimation("idle", animation)
//        }
//        if (model.currentAnimation != model.animations["idle"]) {
//            model.setAnimation("idle")
//        }

//        model.setAnimation("idle")

//        SmdModelRenderer.render(matrixStack, model)

        if (model == null) {
            model = TestModelLoader.load()
            animation = TestAnimationLoader.load(model!!)
        }

        TestRender.render(matrixStack, model!!, animation!!)
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