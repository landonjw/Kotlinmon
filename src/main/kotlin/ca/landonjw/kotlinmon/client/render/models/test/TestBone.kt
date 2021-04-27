package ca.landonjw.kotlinmon.client.render.models.test

import ca.landonjw.kotlinmon.Matrix4f


class TestBone(val id: Int, val parentId: Int, val name: String, var currentFrame: TestAnimationFrame? = null) {

    var rest: Matrix4f? = null
    var restInverted: Matrix4f? = null

    var transformation: Matrix4f? = Matrix4f()

    var vertices: MutableList<TestMeshVertex> = mutableListOf()

    var children: MutableList<TestBone> = mutableListOf()
    var parent: TestBone? = null

    fun setModified() {
        var real: Matrix4f? = null
        var realInverted: Matrix4f? = null
        if (currentFrame != null) {
            realInverted = currentFrame?.transformations?.get(id)!!
            real = Matrix4f.invert(currentFrame?.transformations?.get(id)!!, null)
        }
        else {
            realInverted = rest
            real = restInverted
        }
        val delta = Matrix4f()
        Matrix4f.mul(realInverted!!, real!!, delta)
        this.transformation = if (parent != null) Matrix4f.mul(parent!!.transformation, delta, initTransform()) else delta
        children.forEach { it.setModified() }
    }

    fun initTransform(): Matrix4f {
        if (transformation == null) {
            transformation = Matrix4f()
        }
        return transformation!!
    }

    fun reform(parentTransform: Matrix4f) {
        rest = Matrix4f.mul(parentTransform, rest!!, null)
        reformChildren()
    }

    fun reformChildren() {
        children.forEach { it.reform(this.rest!!) }
    }

    fun invertRest() {
        restInverted = Matrix4f.invert(rest, null)
    }

    fun transform() {
        if (currentFrame != null) {
            val transformation = currentFrame!!.transformations[id]
            if (transformation != null) {
                val change = Matrix4f()
                Matrix4f.mul(transformation, restInverted, change)
                this.transformation = if (this.transformation == null) change else Matrix4f.mul(this.transformation, change, this.transformation)
            }
        }
        vertices.forEach { it.transform(this) }
        reset()
    }

    fun reset() {
        transformation?.setIdentity()
    }

}