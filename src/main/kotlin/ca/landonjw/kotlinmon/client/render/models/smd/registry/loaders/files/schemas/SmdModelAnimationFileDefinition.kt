package ca.landonjw.kotlinmon.client.render.models.smd.registry.loaders.files.schemas

import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix
import net.minecraft.util.math.vector.Vector3f

data class SmdModelAnimationFileDefinition(
    val frames: List<SmdAnimationFrame>
)

data class SmdAnimationFrame(
    val frame: Int,
    val transformations: List<SmdBoneMovement>
)

data class SmdBoneMovement(
    val boneId: Int,
    val translation: GeometricPoint,
    val rotation: Vector3f
) {
    val transformation: TransformationMatrix by lazy {
        TransformationMatrix.of(translation, rotation)
    }
}