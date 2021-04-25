package ca.landonjw.kotlinmon.util.math.geometry

/**
 * A builder that assists in creating linear transformations.
 *
 * This ensures an order of operations for transformations as follows:
 *  * Translation
 *  * Rotation
 *      * X Rotation
 *      * Y Rotation
 *      * Z Rotation
 *  * Scaling
 *
 * It is highly recommended to use this class when linear transformations are required.
 *
 * @author landonjw
 */
class TransformationBuilder {

    private val translationTransformations: MutableList<TransformationMatrix> = mutableListOf()
    private val rotationTransformations: Map<Axis, MutableList<TransformationMatrix>> = mutableMapOf(
        Axis.X_AXIS to mutableListOf(),
        Axis.Y_AXIS to mutableListOf(),
        Axis.Z_AXIS to mutableListOf()
    )
    private val scalingTransformations: MutableList<TransformationMatrix> = mutableListOf()

    /**
     * Adds a translation to the builder.
     *
     * @param point the x, y, and z values to translate by
     * @return builder with translation added
     */
    fun translate(point: GeometricPoint): TransformationBuilder {
        val transformation = TransformationMatrix.translate(point)
        translationTransformations.add(transformation)
        return this
    }

    /**
     * Adds a rotation to the builder.
     *
     * @param angle the angle in radians to rotate by.
     *              positive values for clockwise, negative values for counter-clockwise
     * @param axis the axis of rotation
     * @return builder with rotation added
     */
    fun rotate(angle: Float, axis: Axis): TransformationBuilder {
        val transformation = TransformationMatrix.rotate(angle, axis)
        rotationTransformations[axis]!!.add(transformation)
        return this
    }

    /**
     * Adds a scaling transformation to the builder.
     *
     * @param scalarX the scalar for the x axis
     * @param scalarY the scalar for the y axis
     * @param scalarZ the scalar for the z axis
     * @return builder with scale transformation added
     */
    fun scale(scalarX: Float, scalarY: Float, scalarZ: Float): TransformationBuilder {
        val transformation = TransformationMatrix.scale(scalarX, scalarY, scalarZ)
        scalingTransformations.add(transformation)
        return this
    }

    /**
     * Adds a scaling transformation to the builder.
     *
     * @param scalar the scalar for all axes
     * @return builder with scale transformation added
     */
    fun scale(scalar: Float): TransformationBuilder = this.scale(scalar, scalar, scalar)

    /**
     * Builds the final transformation matrix from all previously added transformations.
     *
     * @return the finished transformation matrix
     */
    fun build(): TransformationMatrix {
        val translationMatrix = getMatrixProduct(translationTransformations)
        val xRotationMatrix = getMatrixProduct(rotationTransformations[Axis.X_AXIS]!!)
        val yRotationMatrix = getMatrixProduct(rotationTransformations[Axis.Y_AXIS]!!)
        val zRotationMatrix = getMatrixProduct(rotationTransformations[Axis.Z_AXIS]!!)
        val scalingMatrix = getMatrixProduct(scalingTransformations)

        var result = TransformationMatrix.identityMatrix
        result = TransformationMatrix.combine(result, translationMatrix)
        result = TransformationMatrix.combine(result, xRotationMatrix)
        result = TransformationMatrix.combine(result, yRotationMatrix)
        result = TransformationMatrix.combine(result, zRotationMatrix)
        result = TransformationMatrix.combine(result, scalingMatrix)

        return result
    }

    private fun getMatrixProduct(matrices: List<TransformationMatrix>): TransformationMatrix {
        var product = TransformationMatrix.identityMatrix
        for (matrix in matrices) {
            product = TransformationMatrix.combine(product, matrix)
        }
        return product
    }

    /**
     * Resets the builder to it's initial state.
     */
    fun reset(): TransformationBuilder {
        translationTransformations.clear()
        rotationTransformations[Axis.X_AXIS]!!.clear()
        rotationTransformations[Axis.Y_AXIS]!!.clear()
        rotationTransformations[Axis.Z_AXIS]!!.clear()
        scalingTransformations.clear()
        return this
    }

}
