package ca.landonjw.kotlinmon.client.render.models.api.skeleton

import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import net.minecraft.util.math.vector.Vector3f

interface ModelBone {
    val children: List<ModelBone>
    val parent: ModelBone?

    fun move(translation: GeometricPoint? = null, rotation: Vector3f? = null)
    fun reset()
}