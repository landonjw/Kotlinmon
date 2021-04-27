package ca.landonjw.kotlinmon.client.render.models.test

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.SmdModelAnimationFileLoader
import ca.landonjw.kotlinmon.util.math.geometry.Axis
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix
import net.minecraft.util.ResourceLocation

object TestAnimationLoader {

    fun load(model: TestModel): TestAnimation {
        val definition = SmdModelAnimationFileLoader.load(ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/animations/idle.smd"))
        val frames: MutableList<TestAnimationFrame> = mutableListOf()
        for (frame in definition.frames) {
            val transformations: MutableMap<Int, TransformationMatrix> = mutableMapOf()
            for (transformation in frame.transformations) {
                val translation = TransformationMatrix.translate(transformation.position)
                val rotationZ = TransformationMatrix.rotate(transformation.rotation.z, Axis.Z_AXIS)
                val rotationY = TransformationMatrix.rotate(transformation.rotation.y, Axis.Y_AXIS)
                val rotationX = TransformationMatrix.rotate(transformation.rotation.x, Axis.X_AXIS)
                val matrix = translation * rotationZ * rotationY * rotationX
                println("""
                    ${matrix[0][0]} ${matrix[0][1]} ${matrix[0][2]} ${matrix[0][3]}
                    ${matrix[1][0]} ${matrix[1][1]} ${matrix[1][2]} ${matrix[1][3]}
                    ${matrix[2][0]} ${matrix[2][1]} ${matrix[2][2]} ${matrix[2][3]}
                    ${matrix[3][0]} ${matrix[3][1]} ${matrix[3][2]} ${matrix[3][3]}
                """.trimIndent())
                transformations[transformation.boneId] = matrix
            }
            frames.add(TestAnimationFrame(transformations, model))
        }
        return TestAnimation(model, frames)
    }

}