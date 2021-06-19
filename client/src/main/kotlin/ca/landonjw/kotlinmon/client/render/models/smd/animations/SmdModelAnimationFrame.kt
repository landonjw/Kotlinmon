package ca.landonjw.kotlinmon.client.render.models.smd.animations

import ca.landonjw.kotlinmon.client.render.models.smd.loaders.files.schemas.SmdBoneTransformationSchema
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix

/**
 * A single frame within a [SmdModelAnimation]
 * Defines a list of movements for each bone, at an instant in time.
 *
 * @property skeleton the skeleton of the model to apply animation frame to
 * @property boneTransformations the transformations to apply to each bone
 *                               key is bone id, value is the transformation matrix
 * @param transformations the transformations within the frame
 *
 * @author landonjw
 */
class SmdModelAnimationFrame(
    transformations: List<SmdBoneTransformationSchema>,
    private val skeleton: SmdModelSkeleton
) {

    val boneTransformations: Map<Int, TransformationMatrix>

    init {
        // Create map to prevent too much iteration
        val boneIdToMovement = transformations.map { it.boneId to it }.toMap()

        val boneTransformations = mutableMapOf<Int, TransformationMatrix>()
        transformations.forEach { boneTransformations[it.boneId] = it.transformation }

        // Iterate through each bone, and apply parent transformation to propagate skeleton movements
        for (index in 0 until boneTransformations.size) {
            val bone = skeleton.boneById[index] ?: continue
            val parent = bone.parent ?: continue
            val boneTransformation = boneTransformations[index] ?: continue
            val parentTransformation = boneTransformations[parent.id] ?: continue
            boneTransformations[index] = parentTransformation * boneTransformation
        }
        this.boneTransformations = boneTransformations
    }

    /** Applies the frame's transformations to the skeleton of model. */
    fun apply() {
        skeleton.mesh.reset()
        for (bone in skeleton.bones) {
            val transformation = boneTransformations[bone.id] ?: continue
            bone.transform(transformation)
        }
    }

}