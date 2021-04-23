package ca.landonjw.kotlinmon.client.render.models.smd.skeleton

import ca.landonjw.kotlinmon.client.render.models.api.skeleton.ModelBone
import net.minecraft.util.math.vector.Vector3f

class SmdModelBone(
    val id: Int,
    var baseLocation: Vector3f,
    var baseOrientation: Vector3f,
    override val parent: SmdModelBone?,
    children: List<SmdModelBone> = listOf()
) : ModelBone {

    private val _children: MutableList<SmdModelBone> = children.toMutableList()
    override val children: List<SmdModelBone>
        get() = _children.toList()

    private var _translation: Vector3f? = null
    val translation: Vector3f?
        get() = _translation?.copy()

    private var _rotation: Vector3f? = null
    val rotation: Vector3f?
        get() = _rotation?.copy()

    val location: Vector3f
        get() {
            val location = baseLocation.copy()
            if (_translation != null) location.add(_translation)
            return location
        }

    val orientation: Vector3f
        get() {
            val orientation = baseOrientation.copy()
            if (_rotation != null) orientation.add(_rotation)
            return orientation
        }

    fun addChildBone(bone: SmdModelBone) {
        _children.add(bone)
    }

    // TODO: Doesn't work
    override fun move(translation: Vector3f?, rotation: Vector3f?) {
        if (translation != null) translate(translation)
        if (rotation != null) rotate(rotation)
        children.forEach { it.adjustForParent() }
    }

    fun adjustForParent() {
        if (parent == null) return
        if (parent.translation != null) {
            if (_translation != null) {
                _translation?.add(parent.translation!!)
            }
            else {
                _translation = parent.translation
            }
        }

        if (parent.rotation != null) {
            if (_rotation != null) {
                _rotation?.add(parent.rotation!!)
            }
            else {
                _rotation = parent.rotation
            }
        }
    }

    override fun reset() {
        _translation = null
        _rotation = null
    }

    private fun translate(translation: Vector3f) {
        if (_translation != null) {
            _translation?.add(translation)
        }
        else {
            _translation = translation
        }
    }

    private fun rotate(rotation: Vector3f) {
        if (_rotation != null) {
            _rotation?.add(rotation)
        }
        else {
            _rotation = rotation
        }
    }

}