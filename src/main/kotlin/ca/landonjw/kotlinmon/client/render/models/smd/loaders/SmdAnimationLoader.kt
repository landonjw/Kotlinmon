package ca.landonjw.kotlinmon.client.render.models.smd.loaders

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimationFrame
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdModelAnimationFileLoader
import net.minecraft.util.ResourceLocation

object SmdAnimationLoader {

    fun load(location: ResourceLocation, model: SmdModel): SmdModelAnimation {
        val schema = SmdModelAnimationFileLoader.load(location)
        val animationFrames = mutableListOf<SmdModelAnimationFrame>()
        for (frame in schema.frames) {
            animationFrames.add(SmdModelAnimationFrame(frame.transformations, model.skeleton))
        }
        return SmdModelAnimation(animationFrames)
    }

}