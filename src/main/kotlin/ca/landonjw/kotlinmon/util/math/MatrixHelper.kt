package ca.landonjw.kotlinmon.util.math

object MatrixHelper {

    fun multiply(left: Matrix, right: Matrix): Matrix {
        if (left.columns != right.rows) throw IllegalArgumentException("matrices cannot be multiplied")
        val values = Array(right.rows) {
            Array(left.columns) { 0f }
        }
        for (row in 0 until left.rows) {
            for (col in 0 until right.columns) {
                values[row][col] = getMultiplicationResult(row, col, left, right)
            }
        }
        return Matrix(right.rows, left.columns, values)
    }

    private fun getMultiplicationResult(row: Int, col: Int, left: Matrix, right: Matrix): Float {
        var result = 0f
        for (leftCol in 0 until left.columns) {
            for (rightRow in 0 until right.rows) {
                result += left[row][leftCol] * right[rightRow][col]
            }
        }
        return result
    }

}