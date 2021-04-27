package ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas

import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import net.minecraft.util.math.vector.Vector3f

data class SmdModelAnimationFileDefinition(
    val frames: List<SmdAnimationFrame>
)

data class SmdAnimationFrame(
    val frame: Int,
    val transformations: List<SmdBoneTransformation>
)

data class SmdBoneTransformation(
    val boneId: Int,
    val position: GeometricPoint,
    val rotation: Vector3f
)