package ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.getParentPath
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.util.math.geometry.toRadians
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3f

object PQCFileLoader {

    fun load(location: ResourceLocation): PQCSchema {
        val parentPath = getParentPath(location)

        val lines = readLinesFromResource(location)
        val builder = PQCSchemaBuilder()
        val animations = mutableListOf<PQCAnimation>()
        for (line in lines) {
            val split = line.split(" ")
            if (split.isEmpty()) continue
            when (split[0]) {
                "\$body" -> {
                    checkArgumentSize(2, split.size)
                    val modelFile = split[1]
                    checkFileFormat(".smd", modelFile)
                    builder.modelPath = ResourceLocation(Kotlinmon.MODID, "$parentPath/models/$modelFile")
                }
                "\$anim" -> {
                    checkArgumentSize(3, split.size)
                    val animationName = split[1]
                    val animationFile = split[2]
                    checkFileFormat(".smd", animationFile)
                    val resourceLoc = ResourceLocation(Kotlinmon.MODID, "$parentPath/animations/$animationFile")
                    animations.add(PQCAnimation(animationName, resourceLoc))
                }
                "\$scale" -> {
                    checkArgumentSize(4, split.size)
                    builder.scale = parseVector3(split.subList(1, split.size))
                }
                "\$rotation" -> {
                    checkArgumentSize(4, split.size)
                    val x = split[1].toFloat().toRadians()
                    val y = split[2].toFloat().toRadians()
                    val z = split[3].toFloat().toRadians()
                    builder.rotationOffset = Vector3f(x, y, z)
                }
                "\$position" -> {
                    checkArgumentSize(4, split.size)
                    val x = split[1].toFloat()
                    val y = split[2].toFloat()
                    val z = split[3].toFloat()
                    builder.positionOffset = GeometricPoint(x, y, z)
                }
                "\$glitch" -> {
                    checkArgumentSize(4, split.size)
                    val x = split[1].toFloat().toRadians()
                    val y = split[2].toFloat().toRadians()
                    val z = split[3].toFloat().toRadians()
                    builder.glitch = Vector3f(x, y, z)
                }
            }
        }
        builder.animations = animations
        return builder.build()
    }

    private fun parseVector3(args: List<String>): Vector3f {
        val x = args[0].toFloat()
        val y = args[1].toFloat()
        val z = args[2].toFloat()
        return Vector3f(x, y, z)
    }

    private fun checkArgumentSize(expected: Int, actual: Int) {
        if (actual != expected) throw IllegalArgumentException("expected $expected arguments, got $actual")
    }

    private fun checkFileFormat(expected: String, line: String) {
        if (!line.endsWith(expected)) throw IllegalArgumentException("file does not end with $expected")
    }

}

private data class PQCSchemaBuilder(
    var animations: List<PQCAnimation>? = null,
    var modelPath: ResourceLocation? = null,
    var scale: Vector3f? = null,
    var rotationOffset: Vector3f? = null,
    var positionOffset: GeometricPoint? = null,
    var glitch: Vector3f? = null
) {
    fun build(): PQCSchema {
        return PQCSchema(
            animations = animations ?: listOf(),
            modelPath = modelPath!!,
            scale = scale ?: Vector3f(1.0f, 1.0f, 1.0f),
            rotationOffset = rotationOffset ?: Vector3f(0f, 0f, 0f),
            positionOffset = positionOffset ?: GeometricPoint(),
            glitch = glitch ?: Vector3f(0f, 0f, 0f)
        )
    }
}