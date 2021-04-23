package ca.landonjw.kotlinmon.util.math

class Matrix4(values: Array<Array<Float>>? = null) : Matrix(4, 4, values) {

    fun foo(): Array<Array<Float>> {
        return arrayOf()
    }

    companion object {
        fun identityMatrix(): Matrix4 {
            return Matrix4(identityMatrix(4).values)
        }

        fun copy(matrix: Matrix): Matrix4 {
            if (matrix.rows != 4 || matrix.columns != 4) throw IllegalArgumentException("matrix dimensions must be 4x4")
            val values = Array(4) {
                Array(4) { 0f }
            }
            for (row in 0 until 4) {
                for (col in 0 until 4) {
                    values[row][col] = matrix[row][col]
                }
            }
            return Matrix4(values)
        }
    }

}