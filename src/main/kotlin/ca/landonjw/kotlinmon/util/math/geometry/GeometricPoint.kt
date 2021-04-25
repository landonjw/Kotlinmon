package ca.landonjw.kotlinmon.util.math.geometry

/**
 * A three dimensional point in space.
 *
 * Often used in conjunction with [TransformationMatrix] for three dimensional transformations.
 *
 * @author landonjw
 */
data class GeometricPoint(val x: Float, val y: Float, val z: Float) {

    val w = 1

    companion object {

        /**
         * Creates a new geometric point representing two points added together.
         *
         * @param left the point to add
         * @param right the point to add
         * @return point representing sum of two other points
         */
        fun add(left: GeometricPoint, right: GeometricPoint): GeometricPoint {
            return GeometricPoint(left.x + right.x, left.y + right.y, left.z + right.z)
        }

        /**
         * Creates a new geometric point representing a product of a point multiplied by a scalar
         *
         * @param point the point to multiply
         * @return point representing product of a point and scalar
         */
        fun multiply(point: GeometricPoint, scalar: Float): GeometricPoint {
            return GeometricPoint(point.x * scalar, point.y * scalar, point.z * scalar)
        }

    }

}