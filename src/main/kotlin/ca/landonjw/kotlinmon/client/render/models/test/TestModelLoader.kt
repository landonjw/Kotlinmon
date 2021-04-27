package ca.landonjw.kotlinmon.client.render.models.test

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.SmdModelFileLoader
import ca.landonjw.kotlinmon.util.math.geometry.Axis
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix
import net.minecraft.util.ResourceLocation

object TestModelLoader {

    fun load(): TestModel {
        val definition = SmdModelFileLoader.load(ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/models/kingler.smd"))
        val bones: MutableList<TestBone> = mutableListOf()
        for (bone in definition.bones) {
            bones.add(TestBone(
                bone.id,
                bone.parent,
                bone.name
            ))
        }
        for (boneLoc in definition.boneLocations) {
            val loc = boneLoc.location
            val rot = boneLoc.orientation
            val matrix = matrix4FromLocRot(loc.x, loc.y, loc.z, rot.x, rot.y, rot.z)
            bones.firstOrNull { it.id == boneLoc.boneId }?.rest = matrix
        }
        val vertices: MutableList<TestMeshVertex> = mutableListOf()
        for (vertex in definition.polygonMesh) {
            vertices.add(
                TestMeshVertex(
                vertex.vertex1.position,
                vertex.vertex1.normal,
                vertex.vertex1.links!!,
                vertex.vertex1.uvMap.a,
                vertex.vertex1.uvMap.b
            ))
            vertices.add(TestMeshVertex(
                vertex.vertex2.position,
                vertex.vertex2.normal,
                vertex.vertex2.links!!,
                vertex.vertex2.uvMap.a,
                vertex.vertex2.uvMap.b
            ))
            vertices.add(TestMeshVertex(
                vertex.vertex3.position,
                vertex.vertex3.normal,
                vertex.vertex3.links!!,
                vertex.vertex3.uvMap.a,
                vertex.vertex3.uvMap.b
            ))
        }

        return TestModel(bones, vertices)
    }

}