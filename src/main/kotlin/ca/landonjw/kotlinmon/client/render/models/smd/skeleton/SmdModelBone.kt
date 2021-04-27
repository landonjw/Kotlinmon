package ca.landonjw.kotlinmon.client.render.models.smd.skeleton

import ca.landonjw.kotlinmon.client.render.models.api.skeleton.ModelBone
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.SmdVertex
import ca.landonjw.kotlinmon.client.render.models.smd.mesh.SmdMesh
import ca.landonjw.kotlinmon.util.collections.immutableArrayOf
import ca.landonjw.kotlinmon.util.math.geometry.Axis
import ca.landonjw.kotlinmon.util.math.geometry.GeometricPoint
import ca.landonjw.kotlinmon.util.math.geometry.TransformationBuilder
import ca.landonjw.kotlinmon.util.math.geometry.TransformationMatrix
import net.minecraft.util.math.vector.Matrix4f
import net.minecraft.util.math.vector.Vector3f

class SmdModelBone(
    val id: Int,
    val name: String,
    override val parent: SmdModelBone?,
    children: List<SmdModelBone> = listOf(),
    jointLocation: GeometricPoint = GeometricPoint(),
    var mesh: SmdMesh? = null
) : ModelBone {

    private val _children: MutableList<SmdModelBone> = children.toMutableList()
    override val children: List<SmdModelBone>
        get() = _children.toList()

    var jointLocation = jointLocation
        private set

    var lastTranslation: GeometricPoint? = null
    var lastRotation: Vector3f? = null

    var orientation: TransformationMatrix = TransformationMatrix.identityMatrix

    var localRotationX = jointLocation + GeometricPoint(0.5f, 0f, 0f)
    var localRotationY = jointLocation + GeometricPoint(0f, 0.5f, 0f)
    var localRotationZ = jointLocation + GeometricPoint(0f, 0f, 0.5f)

    fun addChildBone(bone: SmdModelBone) {
        _children.add(bone)
    }

    fun applyLastTransformation() {
        move(lastTranslation, lastRotation)
    }

    override fun move(translation: GeometricPoint?, rotation: Vector3f?) {
        if (rotation != null) {
            if (lastRotation != null) {
                val delta = Vector3f(rotation.x - lastRotation!!.x, rotation.y - lastRotation!!.y, rotation.z - lastRotation!!.z)
                rotate(delta)
            }
            else {
                rotate(rotation)
            }
        }

        if (translation != null) {
            if (lastTranslation != null) {
                val delta = translation + (lastTranslation!! * -1f)
                translate(delta)
            }
            else {
                translate(translation)
            }
        }
        lastRotation = rotation
        lastTranslation = translation
    }

    private fun translate(translation: GeometricPoint) {
        val transformation = TransformationMatrix.translate(translation)
        transform(transformation)
    }

    private fun rotate(rotation: Vector3f) {
        val transformation = TransformationMatrix.rotateAroundPoint(jointLocation, rotation)
//        val translateToOrigin = TransformationMatrix.translate(jointLocation * -1f)
//        val orientLocally = orientation
//        val rotate = TransformationMatrix.rotate(rotation)
//        val translateFromOrigin = TransformationMatrix.translate(jointLocation)
//        val transformation = translateFromOrigin * rotate * orientLocally * translateToOrigin
//        val rotation = TransformationMatrix.rotate(rotation)
//        transformOrientation(rotation)
//        orientation = orientation * rotation
        transform(transformation)
    }

    private fun rotateAroundParent(rotation: Vector3f) {
        if (parent == null) return
        val transformation = TransformationMatrix.rotateAroundPoint(parent.jointLocation, rotation)
        transform(transformation)
    }

    private fun transform(transformation: TransformationMatrix) {
        jointLocation = transformation * jointLocation
        localRotationX = transformation * localRotationX
        localRotationY = transformation * localRotationY
        localRotationZ = transformation * localRotationZ
        mesh?.transform(this, transformation)
        children.forEach { it.transform(transformation) }
    }

    private fun transformOrientation(rotation: TransformationMatrix) {
        orientation = orientation * rotation
        children.forEach { it.transformOrientation(rotation) }
    }

    override fun reset() {
        jointLocation = GeometricPoint()
        orientation = TransformationMatrix.identityMatrix
        localRotationX = jointLocation + GeometricPoint(0.5f, 0f, 0f)
        localRotationY = jointLocation + GeometricPoint(0f, 0.5f, 0f)
        localRotationZ = jointLocation + GeometricPoint(0f, 0f, 0.5f)
        lastTranslation = null
        lastRotation = null
    }

}