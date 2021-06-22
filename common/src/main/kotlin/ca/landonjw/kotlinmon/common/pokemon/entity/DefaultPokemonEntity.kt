package ca.landonjw.kotlinmon.common.pokemon.entity

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntitySize
import net.minecraft.entity.EntityType
import net.minecraft.entity.Pose
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.fml.network.NetworkHooks

class DefaultPokemonEntity : PokemonEntity, TameableEntity {

    val clientComponent: PokemonEntityClient

    override lateinit var pokemon: Pokemon
        private set

    /**
     * Primary constructor.
     *
     * @param type The type of the entity being registered. This will likely always be [DefaultPokemonEntity].
     * @param world The world the entity is being added to.
     * @param pokemon The pokemon that this entity represents. **This should always be non-null server-side!**.
     *                This is nullable to allow for client side to initialize the entity without failing exceptionally,
     *                as the pokemon only exists on the server side.
     * @param clientComponentFactory A factory for creating the controller for the client representation.
     */
    constructor(
        type: EntityType<out DefaultPokemonEntity>,
        world: World,
        pokemon: Pokemon?,
        clientComponentFactory: (EntityDataManager) -> PokemonEntityClient
    ) : super(type, world) {
        registerDefaultDataParams()
        this.clientComponent = clientComponentFactory(this.entityData)
        if (pokemon != null) setPokemon(pokemon)
    }

    private fun registerDefaultDataParams() {
        entityData.define(dwSpecies, "")
        entityData.define(dwForm, 0)
        entityData.define(dwTexture, "")
    }

    override fun asMinecraftEntity() = this

    private fun setPokemon(pokemon: Pokemon) {
        this.pokemon = pokemon
        entityData.set(dwSpecies, pokemon.species.name)
        entityData.set(dwForm, getPokemonFormOrdinal(pokemon.species, pokemon.form))
        entityData.set(dwTexture, pokemon.texture ?: "")
    }

    private fun getPokemonFormOrdinal(species: PokemonSpecies, form: PokemonForm): Int = when (form) {
        species.defaultForm -> 0
        in species.alternativeForms -> species.alternativeForms.indexOf(form) + 1
        else -> throw IllegalArgumentException("form not apart of given species")
    }

    override fun getDimensions(poseIn: Pose): EntitySize = super.getDimensions(poseIn).scale(2f, 1f)

    override fun getBreedOffspring(world: ServerWorld, mate: AgeableEntity): AgeableEntity? = null

    override fun getAddEntityPacket() = NetworkHooks.getEntitySpawningPacket(this)

    companion object {
        fun prepareAttributes(): AttributeModifierMap {
            return createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE) // TODO: Probably not needed?
                .build()
        }

        val dwSpecies: DataParameter<String> = EntityDataManager.defineId(DefaultPokemonEntity::class.java, DataSerializers.STRING)
        val dwForm: DataParameter<Int> = EntityDataManager.defineId(DefaultPokemonEntity::class.java, DataSerializers.INT)
        val dwTexture: DataParameter<String> = EntityDataManager.defineId(DefaultPokemonEntity::class.java, DataSerializers.STRING)
    }

}