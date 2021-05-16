package ca.landonjw.kotlinmon.common.pokemon.entity

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
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

class PokemonEntity(
    type: EntityType<out PokemonEntity>, world: World
) : TameableEntity(type, world) {

    companion object {
        fun prepareAttributes(): AttributeModifierMap {
            return registerAttributes()
                .createMutableAttribute(Attributes.FOLLOW_RANGE)
                .create()
        }

        val dwSpecies: DataParameter<String> = EntityDataManager.createKey(PokemonEntity::class.java, DataSerializers.STRING)
        val dwForm: DataParameter<Int> = EntityDataManager.createKey(PokemonEntity::class.java, DataSerializers.VARINT)
        val dwTexture: DataParameter<String> = EntityDataManager.createKey(PokemonEntity::class.java, DataSerializers.STRING)
    }

    val clientComponent = PokemonEntityClient(dataManager)

    lateinit var pokemon: Pokemon
        private set

    init {
        dataManager.register(dwSpecies, "")
        dataManager.register(dwForm, 0)
        dataManager.register(dwTexture, "")
    }

    fun setPokemon(pokemon: Pokemon) {
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

}