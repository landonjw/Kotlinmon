package ca.landonjw.kotlinmon.client.render.models.api

import ca.landonjw.kotlinmon.client.render.models.api.animations.ModelAnimation
import ca.landonjw.kotlinmon.client.render.models.api.skeleton.ModelSkeleton

interface Model {
    val skeleton: ModelSkeleton
    val animations: Map<String, ModelAnimation>
    val currentAnimation: ModelAnimation?

    fun addAnimation(token: String, animation: ModelAnimation)
    fun setAnimation(animation: String?)
    fun tPose()
}