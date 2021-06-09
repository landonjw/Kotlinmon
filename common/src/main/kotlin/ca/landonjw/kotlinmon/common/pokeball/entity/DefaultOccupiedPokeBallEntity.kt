package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import ca.landonjw.kotlinmon.common.EntityRegistry
import net.minecraft.entity.EntityType
import net.minecraft.network.IPacket
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class DefaultOccupiedPokeBallEntity : OccupiedPokeBallEntity, DefaultPokeBallEntity {

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