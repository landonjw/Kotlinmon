package ca.landonjw.kotlinmon.client.render.models.smd.loaders

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimationFrame
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdBoneTransformation
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdModelFileDefinition
import net.minecraft.util.ResourceLocation

object SmdModelAnimationLoader {

    fun load(location: ResourceLocation, model: SmdModel): SmdModelAnimation {
        val definition = SmdModelAnimationFileLoader.load(location)

        val modelAnimationFrames: MutableList<SmdModelAnimationFrame> = mutableListOf()
        definition.frames.forEach { frame ->
            modelAnimationFrames.add(SmdModelAnimationFrame(frame.transformations))
        }

        return SmdModelAnimation(model, modelAnimationFrames)
    }

}