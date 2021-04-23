package ca.landonjw.kotlinmon.client.render.models.smd.skeleton

import ca.landonjw.kotlinmon.client.render.models.api.skeleton.ModelSkeleton

class SmdModelSkeleton(
    override val bones: List<SmdModelBone>
) : ModelSkeleton {

    val boneIdToBone: Map<Int, SmdModelBone>

    init {
        val boneMap = mutableMapOf<Int, SmdModelBone>()
        bones.forEach { bone -> boneMap[bone.id] = bone }
        boneIdToBone = boneMap
    }

    operator fun get(boneId: Int) = boneIdToBone[boneId]

    // TODO: Evaluate what needs to be put here

    fun resetAllBones() {
        bones.forEach { it.reset() }
    }

}