package ca.landonjw.kotlinmon.client.render.models.smd.animations

import ca.landonjw.kotlinmon.client.render.models.api.animations.ModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel

class SmdModelAnimation(
    override val model: SmdModel,
    val frames: List<SmdModelAnimationFrame>
) : ModelAnimation {

    override val totalFrames: Int
        get() = frames.size
    private var currentFrame: Int = 0

    override fun apply() {
        frames[currentFrame].apply(model.skeleton)
        currentFrame = (currentFrame + 1) % frames.size
    }

}