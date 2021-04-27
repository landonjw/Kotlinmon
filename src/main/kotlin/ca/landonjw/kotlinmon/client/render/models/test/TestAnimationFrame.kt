package ca.landonjw.kotlinmon.client.render.models.test

import ca.landonjw.kotlinmon.util.math.geometry.Axis
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix
import ca.landonjw.kotlinmon.util.math.geometry.toRadians

class TestAnimationFrame(
    val transformations: MutableMap<Int, TransformationMatrix>,
    val model: TestModel
) {

    fun fixup() {
        val radians = 0f.toRadians()
        val translation = TransformationMatrix.translate(GeometricPoint())
        val rotationZ = TransformationMatrix.rotate(radians, Axis.Z_AXIS)
        val rotationY = TransformationMatrix.rotate(0f, Axis.Y_AXIS)
        val rotationX = TransformationMatrix.rotate(0f, Axis.X_AXIS)
        val transformation = translation * rotationZ * rotationY * rotationX
        transformations[1] = transformation * transformations[1]!!
    }

    fun reform() {
        for (index in 0 until transformations.size) {
            val bone = model.getBoneById(index)!!
            val parent = bone.parent ?: continue
            transformations[index] = transformations[parent.id]!! * transformations[index]!!
        }
    }

}