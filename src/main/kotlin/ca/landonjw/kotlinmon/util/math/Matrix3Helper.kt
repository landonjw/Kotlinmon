package ca.landonjw.kotlinmon.util.math

import net.minecraft.util.math.vector.Vector3f
import kotlin.math.cos
import kotlin.math.sin

object Matrix3Helper {

    fun rotationMatrix(rotation: Vector3f): Matrix3 {
        val rotX = rotation.x.toDouble()
        val rotY = rotation.y.toDouble()
        val rotZ = rotation.z.toDouble()

        val matrix = Matrix3()
        matrix[0][0] = (cos(rotZ) * cos(rotY)).toFloat()
        matrix[0][1] = (cos(rotZ) * sin(rotY) * sin(rotX) - sin(rotZ) * cos(rotX)).toFloat()
        matrix[0][2] = (cos(rotZ) * sin(rotY) * cos(rotX) - sin(rotZ) * sin(rotX)).toFloat()
        matrix[1][0] = (sin(rotZ) * cos(rotY)).toFloat()
        matrix[1][1] = (sin(rotZ) * sin(rotY) * sin(rotX) + cos(rotZ) * cos(rotX)).toFloat()
        matrix[1][2] = (sin(rotZ) * sin(rotY) * cos(rotX) - cos(rotZ) * sin(rotX)).toFloat()
        matrix[2][0] = (-1.0 * sin(rotY)).toFloat()
        matrix[2][1] = (cos(rotY) * sin(rotX)).toFloat()
        matrix[2][2] = (cos(rotY) * cos(rotX)).toFloat()
        return matrix
    }

    fun determinant(matrix: Matrix3): Float {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) +
                matrix[0][1] * (matrix[1][2] * matrix[2][0] - matrix[1][0] * matrix[2][2]) +
                matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0])
    }

}