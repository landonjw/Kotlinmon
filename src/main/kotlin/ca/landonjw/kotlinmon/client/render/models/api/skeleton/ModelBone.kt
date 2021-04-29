package ca.landonjw.kotlinmon.client.render.models.api.skeleton

import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix

/**
 * A bone in a [ModelSkeleton].
 * Transformations on a bone will have a direct impact on the visual representation of the model.
 *
 * @property children any bones that are children of this bone.
 *                    for example, a head bone may be a child of a neck bone
 * @property parent the bone that is a parent of the bone instance.
 *                  for example, a neck bone may be a parent of a head bone.
 *                  a bone may not have a parent.
 *
 * @author landonjw
 */
interface ModelBone {
    val children: List<ModelBone>
    val parent: ModelBone?

    /**
     * Moves the bone by a given transformation.
     *
     * @param transformation the transformation to apply to the bone
     */
    fun transform(transformation: TransformationMatrix)
}