package ca.landonjw.kotlinmon.client.render.models.smd.loaders

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdBoneLocationDefinition
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdModelFileDefinition
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.Material
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.SmdMesh
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelBone
import ca.landonjw.kotlinmon.client.render.models.smd.skeleton.SmdModelSkeleton
import ca.landonjw.kotlinmon.util.math.geometry.Axis
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.util.math.geometry.TransformationBuilder
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3f

object SmdModelLoader {

    fun load(location: ResourceLocation, texture: ResourceLocation): SmdModel {
        val modelDefinition = SmdModelFileLoader.load(location)

        val skeleton = getSkeleton(modelDefinition)
        val mesh = SmdMesh(modelDefinition.polygonMesh, skeleton, Material(texture))
        val model = SmdModel(skeleton, mesh)

        return model
    }

    private fun getSkeleton(definition: SmdModelFileDefinition): SmdModelSkeleton {
        val boneIdToBoneLocation: MutableMap<Int, SmdBoneLocationDefinition> = mutableMapOf()
        definition.boneLocations.forEach { boneLoc -> boneIdToBoneLocation[boneLoc.boneId] = boneLoc }

        val boneIdToBone: MutableMap<Int, SmdModelBone> = mutableMapOf()
        val boneBuilders: MutableMap<Int, SmdModelBoneBuilder> = mutableMapOf()

        for (bone in definition.bones) {
            val boneLocation = boneIdToBoneLocation[bone.id] ?: throw IllegalStateException("bone location not found")
            val builder = SmdModelBoneBuilder(bone.id, bone.name, boneLocation.location, boneLocation.orientation, bone.parent)
            boneBuilders[bone.id] = builder
        }

        for (builder in boneBuilders.values) {
            buildBone(builder, boneIdToBone, boneBuilders)
        }

        for (bone in boneIdToBone.values) {
            bone.parent?.addChildBone(bone)
        }

        return SmdModelSkeleton(boneIdToBone.values.sortedBy { it.id })
    }

    private fun buildBone(
        builder: SmdModelBoneBuilder,
        boneIdToBone: MutableMap<Int, SmdModelBone>,
        boneBuilders: MutableMap<Int, SmdModelBoneBuilder>
    ) {
        // Captures the case that a bone was already constructed if it was a dependency of a child
        if (boneIdToBone[builder.id] != null) return

        if (builder.parent != -1 && boneIdToBone[builder.parent] == null) {
            val parentBuilder = boneBuilders[builder.parent] ?: throw IllegalStateException("parent bone not found")
            buildBone(parentBuilder, boneIdToBone, boneBuilders)
        }
        val bone = SmdModelBone(
            id = builder.id,
            name = builder.name,
            parent = boneIdToBone[builder.parent]
        )
        bone.move(builder.baseLocation, builder.baseOrientation)

        boneIdToBone[builder.id] = bone
    }

}

private data class SmdModelBoneBuilder(
    var id: Int,
    var name: String,
    var baseLocation: GeometricPoint,
    var baseOrientation: Vector3f,
    var parent: Int
)