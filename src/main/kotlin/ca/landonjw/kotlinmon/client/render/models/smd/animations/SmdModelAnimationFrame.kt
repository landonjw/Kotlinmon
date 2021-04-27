package ca.landonjw.kotlinmon.client.render.models.smd.animations

import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdBoneTransformation
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
import net.minecraft.util.math.vector.Vector3f

class SmdModelAnimationFrame(
    val boneMovements: List<SmdBoneTransformation>
) {

    fun apply(skeleton: SmdModelSkeleton) {
//        for (bone in skeleton.bones) {
//            bone.reset()
//        }
        for (bone in skeleton.bones) {
            val movement = boneMovements.firstOrNull { it.boneId == bone.id }
            if (movement == null) {
//                bone.applyLastTransformation()
            }
            else {
                bone.move(movement.position, movement.rotation)
            }
        }
    }

}