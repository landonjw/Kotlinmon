package ca.landonjw.kotlinmon.client.render.models.smd.renderer

import ca.landonjw.kotlinmon.api.data.Key
import ca.landonjw.kotlinmon.util.math.geometry.Axis
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import net.minecraft.util.math.vector.Vector3f

/**
 * Defines a property used to modify the behaviour of the [SmdModelRenderer].
 *
 * @author landonjw
 */
interface SmdRenderProperty<T>: Key<T>

/**
 * Defines a global scaling transformation for a model.
 *
 * @property value the scaling value on each axis
 *
 * @author landonjw
 */
class Scale : SmdRenderProperty<Vector3f> {
    override val token = "scale"
    override val value: Vector3f

    constructor(scalars: Vector3f) {
        value = scalars
    }

    constructor(scalar: Float, axis: Axis) {
        value = Vector3f(axis.x * scalar, axis.y * scalar, axis.z * scalar)
    }
}

/**
 * Defines a global rotation transformation for a model.
 *
 * @property value the rotation angle for each axis in radians
 *
 * @author landonjw
 */
class RotationOffset : SmdRenderProperty<Vector3f> {
    override val token = "rotation"
    override val value: Vector3f

    constructor(angles: Vector3f) {
        value = angles
    }

    constructor(angle: Float, axis: Axis) {
        value = Vector3f(axis.x * angle, axis.y * angle, axis.z * angle)
    }
}

/**
 * Defines a global translation for a model.
 *
 * @property value the amount fo translate on each axis
 *
 * @author landonjw
 */
class PositionOffset(offset: GeometricPoint) : SmdRenderProperty<GeometricPoint> {
    override val token = "position"
    override val value: GeometricPoint = offset
}

/**
 * Defines a range to randomly rotate each vertex for a model.
 * This produces a 'shakey' or 'glitchy' effect.
 *
 * For example, a value of [0, 0, 1] will make each vertex rotate
 * between 0-1 radian on the z axis.
 *
 * @property value the amount of noise for each axis in radians
 */
class GlitchNoise : SmdRenderProperty<Vector3f> {
    override val token = "shake"
    override val value: Vector3f

    constructor(noise: Vector3f) {
        value = noise
    }

    constructor(noise: Float, axis: Axis) {
        value = Vector3f(axis.x * noise, axis.y * noise, axis.z * noise)
    }
}