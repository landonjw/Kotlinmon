package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.pokemon.entity.DefaultPokemonEntity
import net.minecraft.entity.EntityType
import net.minecraft.network.IPacket
import net.minecraft.util.math.EntityRayTraceResult
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

/**
 * A poke ball that currently has a [Pokemon] occupying it.
 *
 * @author landonjw
 */
class DefaultOccupiedPokeBallEntity : OccupiedPokeBallEntity, DefaultPokeBallEntity {

    /** The pokemon that is occupying the poke ball. */
    override lateinit var occupant: Pokemon
        private set

    private val pokemonFactory: PokemonFactory by KotlinmonDI.inject()

    constructor(type: EntityType<out DefaultOccupiedPokeBallEntity>, world: World) : super(type, world)

    constructor(
        world: World,
        occupant: Pokemon,
        pokeBallType: PokeBall? = null,
        orientation: Vector3d? = null
    ) : this(
        EntityRegistry.OCCUPIED_POKEBALL.get(),
        world
    ) {
        this.occupant = occupant
        pokeBallType?.let { this.pokeBallType = it }
        orientation?.let { this.orientation = it }
    }

    /**
     * Upon collision with a block, this will spawn an entity for the pokemon that is inside the poke ball.
     */
    override fun onBlockImpact() {
        if (!world.isRemote) {
            val pokemonEntity = pokemonFactory.createEntity(occupant, entityWorld).also {
                it.setPosition(posX, posY, posZ)
            }
            entityWorld.addEntity(pokemonEntity)
            setDead()
        }
    }

    override fun onPokemonImpact(pokemon: PokemonEntity) {
        TODO("Add battle hooks")
    }

    override fun createSpawnPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

}