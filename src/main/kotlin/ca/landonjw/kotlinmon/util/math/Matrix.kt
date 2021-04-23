package ca.landonjw.kotlinmon.util.math

import net.minecraft.util.math.vector.Vector3f
import kotlin.math.cos
import kotlin.math.sin

open class Matrix(val rows: Int, val columns: Int, values: Array<Array<Float>>? = null) {

    val values: Array<Array<Float>>

    init {
        if (values != null) {
            if (values.size != rows) throw IllegalArgumentException("supplied values do not conform to matrix row size")
            require(!values.any { it.size != columns }) { "supplied values do not conform to matrix column size"}
            this.values = values
        } else {
            this.values = Array(rows) {
                Array(columns) { 0f }
            }
        }
    }

    companion object {

        fun identityMatrix(size: Int): Matrix {
            val list = mutableListOf<List<Float>>()
            for (row in 0 until size) {
                val rowList = mutableListOf<Float>()
                for (col in 0 until size) {
                    rowList.add(if (row == col) 1f else 0f)
                }
                list.add(rowList)
            }
            val arrList = mutableListOf<Array<Float>>()
            for (rowList in list) {
                arrList.add(rowList.toTypedArray())
            }
            return Matrix(size, size, arrList.toTypedArray())
        }

    }

    operator fun get(row: Int) = values[row]

    enum class Axis(private val axis: String, private val vector: Vector3f) {
        X_AXIS("X", Vector3f(1f, 0f, 0f)),
        Y_AXIS("Y", Vector3f(0f, 1f, 0f)),
        Z_AXIS("Z", Vector3f(0f, 0f, 1f));

        val normalX: Float
            get() = vector.x
        val normalY: Float
            get() = vector.y
        val normalZ: Float
            get() = vector.z

        fun getRotationMatrix(angle: Float): Matrix = when (this) {
            X_AXIS -> Matrix(3, 3, arrayOf(
                arrayOf(1f, 0f, 0f),
                arrayOf(0f, cos(angle), -1 * sin(angle)),
                arrayOf(0f, sin(angle), cos(angle))
            ))
            Y_AXIS -> Matrix(3, 3, arrayOf(
                arrayOf(cos(angle), 0f, sin(angle)),
                arrayOf(0f, 1f, 0f),
                arrayOf(-1 * sin(angle), 0f, cos(angle))
            ))
            Z_AXIS -> Matrix(3, 3, arrayOf(
                arrayOf(cos(angle), -1 * sin(angle), 0f),
                arrayOf(sin(angle), cos(angle), 0f),
                arrayOf(0f, 0f, 1f)
            ))
        }
    }

}