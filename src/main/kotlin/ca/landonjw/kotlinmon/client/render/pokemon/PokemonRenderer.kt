package ca.landonjw.kotlinmon.client.render.pokemon

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.pokemon.getModelTextureLocation
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntity
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.ResourceLocation

class PokemonRenderer(
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
        val clientComponent = entity.clientComponent

        val model = entity.clientComponent.model ?: return
        val modelTextureLoc = getModelTextureLocation(clientComponent.species, clientComponent.form, clientComponent.texture)

        /*
         * We change the texture on the model's mesh to accommodate for different textures to be implemented
         * for a single model. After the model is rendered, we simply set the default texture back.
         */
        val defaultTexture = model.skeleton.mesh.texture
        setModelTexture(model, modelTextureLoc)

        model.setAnimation("idle")
        SmdModelRenderer.render(matrixStack, model)

        model.skeleton.mesh.texture = defaultTexture
    }

    private fun setModelTexture(model: SmdModel, texture: ResourceLocation) {
        // Check that texture exists in assets
        val modelTextureURL = Kotlinmon::class.java.getResource("/assets/${texture.namespace}/${texture.path}")
        if (modelTextureURL != null) model.skeleton.mesh.texture = texture
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