package ca.landonjw.kotlinmon.api.pokeball.entity

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import net.minecraft.entity.projectile.ThrowableEntity
import net.minecraft.util.math.vector.Vector3d

/**
 * A living instance of a [PokeBall] in a world.
 *
 * @author landonjw
 */
interface PokeBallEntity {

    /** The type of poke ball the entity represents. */
    val pokeBallType: PokeBall

    /** The orientation of the poke ball entity. */
    val orientation: Vector3d

    /** Gets the underlying Minecraft entity instance of the poke ball entity. */
    fun asMinecraftEntity(): ThrowableEntity

}