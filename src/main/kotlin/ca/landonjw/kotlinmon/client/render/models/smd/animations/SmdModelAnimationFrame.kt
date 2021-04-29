package ca.landonjw.kotlinmon.client.render.models.smd.animations

import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdBoneMovement
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix

class SmdModelAnimationFrame(
    movements: List<SmdBoneMovement>,
    private val skeleton: SmdModelSkeleton
) {

    val boneTransformations: Map<Int, TransformationMatrix>

    init {
        // Create map to prevent too much iteration
        val boneIdToMovement = movements.map { it.boneId to it }.toMap()

        val boneTransformations = mutableMapOf<Int, TransformationMatrix>()
        movements.forEach { boneTransformations[it.boneId] = it.transformation }

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

    fun apply() {
        skeleton.mesh.reset()
        for (bone in skeleton.bones) {
            val transformation = boneTransformations[bone.id] ?: continue
            bone.transform(transformation)
        }
    }

}