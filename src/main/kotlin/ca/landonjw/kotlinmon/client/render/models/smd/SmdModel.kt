package ca.landonjw.kotlinmon.client.render.models.smd

import ca.landonjw.kotlinmon.client.render.models.api.Model
import ca.landonjw.kotlinmon.client.render.models.api.animations.ModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.SmdMesh
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
import net.minecraft.util.math.vector.Vector3f

class SmdModel(
    override val skeleton: SmdModelSkeleton,
    val mesh: SmdMesh,
    val scale: Float = 1f,
    val positionOffset: Vector3f? = null,
    val orientationOffset: Vector3f? = Vector3f(270f, 0f, 0f)
) : Model {

    private val backingAnimations: MutableMap<String, SmdModelAnimation> = mutableMapOf()
    override val animations: Map<String, ModelAnimation>
        get() = backingAnimations

    private var backingCurrentAnimation: ModelAnimation? = null
    override val currentAnimation: ModelAnimation?
        get() = backingCurrentAnimation

    override fun addAnimation(token: String, animation: ModelAnimation) {
        if (animations.containsKey(token)) throw IllegalArgumentException("token $token has already been registered")
        backingAnimations[token] = animation as SmdModelAnimation
    }

    override fun setAnimation(animation: String?) {
        if (animation == null) tPose()
        if (animations[animation] == null) tPose()
        this.backingCurrentAnimation = animations[animation]

        // TODO: Remove
        backingAnimations[animation]?.frames?.get(0)?.boneMovements?.forEach { movement ->
            skeleton[movement.boneId]?.baseLocation = movement.translation
            skeleton[movement.boneId]?.baseOrientation = movement.rotation
        }
    }

    override fun tPose() {
        skeleton.resetAllBones()
    }

}