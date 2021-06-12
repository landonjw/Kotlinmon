package ca.landonjw.kotlinmon.common.pokemon.entity

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultOccupiedPokeBallEntity
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
        this.clientComponent = clientComponentFactory(this.dataManager)
        if (pokemon != null) setPokemon(pokemon)
    }

    private fun registerDefaultDataParams() {
        dataManager.register(dwSpecies, "")
        dataManager.register(dwForm, 0)
        dataManager.register(dwTexture, "")
    }

    override fun asMinecraftEntity() = this

    private fun setPokemon(pokemon: Pokemon) {
        this.pokemon = pokemon
        dataManager.set(dwSpecies, pokemon.species.name)
        dataManager.set(dwForm, getPokemonFormOrdinal(pokemon.species, pokemon.form))
        dataManager.set(dwTexture, pokemon.texture ?: "")
    }

    private fun getPokemonFormOrdinal(species: PokemonSpecies, form: PokemonForm): Int = when (form) {
        species.defaultForm -> 0
        in species.alternativeForms -> species.alternativeForms.indexOf(form) + 1
        else -> throw IllegalArgumentException("form not apart of given species")
    }

    override fun getSize(poseIn: Pose): EntitySize = super.getSize(poseIn).scale(2f, 1f)

    override fun createChild(world: ServerWorld, mate: AgeableEntity): AgeableEntity? = null

    override fun createSpawnPacket() = NetworkHooks.getEntitySpawningPacket(this)

    companion object {
        fun prepareAttributes(): AttributeModifierMap {
            return registerAttributes()
                .createMutableAttribute(Attributes.FOLLOW_RANGE) // TODO: Probably not needed?
                .create()
        }

        val dwSpecies: DataParameter<String> = EntityDataManager.createKey(DefaultPokemonEntity::class.java, DataSerializers.STRING)
        val dwForm: DataParameter<Int> = EntityDataManager.createKey(DefaultPokemonEntity::class.java, DataSerializers.VARINT)
        val dwTexture: DataParameter<String> = EntityDataManager.createKey(DefaultPokemonEntity::class.java, DataSerializers.STRING)
    }

}