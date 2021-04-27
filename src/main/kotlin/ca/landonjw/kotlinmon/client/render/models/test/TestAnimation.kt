package ca.landonjw.kotlinmon.client.render.models.test

class TestAnimation(
    val model: TestModel,
    val frames: MutableList<TestAnimationFrame> = mutableListOf()
) {

    var currentFrame: Int = 0

    init {
        reform()
    }

    fun apply() {
        model.resetVertices()
        this.model.root.setModified()
        model.bones.forEach { it.currentFrame = frames[currentFrame] }
        model.bones.forEach { it.transform() }
        currentFrame = (currentFrame + 1) % frames.size
    }

    fun reform() {
        for (frame in frames) {
            frame.fixup()
            frame.reform()
        }
    }

}