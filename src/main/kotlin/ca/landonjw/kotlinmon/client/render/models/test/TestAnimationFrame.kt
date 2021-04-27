package ca.landonjw.kotlinmon.client.render.models.test

import ca.landonjw.kotlinmon.Matrix4f
import ca.landonjw.kotlinmon.util.math.geometry.Axis
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix
import ca.landonjw.kotlinmon.util.math.geometry.toRadians

class TestAnimationFrame(
    val transformations: MutableMap<Int, Matrix4f>,
    val model: TestModel
) {

    fun fixup() {
        val radians = 0f.toRadians()
        val rotator = matrix4FromLocRot(0f, 0f, 0f, radians, 0f, 0f)
        Matrix4f.mul(rotator, transformations[1], transformations[1])
    }

    fun reform() {
        for (index in 0 until transformations.size) {
            val bone = model.getBoneById(index)!!
            val parent = bone.parent ?: continue
            val temp = Matrix4f.mul(transformations[parent.id]!!, transformations[index]!!, null)
            transformations[index] = temp
        }
    }

}