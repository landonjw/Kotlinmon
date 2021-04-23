package ca.landonjw.kotlinmon.util.math

class Matrix3(values: Array<Array<Float>>? = null) : Matrix(3, 3, values) {

    constructor(values: Array<Float>): this(arrayOf<Array<Float>>(
        values.sliceArray<Float>(0 until 3),
        values.sliceArray<Float>(3 until 6),
        values.sliceArray<Float>(6 until 9)
    ))

    companion object {
        fun identityMatrix(): Matrix3 {
            return Matrix3(identityMatrix(3).values)
        }

        fun copy(matrix: Matrix): Matrix3 {
            if (matrix.rows != 3 || matrix.columns != 3) throw IllegalArgumentException("matrix dimensions must be 3x3")
            val values = Array(3) {
                Array(3) { 0f }
            }
            for (row in 0 until 3) {
                for (col in 0 until 3) {
                    values[row][col] = matrix[row][col]
                }
            }
            return Matrix3(values)
        }
    }

}