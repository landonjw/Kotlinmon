package ca.landonjw.kotlinmon.client.render.models.smd.animations

import ca.landonjw.kotlinmon.client.render.models.api.animations.ModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint

class SmdModelAnimation(
    override val model: SmdModel,
    val frames: List<SmdModelAnimationFrame>
) : ModelAnimation {

    override val totalFrames: Int
        get() = frames.size
    private var currentFrame: Int = 0

    override fun apply() {
        if (currentFrame == 0) {
            model.skeleton.resetPosture()
            apply(currentFrame)
            currentFrame = (currentFrame + 1) % frames.size
        }
        apply(currentFrame)
        currentFrame = (currentFrame + 1) % frames.size
    }

    override fun apply(frame: Int) {
        model.mesh.links.forEach { vertex, links -> vertex.dirtyPosition = vertex.position.copy() }
        frames[frame].apply(model.skeleton)
    }

}