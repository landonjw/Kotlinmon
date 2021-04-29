package ca.landonjw.kotlinmon.client.render.models.smd.renderer

import ca.landonjw.kotlinmon.client.render.models.api.renderer.RenderProperty
import ca.landonjw.kotlinmon.util.math.geometry.Axis
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import net.minecraft.util.math.vector.Vector3f

class Scale : RenderProperty<Vector3f> {
    override val token = "scale"
    override val value: Vector3f

    constructor(scalars: Vector3f) {
        value = scalars
    }

    constructor(scalar: Float, axis: Axis) {
        value = Vector3f(axis.x * scalar, axis.y * scalar, axis.z * scalar)
    }
}

class RotationOffset : RenderProperty<Vector3f> {
    override val token = "rotation"
    override val value: Vector3f

    constructor(angles: Vector3f) {
        value = angles
    }

    constructor(angle: Float, axis: Axis) {
        value = Vector3f(axis.x * angle, axis.y * angle, axis.z * angle)
    }
}

class PositionOffset(offset: GeometricPoint) : RenderProperty<GeometricPoint> {
    override val token = "position"
    override val value: GeometricPoint = offset
}

class GlitchNoise : RenderProperty<Vector3f> {
    override val token = "shake"
    override val value: Vector3f

    constructor(noise: Vector3f) {
        value = noise
    }

    constructor(noise: Float, axis: Axis) {
        value = Vector3f(axis.x * noise, axis.y * noise, axis.z * noise)
    }
}