package ca.landonjw.kotlinmon.util.math.geometry

/**
 * Represents a line that is perpendicular to a given line.
 * For more information, see [Normal (Geometry)](https://en.wikipedia.org/wiki/Normal_%28geometry%29)
 *
 * Often used in conjunction with [TransformationMatrix] for three dimensional transformations.
 *
 * @author landonjw
 */
data class GeometricNormal(val x: Float, val y: Float, val z: Float) {

    val w: Float = 0f // This is always 0 to prevent translations on transformation matrices

    operator fun plus(right: GeometricNormal): GeometricNormal = add(this, right)
    operator fun times(scalar: Float): GeometricNormal = multiply(this, scalar)

    constructor() : this(0f, 0f, 0f)

    companion object {

        /**
         * Creates a new geometric normal representing two normals added together.
         * Addition is commutative and therefore order does not matter.
         *
         * @param left the normal to add
         * @param right the normal to add
         * @return point representing sum of two other points
         */
        fun add(left: GeometricNormal, right: GeometricNormal): GeometricNormal {
            return GeometricNormal(left.x + right.x, left.y + right.y, left.z + right.z)
        }

        /**
         * Creates a new geometric normal representing a product of a normal multiplied by a scalar
         *
         * @param normal the normal to multiply
         * @return normal representing product of a normal and scalar
         */
        fun multiply(normal: GeometricNormal, scalar: Float): GeometricNormal {
            return GeometricNormal(normal.x * scalar, normal.y * scalar, normal.z * scalar)
        }

    }

}