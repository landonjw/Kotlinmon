package ca.landonjw.kotlinmon.client.render.models.smd.animations

import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdBoneTransformation
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
import net.minecraft.util.math.vector.Vector3f

class SmdModelAnimationFrame(
    val boneMovements: List<SmdBoneTransformation>
) {

    fun apply(skeleton: SmdModelSkeleton) {
        skeleton.resetAllBones()
        boneMovements.forEach { movement ->
            val bone = skeleton[movement.boneId]
            if (bone != null) {
//                val translation = getDifference(bone.location, movement.translation)
//                val rotation = getDifference(bone.orientation, movement.rotation)
//                skeleton[movement.boneId]?.move(translation, rotation)

                val translation = getDifference(bone.location, movement.translation)
                val rotation = getDifference(bone.orientation, movement.rotation)
                skeleton[movement.boneId]?.move(translation, rotation)
            }
        }
    }

    private fun getDifference(vector1: Vector3f, vector2: Vector3f): Vector3f {
        val difference = vector1.copy()
        difference.sub(vector2)
        return difference
    }

}