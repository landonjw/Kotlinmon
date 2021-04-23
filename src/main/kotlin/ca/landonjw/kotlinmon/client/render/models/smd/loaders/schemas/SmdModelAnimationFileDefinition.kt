package ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas

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
    val translation: Vector3f,
    val rotation: Vector3f
)