package ca.landonjw.kotlinmon.client.render.models.smd

import ca.landonjw.kotlinmon.client.render.models.api.Model
import ca.landonjw.kotlinmon.client.render.models.api.renderer.RenderProperty
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton

class SmdModel(
    override val skeleton: SmdModelSkeleton,
    val renderProperties: MutableList<RenderProperty<*>> = mutableListOf()
) : Model {

    private val _animations: MutableMap<String, SmdModelAnimation> = mutableMapOf()
    override val animations: Map<String, SmdModelAnimation>
        get() = _animations.toMap()

    override var currentAnimation: String? = null
        private set

    override fun animate(animation: String?) {
        if (currentAnimation == animation) return
        reset()
        currentAnimation = animation
    }

    fun addAnimation(name: String, animation: SmdModelAnimation) {
        _animations[name] = animation
    }

    private fun reset() {
        skeleton.reset()
    }

}