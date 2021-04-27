package ca.landonjw.kotlinmon.client.render.models.smd.skeleton

import ca.landonjw.kotlinmon.client.render.models.api.skeleton.ModelSkeleton
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimation

class SmdModelSkeleton(
    override val bones: List<SmdModelBone>
) : ModelSkeleton {

    val boneById: Map<Int, SmdModelBone>

    init {
        val boneMap = mutableMapOf<Int, SmdModelBone>()
        bones.forEach { bone -> boneMap[bone.id] = bone }
        boneById = boneMap
    }

    operator fun get(boneId: Int) = boneById[boneId]

    fun resetPosture() {
        bones.forEach { it.reset() }
    }

    fun setInitialPosture(animation: SmdModelAnimation) {
        val firstFrame = animation.frames.firstOrNull() ?: return
        for (movement in firstFrame.boneMovements) {
            val bone = boneById[movement.boneId] ?: continue
            bone.move(movement.position, movement.rotation)
        }
    }

}