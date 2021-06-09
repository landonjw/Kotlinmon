package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.entity.PokeBallEntity
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.projectile.ThrowableEntity
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World

abstract class DefaultPokeBallEntity : PokeBallEntity, ThrowableEntity {

    override var pokeBallType: PokeBall by PokeBallTypeController.create(this)
    override var orientation: Vector3d by OrientationController.create(this)

    /**
     * Used for Minecraft's entity type registration. **Do not use!**
     */
    constructor(type: EntityType<out DefaultPokeBallEntity>, world: World) : super(type, world)

    override fun asMinecraftEntity(): ThrowableEntity = this

    override fun tick() {
        // Rotate the ball every tick to make it spin
        this.orientation = this.orientation.add(4.0, 4.0, 0.0)
        super.tick()
    }

    override fun registerData() { }

    override fun onImpact(result: RayTraceResult) {
        if (!world.isRemote) {
            when (result.type) {
                RayTraceResult.Type.BLOCK -> onBlockImpact()
                RayTraceResult.Type.ENTITY -> {
                    if (entity is PokemonEntity) {
                        onPokemonImpact(entity as PokemonEntity)
                    }
                }
            }
        }
        super.onImpact(result)
    }

    /**
     * Event for when a poke ball collides with a block.
     */
    protected abstract fun onBlockImpact()

    /**
     * Event for when a poke ball collides with a [PokemonEntity].
     *
     * @param pokemon The pokemon entity collided with.
     */
    protected abstract fun onPokemonImpact(pokemon: PokemonEntity)

}