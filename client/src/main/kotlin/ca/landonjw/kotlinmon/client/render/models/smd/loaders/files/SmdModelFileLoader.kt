package ca.landonjw.kotlinmon.client.render.models.smd.loaders.files

import ca.landonjw.kotlinmon.client.render.models.smd.loaders.files.schemas.*
import ca.landonjw.kotlinmon.util.math.geometry.GeometricNormal
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Tuple
import net.minecraft.util.math.vector.Vector3f

class SmdModelFileLoader {

    fun load(location: ResourceLocation): SmdModelSchema {
        val lines = readLinesFromResource(location)

        var builder = SmdModelSchemaBuilder()

        var lineIndex = 0
        lineIndex = parseVersion(lines, lineIndex, builder)
        lineIndex = parseBones(lines, lineIndex, builder)
        lineIndex = parseBoneLocations(lines, lineIndex, builder)
        lineIndex = parsePolygonMesh(lines, lineIndex, builder)

        return builder.build()
    }

    private fun parseVersion(lines: List<String>, startIndex: Int, builder: SmdModelSchemaBuilder): Int {
        val line = lines[startIndex]
        if (!line.startsWith("version")) throw IllegalStateException("expected header and couldn't find version")
        val split: List<String> = line.split(" ")
        if (split.size != 2) throw IllegalStateException("found invalid amount of arguments in header")
        val version = split[1].toIntOrNull() ?: throw IllegalStateException("version could not be identified")

        builder.version = version
        return startIndex + 1
    }

    private fun parseBones(lines: List<String>, startIndex: Int, builder: SmdModelSchemaBuilder): Int {
        var lineIndex = startIndex
        var line = lines[startIndex]
        if (line != "nodes") throw IllegalStateException("expected start of node block")

        line = lines[++lineIndex]

        val bones: MutableList<SmdBoneSchema> = mutableListOf()
        while (line != "end") {
            bones.add(parseBone(line))
            line = lines[++lineIndex]
        }

        builder.bones = bones
        return lineIndex + 1
    }

    private fun parseBone(line: String): SmdBoneSchema {
        val values = line.splitSmdValues()
        if (values.size != 3) throw IllegalStateException("expected 3 arguments for bone")
        val boneId = values[0].toIntOrNull() ?: throw IllegalStateException("could not parse bone id")
        val boneName = values[1].replace("\"", "")
        val parentId = values[2].toIntOrNull() ?: throw IllegalStateException("could not parse parent bone id")

        return SmdBoneSchema(boneId, boneName, parentId)
    }

    private fun parseBoneLocations(lines: List<String>, startIndex: Int, builder: SmdModelSchemaBuilder): Int {
        var lineIndex = startIndex
        var line = lines[startIndex]
        if (line != "skeleton") throw IllegalStateException("expected start of skeleton block")

        lineIndex += 2 // Skip 'time 0' line
        line = lines[lineIndex]

        val boneLocations: MutableList<SmdBoneLocationSchema> = mutableListOf()
        while (line != "end") {
            boneLocations.add(parseBoneLocation(line))
            line = lines[++lineIndex]
        }

        builder.boneLocations = boneLocations
        return lineIndex + 1
    }

    private fun parseBoneLocation(line: String): SmdBoneLocationSchema {
        val values = line.splitSmdValues()
        if (values.size != 7) throw IllegalStateException("expected 7 arguments for bone location")

        val boneId = values[0].toIntOrNull() ?: throw IllegalStateException("could not parse bone id")
        val xPos = values[1].toFloatOrNull() ?: throw IllegalStateException("could not parse x position")
        val yPos = values[2].toFloatOrNull() ?: throw IllegalStateException("could not parse y position")
        val zPos = values[3].toFloatOrNull() ?: throw IllegalStateException("could not parse z position")
        val location = GeometricPoint(xPos, -yPos, -zPos)

        val xRot = values[4].toFloatOrNull() ?: throw IllegalStateException("could not parse x rotation")
        val yRot = values[5].toFloatOrNull() ?: throw IllegalStateException("could not parse y rotation")
        val zRot = values[6].toFloatOrNull() ?: throw IllegalStateException("could not parse z rotation")
        val orientation = Vector3f(xRot, -yRot, -zRot)

        return SmdBoneLocationSchema(boneId, location, orientation)
    }

    private fun parsePolygonMesh(lines: List<String>, startIndex: Int, builder: SmdModelSchemaBuilder): Int {
        var lineIndex = startIndex
        var line = lines[startIndex]
        if (line != "triangles") throw IllegalStateException("expected start of triangles block")

        line = lines[++lineIndex]

        val mesh: MutableList<SmdMeshTriangleSchema> = mutableListOf()
        while (line != "end") {
            val material = lines[lineIndex++]
            val vertex1 = parsePolygonVertex(lines[lineIndex++])
            val vertex2 = parsePolygonVertex(lines[lineIndex++])
            val vertex3 = parsePolygonVertex(lines[lineIndex++])

            mesh.add(SmdMeshTriangleSchema(material, vertex1, vertex2, vertex3))
            line = lines[lineIndex]
        }

        builder.polygonMesh = mesh
        return lineIndex + 1
    }

    private fun parsePolygonVertex(line: String): SmdMeshVertexSchema {
        val values = line.splitSmdValues()
        if (values.size < 9) throw java.lang.IllegalStateException("expected atleast 9 arguments for triangle vertex")

        val parentBone = values[0].toIntOrNull() ?: throw IllegalStateException("could not parse parent bone id")
        val xPos = values[1].toFloatOrNull() ?: throw IllegalStateException("could not parse x position")
        val yPos = values[2].toFloatOrNull() ?: throw IllegalStateException("could not parse y position")
        val zPos = values[3].toFloatOrNull() ?: throw IllegalStateException("could not parse z position")
        val position = GeometricPoint(xPos, -yPos, -zPos)

        val xNorm = values[4].toFloatOrNull() ?: throw IllegalStateException("could not parse x normal")
        val yNorm = values[5].toFloatOrNull() ?: throw IllegalStateException("could not parse y normal")
        val zNorm = values[6].toFloatOrNull() ?: throw IllegalStateException("could not parse z normal")
        val normal = GeometricNormal(xNorm, -yNorm, -zNorm)

        val u = values[7].toFloatOrNull() ?: throw IllegalStateException("could not parse u texture coordinate")
        val v = values[8].toFloatOrNull() ?: throw IllegalStateException("could not parse v texture coordinate")
        val uvMap = Tuple(u, 1 - v)

        var links: MutableMap<Int, Float>? = null
        if (values.size > 9) {
            val numLinks = values[9].toIntOrNull() ?: throw IllegalStateException("could not parse link number")
            if (values.size != 10 + (numLinks * 2))
                throw IllegalStateException("unexpected number of arguments (expected ${10 + (numLinks * 2)}, got ${values.size})")
            for (i in 10 until values.size step 2) {
                val boneId = values[i].toIntOrNull() ?: throw IllegalStateException("could not parse bone id")
                val weight = values[i + 1].toFloatOrNull() ?: throw IllegalStateException("could not parse weight")

                if (links == null) links = mutableMapOf()
                links[boneId] = weight
            }
        }

        return SmdMeshVertexSchema(parentBone, position, normal, uvMap, links)
    }

}

private data class SmdModelSchemaBuilder(
    var version: Int = -1,
    var bones: List<SmdBoneSchema> = mutableListOf(),
    var boneLocations: List<SmdBoneLocationSchema> = mutableListOf(),
    var polygonMesh: List<SmdMeshTriangleSchema> = mutableListOf()
) {
    fun build(): SmdModelSchema {
        validateBuild()
        return SmdModelSchema(
            version = version,
            bones = bones,
            boneLocations = boneLocations,
            polygonMesh = polygonMesh
        )
    }

    private fun validateBuild() {
        // Validate version
        if (version == null) throw IllegalStateException("smd version not defined")
        validateBones()

        val boneIdToBone = mutableMapOf<Int, SmdBoneSchema>()
        bones.forEach { boneIdToBone[it.id] = it }

        validateBoneLocations(boneIdToBone)
        validatePolygonMesh(boneIdToBone)
    }

    private fun validateBones() {
        if (bones.isEmpty()) throw IllegalStateException("no bones defined")
        if (bones.distinctBy { it.id }.size != bones.size) throw IllegalStateException("all bones do not have unique ids")
        for (bone in bones) {
            if (bone.parent < -1) throw IllegalStateException("bone has illegal id for parent")
            if (bone.parent != -1 && bones.firstOrNull { it.id == bone.parent } == null)
                throw IllegalStateException("bone has illegal id for parent")
        }
    }

    private fun validateBoneLocations(boneIdToBone: Map<Int, SmdBoneSchema>) {
        if (boneLocations.size != bones.size) throw IllegalStateException("all bone locations must be defined")
        for (location in boneLocations) {
            if (boneIdToBone[location.boneId] == null)
                throw IllegalStateException("bone location has unknown bone")
        }
    }

    private fun validatePolygonMesh(boneIdToBone: Map<Int, SmdBoneSchema>) {
        for (polygon in polygonMesh) {
            validatePolygonVertex(polygon.vertex1, boneIdToBone)
            validatePolygonVertex(polygon.vertex2, boneIdToBone)
            validatePolygonVertex(polygon.vertex3, boneIdToBone)
        }
    }

    private fun validatePolygonVertex(vertex: SmdMeshVertexSchema, boneIdToBone: Map<Int, SmdBoneSchema>) {
        if (boneIdToBone[vertex.parentId] == null)
            throw IllegalStateException("polygon vertex mapped to unknown bone id")

        var weightSum = 0f
        vertex.links?.forEach { (boneId, weight) ->
            if (boneIdToBone[boneId] == null) throw IllegalStateException("polygon vertex mapped to unknown bone id")
            weightSum += weight
        }

        if (weightSum <= 0.99f) throw IllegalStateException("polygon vertex has illegal weight sum ($weightSum)")
    }

}