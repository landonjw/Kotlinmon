package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import ca.landonjw.kotlinmon.common.EntityRegistry
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.network.IPacket
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class DefaultEmptyPokeBallEntity : EmptyPokeBallEntity, DefaultPokeBallEntity {

    private lateinit var pokeBallFactory: PokeBallFactory

    /**
     * Used for Minecraft's entity type registration. **Do not use!**
     */
    constructor(type: EntityType<out DefaultEmptyPokeBallEntity>, world: World) : super(type, world)

    constructor(
        world: World,
        pokeBallType: PokeBall,
        pokeBallControllerFactory: (Entity) -> PokeBallTypeController,
        orientationControllerFactory: (Entity) -> OrientationController,
        entityRegistry: EntityRegistry,
        pokeBallFactory: PokeBallFactory
    ) : super(entityRegistry.EMPTY_POKEBALL.get(), world, pokeBallType, pokeBallControllerFactory, orientationControllerFactory) {
        this.pokeBallFactory = pokeBallFactory
    }

    override fun onBlockImpact() {
        // Remove the poke ball entity.
        setDead()
        // Spawn a poke ball item where the impact took place.
        val pokeBallItem = pokeBallFactory.createItem(pokeBallType)
        entityDropItem(pokeBallItem)
    }

    override fun onPokemonImpact(pokemon: PokemonEntity) {
        TODO("Add capture hooks")
    }

    override fun createSpawnPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

}