package ca.landonjw.kotlinmon.client.render.models.smd.mesh

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.SmdCache
import net.minecraft.util.ResourceLocation
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SmdMeshTest {

    private val kinglerModel = ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/model/kingler.smd")
    private val kinglerWalkAnimation = ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/animations/walk.smd")

//    @Test
//    fun getVertices() {
//        val textureLocation = ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/texture/kingler.png")
//        val model = SmdCache.getModel(kinglerModel, textureLocation)
//
//        val animation = SmdCache.getModelAnimation(kinglerWalkAnimation, model)
//
//        model.addAnimation("walk", animation)
//        model.setAnimation("walk")
//
//        for (frame in animation.frames) {
//            val movement = frame.boneMovements.firstOrNull { it.boneId == 16 }
//            if (movement != null) println(movement)
//        }
//
//        for (i in 0 until animation.totalFrames) {
//            model.currentAnimation?.apply()
//            val vertices = model.mesh.getVertices()
//            println(vertices[12])
//        }
//        println()
//        println()
//        println()
//        for (bone in model.skeleton.boneIdToBone.values) {
//            println(bone.translation)
//        }
//        for (i in 0 until animation.totalFrames) {
//            model.currentAnimation?.apply()
//            val vertices = model.mesh.getVertices()
//            println(vertices[2].translation)
//        }
//
//
//    }

}