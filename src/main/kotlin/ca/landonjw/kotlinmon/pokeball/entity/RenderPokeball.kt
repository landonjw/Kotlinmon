package ca.landonjw.kotlinmon.pokeball.entity

import ca.landonjw.kotlinmon.Kotlinmon
//import ca.landonjw.kotlinmon.client.render.SmdFileLoader
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.ResourceLocation


//class RenderPokeball(manager: EntityRendererManager) : EntityRenderer<PokeballEntity>(manager) {
//
//    val model: SmdModel
//    val texture: ResourceLocation = ResourceLocation(Kotlinmon.MODID, "pokeballs/textures/pokeball.png")
//
//    init {
//        Kotlinmon.LOGGER.info("Pokeball renderer initialized")
//        val resourceLoc = ResourceLocation(Kotlinmon.MODID, "pokeballs/models/pokeball.smd")
//        val definition = SmdFileLoader.loadDefinition(resourceLoc)
//        model = SmdModel(definition)
//    }
//
//    override fun render(
//        entity: PokeballEntity,
//        entityYaw: Float,
//        partialTicks: Float,
//        matrixStack: MatrixStack,
//        buffer: IRenderTypeBuffer,
//        packedLight: Int
//    ) {
//        model.render(matrixStack, 0.10f, texture, 0.62f)
//        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight)
//    }
//
//    override fun getEntityTexture(entity: PokeballEntity) = null
//}