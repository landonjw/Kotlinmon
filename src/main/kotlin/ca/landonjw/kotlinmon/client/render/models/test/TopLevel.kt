package ca.landonjw.kotlinmon.client.render.models.test

import ca.landonjw.kotlinmon.Matrix4f
import ca.landonjw.kotlinmon.Vector3f

val X_AXIS = Vector3f(1.0f, 0.0f, 0.0f)
val Y_AXIS = Vector3f(0.0f, 1.0f, 0.0f)
val Z_AXIS = Vector3f(0.0f, 0.0f, 1.0f)

fun matrix4FromLocRot(xl: Float, yl: Float, zl: Float, xr: Float, yr: Float, zr: Float): Matrix4f {
    val loc = Vector3f(xl, yl, zl)
    val part1 = Matrix4f()
    part1.translate(loc)
    part1.rotate(zr, Z_AXIS)
    part1.rotate(yr, Y_AXIS)
    part1.rotate(xr, X_AXIS)
    return part1
}