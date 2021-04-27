package ca.landonjw.kotlinmon.client.render.models.smd.mesh

import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdTriangle
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdVertex
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelBone
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
import ca.landonjw.kotlinmon.util.math.geometry.Axis
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.util.math.geometry.TransformationBuilder
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Tuple

class SmdMesh(
    triangles: List<SmdTriangle>,
    private val skeleton: SmdModelSkeleton,
    var texture: Material
) {

    val links: MutableMap<SmdVertex, List<VertexBoneLink>> = mutableMapOf()

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

    fun transform(bone: SmdModelBone, transformation: TransformationMatrix) {
        for (vertex in links.keys) {
            if (vertex.links?.containsKey(bone.id) == true) {
                val transformed = transformation * vertex.dirtyPosition
                vertex.dirtyPosition = transformed
            }
        }
    }

    fun getVertices(): List<MeshVertex> {
        val vertices: MutableList<MeshVertex> = mutableListOf()
        links.forEach { (vertex, links) ->
            val parent = links.firstOrNull { it.bone.id == 11 || it.bone.id == 10 }
            if (parent != null) {
                val position: GeometricPoint = vertex.dirtyPosition.copy()
                val normal: GeometricPoint = vertex.dirtyPosition.copy()
                vertices.add(MeshVertex(
                    position = position,
                    normal = normal,
                    uvMap = vertex.uvMap
                ))
            }
        }
        return vertices
    }

}

data class MeshVertex(
    val position: GeometricPoint,
    val normal: GeometricPoint,
    val uvMap: Tuple<Float, Float>
)

data class Material(
    val resourceLocation: ResourceLocation
)

data class VertexBoneLink(
    val vertex: SmdVertex,
    val bone: SmdModelBone,
    val weight: Float
)