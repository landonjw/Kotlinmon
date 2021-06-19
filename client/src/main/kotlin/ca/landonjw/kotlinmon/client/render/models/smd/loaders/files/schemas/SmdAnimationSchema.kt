package ca.landonjw.kotlinmon.client.render.models.smd.loaders.files.schemas

import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix
import net.minecraft.util.math.vector.Vector3f

data class SmdAnimationSchema(
    val frames: List<SmdAnimationFrameSchema>
)

data class SmdAnimationFrameSchema(
    val frame: Int,
    val transformations: List<SmdBoneTransformationSchema>
)

data class SmdBoneTransformationSchema(
    val boneId: Int,
    val translation: GeometricPoint,
    val rotation: Vector3f
) {
    val transformation: TransformationMatrix by lazy {
        TransformationMatrix.of(translation, rotation)
    }
}