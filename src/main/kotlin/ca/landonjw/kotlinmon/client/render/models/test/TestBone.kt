package ca.landonjw.kotlinmon.client.render.models.test

import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix


class TestBone(val id: Int, val parentId: Int, val name: String, var currentFrame: TestAnimationFrame? = null) {

    var rest: TransformationMatrix? = null
    var restInverted: TransformationMatrix? = null

    var transformation: TransformationMatrix? = TransformationMatrix.identityMatrix

    var vertices: MutableList<TestMeshVertex> = mutableListOf()

    var children: MutableList<TestBone> = mutableListOf()
    var parent: TestBone? = null

    fun setModified() {
        var real: TransformationMatrix?
        var realInverted: TransformationMatrix?
        if (currentFrame != null) {
            realInverted = currentFrame?.transformations?.get(id)!!
            real = TransformationMatrix.invert(currentFrame?.transformations?.get(id)!!)
        }
        else {
            realInverted = rest
            real = restInverted
        }
        val delta = realInverted!! * real!!
        this.transformation = if (parent != null) parent!!.transformation!! * delta else delta
        children.forEach { it.setModified() }
    }

    fun initTransform(): TransformationMatrix {
        if (transformation == null) {
            transformation = TransformationMatrix.identityMatrix
        }
        return transformation!!
    }

    fun reform(parentTransform: TransformationMatrix) {
        rest = parentTransform * rest!!
        reformChildren()
    }

    fun reformChildren() {
        children.forEach { it.reform(this.rest!!) }
    }

    fun invertRest() {
        restInverted = TransformationMatrix.invert(rest!!)
    }

    fun transform() {
        if (currentFrame != null) {
            val transformation = currentFrame!!.transformations[id]
            if (transformation != null) {
                val change = transformation * restInverted!!
                this.transformation = if (this.transformation == null) change else (this.transformation!! * change)
            }
        }
        vertices.forEach { it.transform(this) }
        reset()
    }

    fun reset() {
        transformation = TransformationMatrix.identityMatrix
    }

}