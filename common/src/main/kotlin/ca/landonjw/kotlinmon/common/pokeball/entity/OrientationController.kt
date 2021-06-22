package ca.landonjw.kotlinmon.common.pokeball.entity

import net.minecraft.entity.Entity
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.util.math.Rotations
import net.minecraft.util.math.vector.Vector3d
import java.util.*
import kotlin.reflect.KProperty

open class OrientationController constructor(
    private val dataManager: EntityDataManager,
    private val clazz: Class<out Entity>
) {

    init {
        this.dataManager.define(getOrCreateDataParam(), randomRotation())
    }

    private fun randomRotation(): Rotations {
        val random = Random()
        val x = random.nextFloat() * 360
        val y = random.nextFloat() * 360
        val z = random.nextFloat() * 360
        return Rotations(x, y, z)
    }

    private fun getOrCreateDataParam(): DataParameter<Rotations> {
        if (orientationParams[clazz] == null) {
            orientationParams[clazz] = EntityDataManager.defineId(clazz, DataSerializers.ROTATIONS)
        }
        return orientationParams[clazz]!!
    }

    private fun Rotations.toVector3d(): Vector3d = Vector3d(this.x.toDouble(), this.y.toDouble(), this.z.toDouble())

    private fun Vector3d.toRotations(): Rotations = Rotations(this.x.toFloat(), this.y.toFloat(), this.z.toFloat())

    fun get(): Vector3d {
        return dataManager.get(getOrCreateDataParam()).toVector3d()
    }

    fun set(value: Vector3d) {
        dataManager.set(getOrCreateDataParam(), value.toRotations())
    }

    companion object {
        private val orientationParams: MutableMap<Class<*>, DataParameter<Rotations>> = mutableMapOf()
    }

}