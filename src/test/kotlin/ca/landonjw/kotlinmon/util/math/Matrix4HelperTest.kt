package ca.landonjw.kotlinmon.util.math

import net.minecraft.util.math.vector.Vector3f
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Matrix4HelperTest {

//    @Test
//    fun `rotate returns same result as lwjgl`() {
//        val matrix = Matrix4.identityMatrix()
//
//        val expected = Matrix4(arrayOf(
//            arrayOf(-0.41614684f, 0.0f, 0.9092974f, 0.0f),
//            arrayOf(0.0f, 1.0f, 0.0f, 0.0f),
//            arrayOf(-0.9092974f, 0.0f, -0.41614684f, 0.0f),
//            arrayOf(0.0f, 0.0f, 0.0f, 1.0f)
//        ))
//
//        val actual = Matrix4Helper.rotate(matrix, 2f, Matrix.Axis.Y_AXIS)
//
//        for (row in 0 until actual!!.rows) {
//            for (col in 0 until actual!!.columns) {
//                assertEquals(expected[row][col], actual[row][col])
//            }
//        }
//    }
//
//    @Test
//    fun `translate returns same result as lwjgl`() {
//        val position = Vector3f(3f, 2f, 1f)
//        val matrix = Matrix4.identityMatrix()
//
//        val expected = Matrix4(arrayOf(
//            arrayOf(1.0f, 0.0f, 0.0f, 3.0f),
//            arrayOf(0.0f, 1.0f, 0.0f, 2.0f),
//            arrayOf(0.0f, 0.0f, 1.0f, 1.0f),
//            arrayOf(0.0f, 0.0f, 0.0f, 1.0f)
//        ))
//        val actual = Matrix4Helper.translate(matrix, position)
//
//        for (row in 0 until actual!!.rows) {
//            for (col in 0 until actual!!.columns) {
//                assertEquals(expected[row][col], actual[row][col])
//            }
//        }
//    }
//
//    @Test
//    fun `matrix from position and rotation returns same result as lwjgl`() {
//        val position = Vector3f(3f, 2f, 1f)
//        val rotation = Vector3f(1f, 2f, 3f)
//
//        val expected = Matrix4(arrayOf(
//            arrayOf(0.41198227f, -0.8337376f, -0.36763042f, 3.0f),
//            arrayOf(-0.058726642f, -0.42691758f, 0.90238154f, 2.0f),
//            arrayOf(-0.9092974f, -0.35017547f, -0.22484508f, 1.0f),
//            arrayOf(0.0f, 0.0f, 0.0f, 1.0f)
//        ))
//        val actual = Matrix4Helper.matrixOf(location = position, rotation = rotation)
//
//        for (row in 0 until actual!!.rows) {
//            for (col in 0 until actual!!.columns) {
//                assertEquals(expected[row][col], actual[row][col])
//            }
//        }
//    }
//
//    @Test
//    fun `matrix invert returns same result as lwjgl`() {
//        val matrix = Matrix4(arrayOf(
//            arrayOf(1f, 1f, 2f, 3f),
//            arrayOf(1f, 1f, 4f, 3f),
//            arrayOf(2f, 2f, 2f, 2f),
//            arrayOf(6f, 5f, 4f, 3f)
//        ))
//
//        val expected = Matrix4(arrayOf(
//            arrayOf(1.0f, -1.25f, -0.5f, 0.75f),
//            arrayOf(-0.0f, -0.25f, 0.5f, -0.25f),
//            arrayOf(-3.0f, 3.75f, 0.0f, -0.25f),
//            arrayOf(1.0f, -1.0f, -0.0f, 0.0f)
//        ))
//
//        val actual = Matrix4Helper.inverse(matrix)
//
//        for (row in 0 until actual!!.rows) {
//            for (col in 0 until actual!!.columns) {
//                assertEquals(expected[row][col], actual[row][col])
//            }
//        }
//    }
//
//    @Test
//    fun `matrix determinant returns same result as lwjgl`() {
//        val matrix = Matrix4(arrayOf(
//            arrayOf(1f, 1f, 2f, 3f),
//            arrayOf(1f, 1f, 4f, 3f),
//            arrayOf(2f, 2f, 2f, 2f),
//            arrayOf(6f, 5f, 4f, 3f)
//        ))
//
//        val expected = 8.0f
//
//        val actual = Matrix4Helper.determinant(matrix)
//
//        assertEquals(expected, actual)
//    }

}