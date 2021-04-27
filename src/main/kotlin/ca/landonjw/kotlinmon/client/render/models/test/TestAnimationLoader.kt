package ca.landonjw.kotlinmon.client.render.models.test

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.Matrix4f
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.SmdModelAnimationFileLoader
import ca.landonjw.kotlinmon.util.math.geometry.Axis
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix
import net.minecraft.util.ResourceLocation

object TestAnimationLoader {

    fun load(model: TestModel): TestAnimation {
        val definition = SmdModelAnimationFileLoader.load(ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/animations/idle.smd"))
        val frames: MutableList<TestAnimationFrame> = mutableListOf()
        for (frame in definition.frames) {
            val transformations: MutableMap<Int, Matrix4f> = mutableMapOf()
            for (transformation in frame.transformations) {
                val loc = transformation.position
                val rot = transformation.rotation
                val matrix = matrix4FromLocRot(loc.x, loc.y, loc.z, rot.x, rot.y, rot.z)
                transformations[transformation.boneId] = matrix
            }
            frames.add(TestAnimationFrame(transformations, model))
        }
        return TestAnimation(model, frames)
    }

}