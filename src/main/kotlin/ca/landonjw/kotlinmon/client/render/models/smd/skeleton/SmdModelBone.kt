package ca.landonjw.kotlinmon.client.render.models.smd.skeleton

import ca.landonjw.kotlinmon.client.render.models.smd.mesh.SmdMeshVertex
import ca.landonjw.kotlinmon.common.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.common.util.math.geometry.TransformationMatrix
import net.minecraft.util.math.vector.Vector3f

/**
 * A bone in a [SmdModelSkeleton].
 * Making transformations to a bone will have a direct influence on all vertices that have a relationship with it.
 *
 * @property id the unique id the bone
 *              this value is only unique to the skeleton the bone belongs to
 * @property name the name of the bone
 * @property parent the parent of the bone, if it has one.
 *                  an example would be an arm bone being the parent of a hand bone
 * @property children all children of the bone.
 *                    an example would be finger bones being children of a hand bone
 *
 * @param basePosition the base position of the bone at rest, when the model is t-posed
 * @param baseOrientation the base orientation of the bone at rest, when the model is t-posed
 *
 * @author landonjw
 */
class SmdModelBone(
    val id: Int,
    val name: String,
    basePosition: GeometricPoint,
    baseOrientation: Vector3f
) {

    private val vertices: MutableList<SmdMeshVertex> = mutableListOf()

    internal var _children: MutableList<SmdModelBone> = mutableListOf()
    val children: List<SmdModelBone>
        get() = _children.toList()

    var parent: SmdModelBone? = null
        internal set

    private var baseTransformation: TransformationMatrix = TransformationMatrix.of(basePosition, baseOrientation)
    private lateinit var invertedBaseTransformation: TransformationMatrix

    /**
     * Moves the bone by a given transformation.
     * This will reflect the transformation to each vertex bound to the bone.
     *
     * @param transformation the transformation to apply
     */
    fun transform(transformation: TransformationMatrix) {
        // Calculates the different between the base transformation and supplied transformation
        val delta = transformation * invertedBaseTransformation
        // Applies the change to each vertex the bone has influence over
        vertices.forEach { it.transform(this, delta) }
    }

    internal fun invertBaseTransformation() {
        invertedBaseTransformation = TransformationMatrix.invert(baseTransformation)!!
    }

    /**
     * Propagates the base transformations of bones throughout the entire skeleton recursively.
     * This should always begin propagation from the root bone of the skeleton.
     *
     * @param transformation the transformation to apply
     */
    internal fun adjust(transformation: TransformationMatrix? = null) {
        if (transformation != null) baseTransformation = transformation * baseTransformation
        _children.forEach { it.adjust(baseTransformation) }
    }

    internal fun addVertex(vertex: SmdMeshVertex) {
        vertices.add(vertex)
    }

}