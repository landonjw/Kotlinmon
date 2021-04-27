package ca.landonjw.kotlinmon.client.render.models.test

import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint

class TestMeshVertex(
    val baseLocation: GeometricPoint,
    val baseNormal: GeometricPoint,
    val links: Map<Int, Float>,
    val u: Float,
    val v: Float
) {

    val location: GeometricPoint
        get() = transformedLocation ?: baseLocation

    val normal: GeometricPoint
        get() = transformedNormal ?: baseNormal

    var transformedLocation: GeometricPoint? = null
    var transformedNormal: GeometricPoint? = null

    fun transform(bone: TestBone) {
        val weight = links[bone.id] ?: return
        val transformation = bone.transformation ?: return

        if (transformedLocation == null) transformedLocation = GeometricPoint()
        if (transformedNormal == null) transformedNormal = GeometricPoint()

        val scaledLocation = transformation * baseLocation * weight
        val scaledNormal = transformation * baseNormal * weight

        transformedLocation = transformedLocation!! + GeometricPoint(scaledLocation.x, scaledLocation.y, scaledLocation.z)
        transformedNormal = transformedNormal!! + GeometricPoint(scaledNormal.x, scaledNormal.y, scaledLocation.z)
    }

    fun reset() {
        transformedLocation = null
        transformedNormal = null
    }

}