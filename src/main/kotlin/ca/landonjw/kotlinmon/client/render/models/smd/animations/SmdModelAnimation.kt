package ca.landonjw.kotlinmon.client.render.models.smd.animations

import ca.landonjw.kotlinmon.client.render.models.api.animations.ModelAnimation

class SmdModelAnimation(
    val frames: List<SmdModelAnimationFrame>
) : ModelAnimation {

    var currentFrame: Int = 0
        private set

    override fun animate() {
        animate(currentFrame)
        currentFrame = (currentFrame + 1) % frames.size
    }

    fun animate(frame: Int) {
        frames[frame].apply()
    }

}