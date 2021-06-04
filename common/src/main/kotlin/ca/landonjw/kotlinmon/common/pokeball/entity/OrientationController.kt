package ca.landonjw.kotlinmon.common.pokeball.entity

import net.minecraft.util.math.vector.Vector3f
import java.util.*

class OrientationController {

    private val random = Random()

    var pitch: Float = randomRadian()
    var yaw: Float = randomRadian()
    var roll: Float = randomRadian()

    val orientation: Vector3f
        get() = Vector3f(pitch, yaw, roll)

    fun rotate(pitch: Float = 0f, yaw: Float = 0f, roll: Float = 0f) {
        this.pitch += pitch
        this.yaw += yaw
        this.roll += roll
    }

    private fun randomRadian(): Float = random.nextFloat() * 6.28319f

}