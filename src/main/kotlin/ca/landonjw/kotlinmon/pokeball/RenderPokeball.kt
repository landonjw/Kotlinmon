package ca.landonjw.kotlinmon.pokeball

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.registry.loaders.SmdPQCLoader
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.RotationOffset
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import ca.landonjw.kotlinmon.util.math.geometry.toRadians
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.ResourceLocation

class RenderPokeball(manager: EntityRendererManager): EntityRenderer<PokeballEntity>(manager) {

    val pokeballPQC = ResourceLocation(Kotlinmon.MODID, "pokeballs/pokeball.pqc")
    var pokeballModel: SmdModel? = null

    init {
        println("foo")
    }

    override fun render(
        entity: PokeballEntity,
        entityYaw: Float,
        partialTicks: Float,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        packedLight: Int
    ) {
        if (pokeballModel == null) {
            pokeballModel = SmdPQCLoader.load(pokeballPQC)
        }
        pokeballModel?.replaceProperty<RotationOffset> {
            val copy = it.value.copy()
            copy.add(1f.toRadians(), 1f.toRadians(), 1f.toRadians())
            return@replaceProperty RotationOffset(copy)
        }
        SmdModelRenderer.render(matrixStack, pokeballModel!!)
    }

    override fun getEntityTexture(entity: PokeballEntity) = null

    override fun shouldRender(
        livingEntityIn: PokeballEntity,
        camera: ClippingHelper,
        camX: Double,
        camY: Double,
        camZ: Double
    ): Boolean {
        return true
    }

}