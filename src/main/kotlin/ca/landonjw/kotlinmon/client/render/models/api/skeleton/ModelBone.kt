package ca.landonjw.kotlinmon.client.render.models.api.skeleton

import net.minecraft.util.math.vector.Vector3f

interface ModelBone {
    val children: List<ModelBone>
    val parent: ModelBone?

    fun move(translation: Vector3f? = null, rotation: Vector3f? = null)
    fun reset()
}