package ca.landonjw.kotlinmon.client.render.models.smd.mesh

import net.minecraft.util.ResourceLocation

/**
 * The polygon mesh of a [SmdModel].
 *
 * @property vertices the vertices of all polygons in the mesh
 * @property texture the texture to be applied to the mesh
 * @property verticesByBone a map of bones and vertices they influence
 *                          key is bone id, value is list of vertices they influence
 *
 * @author landonjw
 */
class SmdMesh(
    val vertices: List<SmdMeshVertex>,
    var texture: ResourceLocation
) {

    val verticesByBone: Map<Int, List<SmdMeshVertex>>

    init {
        // Iterates through vertices and populates map of bone-to-vertex relationships
        val verticesByBone = mutableMapOf<Int, MutableList<SmdMeshVertex>>()
        vertices.forEach { vertex ->
            vertex.weights.keys.forEach { bone ->
                if (!verticesByBone.containsKey(bone)) verticesByBone[bone] = mutableListOf()
                verticesByBone[bone]!!.add(vertex)
            }
        }
        this.verticesByBone = verticesByBone
    }

    /** Resets the mesh to it's base state. */
    fun reset() {
        vertices.forEach { it.reset() }
    }

}