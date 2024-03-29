package ca.landonjw.kotlinmon.client.render.models.smd.loaders.files.schemas

import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3f

data class PQCSchema(
    val animations: List<PQCAnimationSchema> = listOf(),
    val modelPath: ResourceLocation,
    val scale: Vector3f?,
    val rotationOffset: Vector3f?,
    val positionOffset: GeometricPoint?
)

data class PQCAnimationSchema(
    val name: String,
    val path: ResourceLocation
)