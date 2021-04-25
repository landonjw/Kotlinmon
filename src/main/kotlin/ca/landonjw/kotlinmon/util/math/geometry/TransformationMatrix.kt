package ca.landonjw.kotlinmon.util.math.geometry

import ca.landonjw.kotlinmon.util.collections.ImmutableArray
import ca.landonjw.kotlinmon.util.collections.immutableArrayOf

/**
 * A 4x4 matrix that is used to represent transformations in three dimensional space.
 * For more information about the operations provided here, see
 * [Spatial Transformation Matrices](https://www.brainvoyager.com/bv/doc/UsersGuide/CoordsAndTransforms/SpatialTransformationMatrices.html)
 *
 * @property values the values of the matrix, in the form of a two-dimensional immutable array
 * @author landonjw
 */
data class TransformationMatrix internal constructor(val values: ImmutableArray<ImmutableArray<Float>>) {

    operator fun get(row: Int) = values[row] // Allows for accessing the matrix as if it was a two-dimensional array

    companion object {

        /**
         * Gets the [identity matrix](https://en.wikipedia.org/wiki/Identity_matrix) for a matrix of size 4x4.
         *
         * @return a new instance of an identity matrix
         */
        val identityMatrix: TransformationMatrix = TransformationMatrix(identityArray().toImmutable())

        private fun identityArray() = arrayOf(
            arrayOf(1f, 0f, 0f, 0f),
            arrayOf(0f, 1f, 0f, 0f),
            arrayOf(0f, 0f, 1f, 0f),
            arrayOf(0f, 0f, 0f, 1f)
        )

        /**
         * Gets the transformation matrix for a given set of translation values.
         * If a target transformation matrix is specified, the transformation matrices are concatenated.
         *
         * @param point the values to translate by
         * @param matrix an optional matrix to concatenate to
         *
         * @return a new transformation matrix for a given translation
         */
        fun translate(point: GeometricPoint, matrix: TransformationMatrix? = null): TransformationMatrix {
            val values = identityArray()
            values[0][3] = point.x
            values[1][3] = point.y
            values[2][3] = point.z
            val transformation = TransformationMatrix(values.toImmutable())
            return if (matrix == null) transformation else combine(matrix, transformation)
        }

        /**
         * Gets the rotation matrix for a given axis and angle.
         * If a target transformation matrix is specified, the transformation matrices are concatenated.
         *
         * If rotating by multiple axes, this should be called several times for each axis.
         * When doing so, keep in mind multiplication is non-commutative and order does matter.
         *
         * @param angle the angle to rotate by
         * @param axis  the axis to rotate on
         * @param matrix an optional matrix to concatenate to
         *
         * @return a new transformation matrix for a given rotation
         */
        fun rotate(angle: Float, axis: Axis, matrix: TransformationMatrix ? = null): TransformationMatrix {
            val transformation = axis.getRotationMatrix(angle)
            return if (matrix == null) transformation else combine(matrix, transformation)
        }

        /**
         * Gets the transformation matrix for a given scalar.
         * If a target transformation matrix is specified, the transformation matrices are concatenated.
         *
         * @param scalar the scalar to get matrix for
         * @param matrix an optional matrix to concatenate to
         *
         * @return a new transformation matrix for a given scalar
         */
        fun scale(scalar: Float, matrix: TransformationMatrix? = null): TransformationMatrix {
            val values = identityArray()
            values[0][0] = scalar
            values[1][1] = scalar
            values[2][2] = scalar
            val transformation = TransformationMatrix(values.toImmutable())
            return if (matrix == null) transformation else combine(matrix, transformation)
        }

        /**
         * Gets the transformation matrix for a set of scalars.
         * These scalars allow you to stretch the dimensions of different dimensions.
         * If a target transformation matrix is specified, the transformation matrices are concatenated.
         *
         * @param scalarX the scalar for the x axis
         * @param scalarY the scalar for the y axis
         * @param scalarZ the scalar for the z axis
         * @param matrix an optional matrix to concatenate to
         *
         * @return a new transformation matrix for a given scalars
         */
        fun scale(scalarX: Float, scalarY: Float, scalarZ: Float, matrix: TransformationMatrix? = null): TransformationMatrix {
            val values = identityArray()
            values[0][0] = scalarX
            values[1][1] = scalarY
            values[2][2] = scalarZ
            val transformation = TransformationMatrix(values.toImmutable())
            return if (matrix == null) transformation else combine(matrix, transformation)
        }

        /**
         * Transforms a three dimensional point to get the new position, based off of the given transformation
         * matrix.
         *
         * @param matrix the transformation matrix to use to transform the point
         * @param point the point to transform
         *
         * @return a new three dimensional point that has been transformed by the given matrix
         */
        fun transform(matrix: TransformationMatrix, point: GeometricPoint): GeometricPoint {
            val x = matrix[0][0] * point.x + matrix[0][1] * point.y + matrix[0][2] * point.z + matrix[0][3]
            val y = matrix[1][0] * point.x + matrix[1][1] * point.y + matrix[1][2] * point.z + matrix[1][3]
            val z = matrix[2][0] * point.x + matrix[2][1] * point.y + matrix[2][2] * point.z + matrix[2][3]
            return GeometricPoint(x, y, z)
        }

        /**
         * Combines, or concatenates, two transformation matrices.
         *
         * This operation is completed by matrix multiplication between the two matrices.
         * When using this, keep in mind it is non-commutative and order does matter.
         *
         * @param left the left side of the combination
         * @param right the right side of the combination
         *
         * @return a new transformation matrix representing the combination of the original matrices
         */
        fun combine(left: TransformationMatrix, right: TransformationMatrix): TransformationMatrix {
            val values = Array(4) {
                Array(4) { 0f }
            }

            for (row in 0 until 4) {
                for (col in 0 until 4) {
                    values[row][col] = multiplyRowCol(row, col, left, right)
                }
            }
            return TransformationMatrix(values.toImmutable())
        }

        private fun multiplyRowCol(row: Int, col: Int, left: TransformationMatrix, right: TransformationMatrix): Float {
            var sum = 0f
            for (i in 0 until 4) sum += left[row][i] * right[i][col]
            return sum
        }

        /**
         * Returns an immutable two-dimensional array.
         *
         * This assumes that the array that is being turned immutable is of 4x4.
         *
         * @return an immutable version of the two-dimensional array
         */
        private fun Array<Array<Float>>.toImmutable(): ImmutableArray<ImmutableArray<Float>> {
            return immutableArrayOf(
                immutableArrayOf(*this[0]),
                immutableArrayOf(*this[1]),
                immutableArrayOf(*this[2]),
                immutableArrayOf(*this[3])
            )
        }

    }

}