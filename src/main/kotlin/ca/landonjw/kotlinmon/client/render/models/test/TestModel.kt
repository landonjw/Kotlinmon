package ca.landonjw.kotlinmon.client.render.models.test


class TestModel(val bones: MutableList<TestBone>, val vertices: MutableList<TestMeshVertex>) {

    val root: TestBone = findRoot()

    init {
        distributeVertices()
        setParent()
        setChildren()
        reformBones()
    }

    fun findRoot(): TestBone {
        for (bone in bones) {
            if (bone.parent == null && bone.children.isNotEmpty()) {
                return bone
            }
        }
        for (bone in bones) {
            if (bone.name != "blender_implicit") return bone
        }
        throw IllegalStateException("no root bone")
    }

    fun getBoneById(id: Int): TestBone? = bones.firstOrNull { it.id == id }

    fun distributeVertices() {
        vertices.forEach { vertex ->
            vertex.links.forEach { (id, _) ->
                val bone = getBoneById(id)
                bone?.vertices?.add(vertex)
            }
        }
    }

    fun reformBones() {
        root.reformChildren()
        bones.forEach { it.invertRest() }
    }

    fun resetVertices() {
        vertices.forEach { it.reset() }
    }

    fun setParent() {
        for (bone in bones) {
            if (bone.parentId != -1) {
                val parent = this.bones.firstOrNull { it.id == bone.parentId }
                if (parent != null) bone.parent = parent
            }
        }
    }

    fun setChildren() {
        for (bone in bones) {
            this.bones.filter { it.parent == bone }.forEach { bone.children.add(it) }
        }
    }

}