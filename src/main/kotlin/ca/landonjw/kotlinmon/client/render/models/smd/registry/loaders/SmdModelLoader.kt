package ca.landonjw.kotlinmon.client.render.models.smd.registry.loaders

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.registry.loaders.files.schemas.*
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.SmdMesh
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.SmdMeshVertex
import ca.landonjw.kotlinmon.client.render.models.smd.registry.loaders.files.SmdModelFileLoader
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelBone
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
import net.minecraft.util.ResourceLocation

/**
 * Loads a [SmdModel] from a `.smd` file.
 * This loading is done synchronously by default, and should often be handled otherwise
 * in any implementations that use it.
 *
 * @author landonjw
 */
object SmdModelLoader {

    /**
     * Loads a [SmdModel] from a `.smd` file.
     * This simply loads the model, it will not add any animations and will be t-posed.
     *
     * @param location the location to load `.smd` file from
     * @return an smd model from the location
     */
    fun load(location: ResourceLocation): SmdModel {
        val schema = SmdModelFileLoader.load(location)

        val texture = getDefaultTexture(location)
        val vertices = getMeshVertices(schema)
        val mesh = SmdMesh(vertices, texture)

        val skeleton = getSkeleton(schema, mesh)
        return SmdModel(skeleton)
    }

    private fun getMeshVertices(schema: SmdModelFileDefinition): List<SmdMeshVertex> {
        val meshVertices = mutableListOf<SmdMeshVertex>()
        schema.polygonMesh.forEach {
            meshVertices.add(getMeshVertexFromDefinition(it.vertex1))
            meshVertices.add(getMeshVertexFromDefinition(it.vertex2))
            meshVertices.add(getMeshVertexFromDefinition(it.vertex3))
        }
        return meshVertices
    }

    private fun getDefaultTexture(location: ResourceLocation): ResourceLocation {
        val split = location.path.split("/")
        val parentPath = split.subList(0, split.size - 2)
            .reduce { acc, s -> "$acc/$s" }
        val fileName = split.last().replace(".smd", "")
        return ResourceLocation(Kotlinmon.MODID, "$parentPath/textures/$fileName.png")
    }

    private fun getSkeleton(schema: SmdModelFileDefinition, mesh: SmdMesh): SmdModelSkeleton {
        val boneLocationDefById = mutableMapOf<Int, SmdBoneLocationDefinition>()
        schema.boneLocations.forEach { boneLocationDefById[it.boneId] = it }

        val modelBones = mutableListOf<SmdModelBone>()
        for (boneDef in schema.bones) {
            val boneLocDef = boneLocationDefById[boneDef.id] ?: continue
            modelBones.add(getBoneFromDefinition(boneDef, boneLocDef))
        }
        linkBones(schema.bones, modelBones)
        return SmdModelSkeleton(modelBones, mesh)
    }

    private fun getBoneFromDefinition(
        boneDef: SmdBoneDefinition,
        locationDef: SmdBoneLocationDefinition
    ): SmdModelBone {
        return SmdModelBone(boneDef.id, boneDef.name, locationDef.location, locationDef.orientation)
    }

    private fun linkBones(boneDefinitions: List<SmdBoneDefinition>, bones: List<SmdModelBone>) {
        val boneById = mutableMapOf<Int, SmdModelBone>()
        bones.forEach { boneById[it.id] = it }
        for (boneDef in boneDefinitions) {
            val bone = boneById[boneDef.id] ?: continue
            val parent = boneById[boneDef.parent] ?: continue
            bone.parent = parent
        }
    }

    private fun getMeshVertexFromDefinition(vertex: SmdVertex): SmdMeshVertex {
        return SmdMeshVertex(
            weights = vertex.links ?: mapOf(),
            basePosition = vertex.position,
            baseNormal = vertex.normal,
            u = vertex.uvMap.a,
            v = vertex.uvMap.b
        )
    }

}