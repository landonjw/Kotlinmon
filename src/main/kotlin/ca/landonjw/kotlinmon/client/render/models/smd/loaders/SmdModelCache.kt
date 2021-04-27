package ca.landonjw.kotlinmon.client.render.models.smd.loaders

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.animations.SmdModelAnimation
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.Material
import net.minecraft.util.ResourceLocation

object SmdCache {

    private val modelCache: MutableMap<ResourceLocation, SmdModel> = mutableMapOf()
    private val animationCache: MutableMap<ResourceLocation, SmdModelAnimation> = mutableMapOf()

    fun getModel(location: ResourceLocation, texture: ResourceLocation): SmdModel {
        val model = modelCache[location] ?: SmdModelLoader.load(location, texture)
        if (modelCache[location] == null) modelCache[location] = model

//        val model = SmdModelLoader.load(location, texture)
//        modelCache[location] = model

        model.mesh.texture = Material(texture)
        return model
    }

    fun getModelAnimation(location: ResourceLocation, model: SmdModel): SmdModelAnimation {
        val animation = animationCache[location] ?: SmdModelAnimationLoader.load(location, model)
        if (animationCache[location] == null) animationCache[location] = animation

//        val animation = SmdModelAnimationLoader.load(location, model)
//        animationCache[location] = animation

        return animation
    }

}