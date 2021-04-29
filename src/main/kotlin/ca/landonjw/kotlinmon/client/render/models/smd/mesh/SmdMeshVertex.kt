package ca.landonjw.kotlinmon.client.render.models.smd.mesh

import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelBone
import ca.landonjw.kotlinmon.util.math.geometry.GeometricNormal
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix

/**
 * A single vertex in a [SmdMesh].
 *
 * @property weights a series of relationships with bones and their weights on the vertex
 *                   key is the bone id, value is the weight. this impacts how much a bone's
 *                   transformation affects the position and normal of the vertex.
 *                   weights should always add up to 1.
 * @property basePosition the position of the vertex at rest, when the model is t-posed
 * @property baseNormal the normal of the vertex at rest, when the model is t-posed
 * @property u value representing the u-coordinate of a texture to map to the vertex
 * @property v value representing the v-coordinate of a texture to map to the vertex
 * @property position the position of the vertex
 *                    * may be different from basePosition if the vertex has been transformed
 *                    * may vary if the vertex has been transformed by an animation
 * @property normal the normal of the vertex
 *                    * may be different from baseNormal if the vertex has been transformed
 *                    * may vary if the vertex has been transformed by an animation
 *
 * @author landonjw
 */
class SmdMeshVertex(
    val weights: Map<Int, Float>,
    val basePosition: GeometricPoint,
    val baseNormal: GeometricNormal,
    val u: Float,
    val v: Float
) {

    val position: GeometricPoint
        get() = dirtyPosition ?: basePosition
    val normal: GeometricNormal
        get() = dirtyNormal ?: baseNormal

    /**
     * A mutated position of the vertex.
     *
     * If the vertex is mutated in a given animation frame, this should sum up
     * to the new location when all relevant bones have been transformed.
     * This will be null if there are no transformations to apply,
     * and the vertex is distinguished to be in it's base state.
     *
     * @see transform for how this is populated
     */
    private var dirtyPosition: GeometricPoint? = null

    /**
     * A mutated normal of the vertex.
     *
     * If the vertex is mutated in a given animation frame, this should sum up
     * to the new normal when all relevant bones have been transformed.
     * This will be null if there are no transformations to apply,
     * and the vertex is distinguished to be in it's base state.
     *
     * @see transform for how this is populated
     */
    private var dirtyNormal: GeometricNormal? = null

    /**
     * Transforms the vertex by a given transformation matrix.
     * The weight link relationship between the supplied bone and the vertex
     * will have a direct influence on how much the vertex is impacted.
     *
     * @param bone the bone performing the transformation
     * @param transformation the transformation occurring
     */
    fun transform(bone: SmdModelBone, transformation: TransformationMatrix) {
        val weight = weights[bone.id] ?: return

        if (dirtyPosition == null) dirtyPosition = GeometricPoint()
        if (dirtyNormal == null) dirtyNormal = GeometricNormal()

        /*
         * Transforms the base position & normal and applies the weight value.
         * This is then added to a 'dirty' value that is reset at the start of a new animation frame.
         * At the end of an animation frame, this should sum up to the new location * normal for the vertex.
         */
        val weightedLocation = transformation * basePosition * weight
        val weightedNormal = transformation * baseNormal * weight

        dirtyPosition = dirtyPosition!! + weightedLocation
        dirtyNormal = dirtyNormal!! + weightedNormal
    }

    /** Resets the vertex to it's base state. */
    fun reset() {
        dirtyPosition = null
        dirtyNormal = null
    }

}