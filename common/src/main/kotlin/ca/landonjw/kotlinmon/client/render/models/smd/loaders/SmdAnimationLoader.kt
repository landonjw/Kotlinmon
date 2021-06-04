package ca.landonjw.kotlinmon.client.render.models.smd.loaders

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimationFrame
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.files.SmdModelAnimationFileLoader
import net.minecraft.util.ResourceLocation

/**
 * Loads a [SmdModelAnimation] from a `.smd` file.
 * This loading is done synchronously by default, and should often be handled otherwise
 * in any implementations that use it.
 *
 * @author landonjw
 */
object SmdAnimationLoader {

    /**
     * Loads a [SmdModelAnimation] from a `.smd` file.
     *
     * @param location the location to load `.smd` file from
     * @return an animation from the location
     */
    fun load(location: ResourceLocation, model: SmdModel): SmdModelAnimation {
        val schema = SmdModelAnimationFileLoader.load(location)
        val animationFrames = mutableListOf<SmdModelAnimationFrame>()
        for (frame in schema.frames) {
            animationFrames.add(SmdModelAnimationFrame(frame.transformations, model.skeleton))
        }
        return SmdModelAnimation(animationFrames)
    }

}