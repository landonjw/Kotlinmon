package ca.landonjw.kotlinmon.client.render.models.api.animations

import ca.landonjw.kotlinmon.client.render.models.api.Model

interface ModelAnimation {
    val model: Model
    val totalFrames: Int

    fun apply()
}