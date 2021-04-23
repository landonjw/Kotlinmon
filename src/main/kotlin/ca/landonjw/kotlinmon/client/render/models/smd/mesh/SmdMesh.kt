package ca.landonjw.kotlinmon.client.render.models.smd.mesh

import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdTriangle
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdVertex
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelBone
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Tuple
import net.minecraft.util.math.vector.Vector3f

class SmdMesh(
    triangles: List<SmdTriangle>,
    private val skeleton: SmdModelSkeleton,
    var texture: Material
) {

    private val links: MutableMap<SmdVertex, List<VertexBoneLink>> = mutableMapOf()

    init {
        triangles.forEach { triangle ->
            links[triangle.vertex1] = getVertexLinks(triangle.vertex1) ?: listOf()
            links[triangle.vertex2] = getVertexLinks(triangle.vertex2) ?: listOf()
            links[triangle.vertex3] = getVertexLinks(triangle.vertex3) ?: listOf()
        }
    }

    private fun getVertexLinks(vertex: SmdVertex): List<VertexBoneLink>? {
        return vertex.links
            ?.filter { link -> skeleton[link.key] != null }
            ?.map { link -> VertexBoneLink(vertex, skeleton[link.key]!!, link.value) }
            ?.toList()
    }

    fun getVertices(): List<MeshVertex> {
        val vertices: MutableList<MeshVertex> = mutableListOf()
        links.forEach { (vertex, links) ->
            val translation: Vector3f? = getWeightedTranslation(links)
            val rotation: Vector3f? = getWeightedRotation(links)
            vertices.add(MeshVertex(
                position = vertex.position,
                normal = vertex.normal,
                uvMap = vertex.uvMap,
                translation = translation,
                rotation = rotation
            ))
        }
        return vertices
    }

    private fun getWeightedTranslation(links: List<VertexBoneLink>): Vector3f? {
        var translation: Vector3f? = null

        links.forEach { link ->
            if (translation == null) {
                translation = link.bone.translation?.copy()
            }
            else {
                val weightedTranslation = link.bone.translation?.copy()
                weightedTranslation?.mul(link.weight)
                if (weightedTranslation != null) translation!!.add(weightedTranslation)
            }
        }
        return translation
    }

    private fun getWeightedRotation(links: List<VertexBoneLink>): Vector3f? {
        var rotation: Vector3f? = null

        links.forEach { link ->
            if (rotation == null) {
                rotation = link.bone.rotation?.copy()
            }
            else {
                val weightedRotation = link.bone.rotation?.copy()
                weightedRotation?.mul(link.weight)
                if (weightedRotation != null) rotation!!.add(weightedRotation)
            }
        }
        return rotation
    }

}

data class MeshVertex(
    val position: Vector3f,
    val normal: Vector3f,
    val uvMap: Tuple<Float, Float>,
    val translation: Vector3f?,
    val rotation: Vector3f?
)

data class Material(
    val resourceLocation: ResourceLocation
)

private data class VertexBoneLink(
    val vertex: SmdVertex,
    val bone: SmdModelBone,
    val weight: Float
)