package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
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

    /** This will be not be initialized on the client side! */
    override lateinit var occupant: Pokemon

    private val pokemonFactory: PokemonFactory

    /**
     * Primary constructor.
     *
     * @param type The type of the entity being registered. This will likely always be [DefaultOccupiedPokeBallEntity].
     * @param world The world the entity is being added to.
     * @param pokeBallType The type of poke ball this represents (ie. Poke Ball, Great Ball, Ultra Ball, etc.)
     * @param occupant The [Pokemon] currently occupying the poke ball. **This should always be non-null server-side!**.
     *                 This is nullable to allow for client side to initialize the entity without failing exceptionally,
     *                 as the occupant only ever exists on the server side.
     * @param pokeBallControllerFactory A factory for creating the controller for the poke ball type.
     * @param orientationController A factory for creating the controller for the poke ball's orientation.
     * @param pokemonFactory A factory for creating new pokemon instances.
     */
    constructor(
        type: EntityType<out DefaultOccupiedPokeBallEntity>,
        world: World,
        pokeBallType: PokeBall,
        occupant: Pokemon?,
        pokeBallControllerFactory: (Entity) -> PokeBallTypeController,
        orientationController: (Entity) -> OrientationController,
        pokemonFactory: PokemonFactory
    ) : super(type, world, pokeBallType, pokeBallControllerFactory, orientationController) {
        if (occupant != null) this.occupant = occupant
        this.pokemonFactory = pokemonFactory
    }

    override fun onBlockImpact() {
        // Check the impact is on the server side.
        if (!world.isRemote) {
            // Remove the poke ball entity.
            setDead()

            // Create pokemon entity and spawn it where the poke ball collided.
            val pokemonEntity = pokemonFactory.createEntity(occupant, entityWorld).apply {
                asMinecraftEntity().setPosition(posX, posY, posZ)
            }
            entityWorld.addEntity(pokemonEntity.asMinecraftEntity())
        }
    }

    override fun onPokemonImpact(pokemon: PokemonEntity) {
        TODO("Add battle hooks")
    }

    override fun createSpawnPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

}