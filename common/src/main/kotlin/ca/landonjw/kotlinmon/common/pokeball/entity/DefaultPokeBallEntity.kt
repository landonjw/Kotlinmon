package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.entity.PokeBallEntity
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import ca.landonjw.kotlinmon.common.EntityRegistry
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.projectile.ThrowableEntity
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World

abstract class DefaultPokeBallEntity : PokeBallEntity, ThrowableEntity {

    private val pokeBallController: PokeBallTypeController
    private val orientationController: OrientationController

    override var pokeBallType: PokeBall
        get() = pokeBallController.get()
        set(value) = pokeBallController.set(value)

    override var orientation: Vector3d
        get() = orientationController.get()
        set(value) = orientationController.set(value)

    constructor(
        type: EntityType<out DefaultPokeBallEntity>,
        world: World,
        pokeBallType: PokeBall,
        pokeBallControllerFactory: (Entity) -> PokeBallTypeController,
        orientationControllerFactory: (Entity) -> OrientationController
    ) : super(type, world) {
        this.pokeBallController = pokeBallControllerFactory(this)
        this.orientationController = orientationControllerFactory(this)

        this.pokeBallType = pokeBallType
    }

    override fun asMinecraftEntity(): ThrowableEntity = this

    override fun tick() {
        // Rotate the ball every tick to make it spin
        this.orientation = this.orientation.add(8.0, 4.0, 0.0)
        super.tick()
    }

    override fun onHit(result: RayTraceResult) {
        if (!level.isClientSide) {
            when (result.type) {
                RayTraceResult.Type.BLOCK -> onBlockImpact()
                RayTraceResult.Type.ENTITY -> {
                    if (entity is PokemonEntity) {
                        onPokemonImpact(entity as PokemonEntity)
                    }
                }
            }
        }
        super.onHit(result)
    }

    override fun defineSynchedData() { }

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