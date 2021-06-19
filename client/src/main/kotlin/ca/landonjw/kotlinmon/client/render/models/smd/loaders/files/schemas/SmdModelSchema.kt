package ca.landonjw.kotlinmon.client.render.models.smd.loaders.files.schemas

import ca.landonjw.kotlinmon.util.math.geometry.GeometricNormal
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import net.minecraft.util.Tuple
import net.minecraft.util.math.vector.Vector3f

data class SmdModelSchema(
    val version: Int = 1,
    val bones: List<SmdBoneSchema>,
    val boneLocations: List<SmdBoneLocationSchema>,
    val polygonMesh: List<SmdMeshTriangleSchema>
)

data class SmdBoneSchema(
    val id: Int,
    val name: String,
    val parent: Int
)

data class SmdBoneLocationSchema(
    val boneId: Int,
    val location: GeometricPoint,
    val orientation: Vector3f
)

data class SmdMeshTriangleSchema(
    // Defines the material to apply to triangle. File format should be ignored.
    // Paths should NOT be included.
    val material: String,
    val vertex1: SmdMeshVertexSchema,
    val vertex2: SmdMeshVertexSchema,
    val vertex3: SmdMeshVertexSchema
)

data class SmdMeshVertexSchema(
    // The unique identifier of the parent bone
    val parentId: Int,
    val position: GeometricPoint,
    // Used to smooth the surface of the model.
    // See: https://en.wikipedia.org/wiki/Normal_%28geometry%29
    val normal: GeometricNormal,
    // The vertex's UV map co-ordinates.
    // See: https://developer.valvesoftware.com/wiki/UV_map
    val uvMap: Tuple<Float, Float>,
    // This is OPTIONAL, and defines a series of weightmap links.
    // Key is Bone ID, value is weight on the vertex.
    // See: https://developer.valvesoftware.com/wiki/Enveloping
    val links: Map<Int, Float>? = null
)