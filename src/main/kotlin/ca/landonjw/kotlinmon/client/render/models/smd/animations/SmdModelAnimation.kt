package ca.landonjw.kotlinmon.client.render.models.smd.animations

/**
 * An animation for a [SmdModel].
 * This defines a set of animation frames that move the skeleton, creating an animation.
 *
 * @property frames the frames of the animation
 * @property currentFrame the current frame of the animation
 *
 * @author landonjw
 */
class SmdModelAnimation(val frames: List<SmdModelAnimationFrame>) {

    var currentFrame: Int = 0
        private set

    /** Applies the next frame in the animation. */
    fun animate() {
        animate(currentFrame)
        currentFrame = (currentFrame + 1) % frames.size
    }

    /** Applies a specific frame in the animation. */
    fun animate(frame: Int) {
        frames[frame].apply()
    }

}