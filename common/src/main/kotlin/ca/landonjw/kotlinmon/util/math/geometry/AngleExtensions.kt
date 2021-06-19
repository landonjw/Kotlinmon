package ca.landonjw.kotlinmon.util.math.geometry

private const val RADIAN_IN_DEGREES = 57.2958f

fun Float.toRadians(): Float = this / RADIAN_IN_DEGREES
fun Float.toDegrees(): Float = this * RADIAN_IN_DEGREES