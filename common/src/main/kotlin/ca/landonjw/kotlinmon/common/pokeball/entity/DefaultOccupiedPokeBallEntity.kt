package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import ca.landonjw.kotlinmon.common.EntityRegistry
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.network.IPacket
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class DefaultOccupiedPokeBallEntity : OccupiedPokeBallEntity, DefaultPokeBallEntity {

    override lateinit var occupant: Pokemon
        private set

    private lateinit var pokemonFactory: PokemonFactory

    constructor(type: EntityType<out DefaultOccupiedPokeBallEntity>, world: World) : super(type, world)

    constructor(
        world: World,
        pokeBallType: PokeBall,
        occupant: Pokemon,
        entityRegistry: EntityRegistry,
        pokeBallControllerFactory: (Entity) -> PokeBallTypeController,
        orientationController: (Entity) -> OrientationController,
        pokemonFactory: PokemonFactory
    ) : super(entityRegistry.OCCUPIED_POKEBALL.get(), world, pokeBallType, pokeBallControllerFactory, orientationController) {
        this.occupant = occupant
        this.pokemonFactory = pokemonFactory
    }

    override fun onBlockImpact() {
        if (!world.isRemote) {
            // Remove the poke ball entity.
            setDead()

            // Create pokemon entity and spawn it where the poke ball collided.
            val pokemonEntity = pokemonFactory.createEntity(occupant, entityWorld).apply {
                setPosition(posX, posY, posZ)
            }
            entityWorld.addEntity(pokemonEntity.asMinecraftEntity())
        }
    }

    override fun onPokemonImpact(pokemon: PokemonEntity) {
        TODO("Add battle hooks")
    }

    override fun createSpawnPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

}