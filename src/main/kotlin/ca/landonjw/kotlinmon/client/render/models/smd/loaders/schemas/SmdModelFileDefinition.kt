package ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas

import net.minecraft.util.Tuple
import net.minecraft.util.math.vector.Vector3f

data class SmdModelFileDefinition(
    val version: Int = 1,
    val bones: List<SmdBoneDefinition>,
    val boneLocations: List<SmdBoneLocationDefinition>,
    val polygonMesh: List<SmdTriangle>
)

data class SmdBoneDefinition(
    val id: Int,
    val name: String,
    val parent: Int
)

data class SmdBoneLocationDefinition(
    val boneId: Int,
    val location: Vector3f,
    val orientation: Vector3f
)

data class SmdTriangle(
    // Defines the material to apply to triangle. File format should be ignored.
    // Paths should NOT be included.
    val material: String,
    val vertex1: SmdVertex,
    val vertex2: SmdVertex,
    val vertex3: SmdVertex
)

data class SmdVertex(
    // The unique identifier of the parent bone
    val parentId: Int,
    val position: Vector3f,
    // Used to smooth the surface of the model.
    // See: https://en.wikipedia.org/wiki/Normal_%28geometry%29
    val normal: Vector3f,
    // The vertex's UV map co-ordinates.
    // See: https://developer.valvesoftware.com/wiki/UV_map
    val uvMap: Tuple<Float, Float>,
    // This is OPTIONAL, and defines a series of weightmap links.
    // Key is Bone ID, value is weight on the vertex.
    // See: https://developer.valvesoftware.com/wiki/Enveloping
    val links: Map<Int, Float>? = null
)