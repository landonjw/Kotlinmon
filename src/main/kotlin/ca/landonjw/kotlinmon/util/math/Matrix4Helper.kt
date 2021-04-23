package ca.landonjw.kotlinmon.util.math

import net.minecraft.util.math.vector.Vector3f
import net.minecraft.util.math.vector.Vector4f
import kotlin.math.cos
import kotlin.math.sin

object Matrix4Helper {

    fun translate(matrix: Matrix4, vector: Vector3f): Matrix4 {
        val result = Matrix4.copy(matrix)
        result[0][3] += matrix[0][0] * vector.x + matrix[0][1] * vector.y + matrix[0][2] * vector.z
        result[1][3] += matrix[1][0] * vector.x + matrix[1][1] * vector.y + matrix[1][2] * vector.z
        result[2][3] += matrix[2][0] * vector.x + matrix[2][1] * vector.y + matrix[2][2] * vector.z
        result[3][3] += matrix[3][0] * vector.x + matrix[3][1] * vector.y + matrix[3][2] * vector.z
        return result
    }

    fun transform(left: Matrix4, right: Vector4f): Vector4f {
        val x = left[0][0] * right.x + left[0][1] * right.y + left[0][2] * right.z + left[0][3] * right.w
        val y = left[1][0] * right.x + left[1][1] * right.y + left[1][2] * right.z + left[1][3] * right.w
        val z = left[2][0] * right.x + left[2][1] * right.y + left[2][2] * right.z + left[2][3] * right.w
        val w = left[3][0] * right.x + left[3][1] * right.y + left[3][2] * right.z + left[3][3] * right.w

        return Vector4f(x, y, z, w)
    }

    /**
     * Rotates the matrix around a given axis by the angle supplied
     *
     * @param angle angle of the rotation in radians
     * @param axis the axis to perform the rotation on
     * @return matrix that is the result of rotating the instance matrix function was invoked on
     */
    fun rotate(matrix: Matrix4, angle: Float, axis: Matrix.Axis): Matrix4 {
        val result = Matrix4.copy(matrix)

        val rot00 = axis.normalX * axis.normalX * (1 - cos(angle)) + cos(angle)
        val rot01 = axis.normalX * axis.normalY * (1 - cos(angle)) + axis.normalZ * sin(angle)
        val rot02 = axis.normalX * axis.normalZ * (1 - cos(angle)) - axis.normalY * sin(angle)
        val rot10 = axis.normalX * axis.normalY * (1 - cos(angle)) - axis.normalZ * sin(angle)
        val rot11 = axis.normalY * axis.normalY * (1 - cos(angle)) + cos(angle)
        val rot12 = axis.normalY * axis.normalZ * (1 - cos(angle)) + axis.normalX * sin(angle)
        val rot20 = axis.normalX * axis.normalZ * (1 - cos(angle)) + axis.normalY * sin(angle)
        val rot21 = axis.normalY * axis.normalZ * (1 - cos(angle)) - axis.normalX * sin(angle)
        val rot22 = axis.normalZ * axis.normalZ * (1 - cos(angle)) + cos(angle)

        val rotMatrix = Matrix3(arrayOf(
            arrayOf(rot00, rot01, rot02),
            arrayOf(rot10, rot11, rot12),
            arrayOf(rot20, rot21, rot22)
        ))

        result[0][0] = matrix[0][0] * rotMatrix[0][0] + matrix[0][1] * rotMatrix[0][1] + matrix[0][2] * rotMatrix[0][2]
        result[0][1] = matrix[0][0] * rotMatrix[1][0] + matrix[0][1] * rotMatrix[1][1] + matrix[0][2] * rotMatrix[1][2]
        result[0][2] = matrix[0][0] * rotMatrix[2][0] + matrix[0][1] * rotMatrix[2][1] + matrix[0][2] * rotMatrix[2][2]

        result[1][0] = matrix[1][0] * rotMatrix[0][0] + matrix[1][1] * rotMatrix[0][1] + matrix[1][2] * rotMatrix[0][2]
        result[1][1] = matrix[1][0] * rotMatrix[1][0] + matrix[1][1] * rotMatrix[1][1] + matrix[1][2] * rotMatrix[1][2]
        result[1][2] = matrix[1][0] * rotMatrix[2][0] + matrix[1][1] * rotMatrix[2][1] + matrix[1][2] * rotMatrix[2][2]

        result[2][0] = matrix[2][0] * rotMatrix[0][0] + matrix[2][1] * rotMatrix[0][1] + matrix[2][2] * rotMatrix[0][2]
        result[2][1] = matrix[2][0] * rotMatrix[1][0] + matrix[2][1] * rotMatrix[1][1] + matrix[2][2] * rotMatrix[1][2]
        result[2][2] = matrix[2][0] * rotMatrix[2][0] + matrix[2][1] * rotMatrix[2][1] + matrix[2][2] * rotMatrix[2][2]

        result[3][0] = matrix[3][0] * rotMatrix[0][0] + matrix[3][1] * rotMatrix[0][1] + matrix[3][2] * rotMatrix[0][2]
        result[3][1] = matrix[3][0] * rotMatrix[1][0] + matrix[3][1] * rotMatrix[1][1] + matrix[3][2] * rotMatrix[1][2]
        result[3][2] = matrix[3][0] * rotMatrix[2][0] + matrix[3][1] * rotMatrix[2][1] + matrix[3][2] * rotMatrix[2][2]

        return result
    }

    fun matrixOf(position: Vector3f, rotation: Vector3f): Matrix4 {
        var matrix = Matrix4.identityMatrix()
        matrix = translate(matrix, position)
        matrix = rotate(matrix, rotation.z, Matrix.Axis.Z_AXIS)
        matrix = rotate(matrix, rotation.y, Matrix.Axis.Y_AXIS)
        matrix = rotate(matrix, rotation.x, Matrix.Axis.X_AXIS)
        return matrix
    }

    fun inverse(matrix: Matrix4): Matrix4? {
        if (determinant(matrix) == 0f) return null

        val inverseDeterminant = 1f / determinant(matrix)
        val result = Matrix4()

        result[0][0] = Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[1][1], matrix[1][2], matrix[1][3], matrix[2][1], matrix[2][2], matrix[2][3], matrix[3][1], matrix[3][2], matrix[3][3]
        ))) * inverseDeterminant
        result[0][1] = -1 * Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[1][0], matrix[1][2], matrix[1][3], matrix[2][0], matrix[2][2], matrix[2][3], matrix[3][0], matrix[3][2], matrix[3][3]
        ))) * inverseDeterminant
        result[0][2] = Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[1][0], matrix[1][1], matrix[1][3], matrix[2][0], matrix[2][1], matrix[2][3], matrix[3][0], matrix[3][1], matrix[3][3]
        ))) * inverseDeterminant
        result[0][3] = -1 * Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[1][0], matrix[1][1], matrix[1][2], matrix[2][0], matrix[2][1], matrix[2][2], matrix[3][0], matrix[3][1], matrix[3][2]
        ))) * inverseDeterminant
        result[1][0] = -1 * Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][1], matrix[0][2], matrix[0][3], matrix[2][1], matrix[2][2], matrix[2][3], matrix[3][1], matrix[3][2], matrix[3][3]
        ))) * inverseDeterminant
        result[1][1] = Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][0], matrix[0][2], matrix[0][3], matrix[2][0], matrix[2][2], matrix[2][3], matrix[3][0], matrix[3][2], matrix[3][3]
        ))) * inverseDeterminant
        result[1][2] = -1 * Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][0], matrix[0][1], matrix[0][3], matrix[2][0], matrix[2][1], matrix[2][3], matrix[3][0], matrix[3][1], matrix[3][3]
        ))) * inverseDeterminant
        result[1][3] = Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][0], matrix[0][1], matrix[0][2], matrix[2][0], matrix[2][1], matrix[2][2], matrix[3][0], matrix[3][1], matrix[3][2]
        ))) * inverseDeterminant
        result[2][0] = Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][1], matrix[0][2], matrix[0][3], matrix[1][1], matrix[1][2], matrix[1][3], matrix[3][1], matrix[3][2], matrix[3][3]
        ))) * inverseDeterminant
        result[2][1] = -1 * Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][0], matrix[0][2], matrix[0][3], matrix[1][0], matrix[1][2], matrix[1][3], matrix[3][0], matrix[3][2], matrix[3][3]
        ))) * inverseDeterminant
        result[2][2] = Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][0], matrix[0][1], matrix[0][3], matrix[1][0], matrix[1][1], matrix[1][3], matrix[3][0], matrix[3][1], matrix[3][3]
        ))) * inverseDeterminant
        result[2][3] = -1 * Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][0], matrix[0][1], matrix[0][2], matrix[1][0], matrix[1][1], matrix[1][2], matrix[3][0], matrix[3][1], matrix[3][2]
        ))) * inverseDeterminant
        result[3][0] = -1 * Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][1], matrix[0][2], matrix[0][3], matrix[1][1], matrix[1][2], matrix[1][3], matrix[2][1], matrix[2][2], matrix[2][3]
        ))) * inverseDeterminant
        result[3][1] = Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][0], matrix[0][2], matrix[0][3], matrix[1][0], matrix[1][2], matrix[1][3], matrix[2][0], matrix[2][2], matrix[2][3]
        ))) * inverseDeterminant
        result[3][2] = -1 * Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][0], matrix[0][1], matrix[0][3], matrix[1][0], matrix[1][1], matrix[1][3], matrix[2][0], matrix[2][1], matrix[2][3]
        ))) * inverseDeterminant
        result[3][3] = Matrix3Helper.determinant(Matrix3(arrayOf(
            matrix[0][0], matrix[0][1], matrix[0][2], matrix[1][0], matrix[1][1], matrix[1][2], matrix[2][0], matrix[2][1], matrix[2][2]
        ))) * inverseDeterminant

        return result
    }

    fun determinant(matrix: Matrix4): Float {
        var determinant: Float = (matrix[0][0]
                * (matrix[1][1] * matrix[2][2] * matrix[3][3] + matrix[1][2] * matrix[2][3] * matrix[3][1] + matrix[1][3] * matrix[2][1] * matrix[3][2]
                - matrix[1][3] * matrix[2][2] * matrix[3][1] - matrix[1][1] * matrix[2][3] * matrix[3][2] - matrix[1][2] * matrix[2][1] * matrix[3][3]))
        determinant -= (matrix[0][1]
                * (matrix[1][0] * matrix[2][2] * matrix[3][3] + matrix[1][2] * matrix[2][3] * matrix[3][0] + matrix[1][3] * matrix[2][0] * matrix[3][2]
                - matrix[1][3] * matrix[2][2] * matrix[3][0] - matrix[1][0] * matrix[2][3] * matrix[3][2] - matrix[1][2] * matrix[2][0] * matrix[3][3]))
        determinant += (matrix[0][2]
                * (matrix[1][0] * matrix[2][1] * matrix[3][3] + matrix[1][1] * matrix[2][3] * matrix[3][0] + matrix[1][3] * matrix[2][0] * matrix[3][1]
                - matrix[1][3] * matrix[2][1] * matrix[3][0] - matrix[1][0] * matrix[2][3] * matrix[3][1] - matrix[1][1] * matrix[2][0] * matrix[3][3]))
        determinant -= (matrix[0][3]
                * (matrix[1][0] * matrix[2][1] * matrix[3][2] + matrix[1][1] * matrix[2][2] * matrix[3][0] + matrix[1][2] * matrix[2][0] * matrix[3][1]
                - matrix[1][2] * matrix[2][1] * matrix[3][0] - matrix[1][0] * matrix[2][2] * matrix[3][1] - matrix[1][1] * matrix[2][0] * matrix[3][2]))
        return determinant
    }

}