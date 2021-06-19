package ca.landonjw.kotlinmon.client.render.models.smd.loaders.files

import ca.landonjw.kotlinmon.client.render.models.smd.loaders.files.schemas.*
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3f

class SmdAnimationFileLoader {

    fun load(location: ResourceLocation): SmdAnimationSchema {
        val lines = readLinesFromResource(location)

        var builder = SmdAnimationSchemaBuilder()

        var lineIndex = 0

        parseAnimationFrames(lines, lineIndex, builder)

        return builder.build()
    }

    private fun parseAnimationFrames(
        lines: List<String>,
        startIndex: Int,
        builder: SmdAnimationSchemaBuilder
    ): Int {
        var lineIndex = startIndex
        var line = lines[startIndex]
        if (line != "skeleton") throw IllegalStateException("expected start of skeleton block")

        line = lines[++lineIndex]

        val frames: MutableList<SmdAnimationFrameSchema> = mutableListOf()
        while (line != "end") {
            val frame = parseFrame(line)
            val transformations: MutableList<SmdBoneTransformationSchema> = mutableListOf()
            line = lines[++lineIndex]
            while (!line.startsWith("time") && line != "end") {
                transformations.add(parseBoneTransformation(line))
                line = lines[++lineIndex]
            }
            frames.add(SmdAnimationFrameSchema(frame, transformations))
        }

        builder.frames = frames
        return lineIndex
    }

    private fun parseFrame(line: String): Int {
        val values = line.splitSmdValues()
        if (values.size != 2) throw IllegalStateException("expected 2 arguments for frame")
        val frame = values[1].toIntOrNull() ?: throw IllegalStateException("could not parse frame number")

        return frame
    }

    private fun parseBoneTransformation(line: String): SmdBoneTransformationSchema {
        val values = line.splitSmdValues()
        if (values.size != 7) throw IllegalStateException("expected 7 arguments for bone transformation")

        val boneId = values[0].toIntOrNull() ?: throw IllegalStateException("could not parse bone id")
        val xPos = values[1].toFloatOrNull() ?: throw IllegalStateException("could not parse x position")
        val yPos = values[2].toFloatOrNull() ?: throw IllegalStateException("could not parse y position")
        val zPos = values[3].toFloatOrNull() ?: throw IllegalStateException("could not parse z position")
        val translation = GeometricPoint(xPos, -yPos, -zPos)

        val xRot = values[4].toFloatOrNull() ?: throw IllegalStateException("could not parse x rotation")
        val yRot = values[5].toFloatOrNull() ?: throw IllegalStateException("could not parse y rotation")
        val zRot = values[6].toFloatOrNull() ?: throw IllegalStateException("could not parse z rotation")
        val rotation = Vector3f(xRot, -yRot, -zRot)

        return SmdBoneTransformationSchema(boneId, translation, rotation)
    }

}

data class SmdAnimationSchemaBuilder(
    var frames: List<SmdAnimationFrameSchema> = mutableListOf()
) {
    fun build(): SmdAnimationSchema {
        validateBuild()
        return SmdAnimationSchema(frames)
    }

    private fun validateBuild() {
        var previousFrame = -1
        frames.forEach { frame ->
            if (previousFrame == -1) previousFrame = frame.frame
            if (frame.frame < previousFrame) throw IllegalStateException("frames should be ordered incrementally")
        }
    }
}