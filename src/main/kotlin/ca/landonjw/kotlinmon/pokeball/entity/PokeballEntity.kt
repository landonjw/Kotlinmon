package ca.landonjw.kotlinmon.pokeball.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.projectile.ThrowableEntity
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

//open class PokeballEntity(
//    type: EntityType<out PokeballEntity>,
//    world: World
//) : ThrowableEntity(type, world) {
//    override fun registerData() { }
//
//    override fun getRenderBoundingBox() = AxisAlignedBB(position, position.add(3, 3, 3)) // TODO: This needs work
//
//    override fun createSpawnPacket() = NetworkHooks.getEntitySpawningPacket(this)
//}