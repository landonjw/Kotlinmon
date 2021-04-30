package ca.landonjw.kotlinmon.client.render.models.smd

import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.Scale
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdRenderProperty
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton

/**
 * A model that uses the `.smd` format.
 * Typically, you will want to load this from the [SmdModelRegistry].
 *
 * @property renderProperties the various properties used to modify the rendering of the model
 * @property skeleton the skeleton of the model, holding the bones and mesh
 * @property animations any animations currently added to the model
 *                      key is animation name, value is animation
 * @property currentAnimation the current animation
 *
 * @author landonjw
 */
class SmdModel(
    val skeleton: SmdModelSkeleton,
    val renderProperties: MutableList<SmdRenderProperty<*>> = mutableListOf()
) {

    private val _animations: MutableMap<String, SmdModelAnimation> = mutableMapOf()
    val animations: Map<String, SmdModelAnimation>
        get() = _animations.toMap()

    var currentAnimation: SmdModelAnimation? = null
        private set

    /**
     * Sets the current animation of the model.
     * If null, will t-pose the model and clear current animation.
     *
     * @param animation the animation to set
     */
    fun setAnimation(animation: String?) {
        if (!_animations.containsKey(animation)) return reset()
        if (currentAnimation == _animations[animation]) return
        currentAnimation = _animations[animation]
        return
    }

    /**
     * Adds an animation to the model.
     * If an animation under the name is already added, it will be overwritten.
     *
     * @param name the name of the animation
     * @param animation the animation to add
     */
    fun addAnimation(name: String, animation: SmdModelAnimation) {
        _animations[name] = animation
    }

    /**
     * Resets the model, causing it to t-pose and lose it's current animation.
     */
    private fun reset() {
        currentAnimation = null
        skeleton.reset()
    }

    inline fun <reified T: SmdRenderProperty<*>> getProperty(): T? {
        return renderProperties.firstOrNull { it is T } as? T
    }

    inline fun <reified T: SmdRenderProperty<*>> replaceProperty(new: T) {
        renderProperties.removeIf { it is T }
        renderProperties.add(new)
    }

    inline fun <reified T: SmdRenderProperty<*>> replaceProperty(replacement: (T) -> T) {
        val old = renderProperties.firstOrNull { it is T } as T
        val new = replacement(old)

        renderProperties.remove(old)
        renderProperties.add(new)
    }

}