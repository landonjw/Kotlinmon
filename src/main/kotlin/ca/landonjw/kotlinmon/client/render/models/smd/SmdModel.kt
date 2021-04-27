package ca.landonjw.kotlinmon.client.render.models.smd

import ca.landonjw.kotlinmon.client.render.models.api.Model
import ca.landonjw.kotlinmon.client.render.models.api.animations.ModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.SmdMesh
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import net.minecraft.util.math.vector.Vector3f

class SmdModel(
    override val skeleton: SmdModelSkeleton,
    val mesh: SmdMesh,
    val scale: Float = 1f
) : Model {

    private val _animations: MutableMap<String, SmdModelAnimation> = mutableMapOf()
    override val animations: Map<String, ModelAnimation>
        get() = _animations

    private var _currentAnimation: ModelAnimation? = null
    override val currentAnimation: ModelAnimation?
        get() = _currentAnimation

    init {
        skeleton.bones.forEach { it.mesh = mesh }
    }

    override fun addAnimation(token: String, animation: ModelAnimation) {
        val smdAnimation = animation as? SmdModelAnimation ?: return

        if (animations.containsKey(token)) throw IllegalArgumentException("token $token has already been registered")
        _animations[token] = animation
    }

    override fun setAnimation(animation: String?) {
        if (animation == null) tPose()
        if (animations[animation] == null) {
            tPose()
        }
        else {
            skeleton.setInitialPosture(_animations[animation]!!)
        }
        this._currentAnimation = animations[animation]
    }

    override fun tPose() {
        skeleton.resetPosture()
        currentAnimation?.apply(0)
        _currentAnimation = null
    }

}