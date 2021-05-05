package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.common.pokemon.data.species.PokemonForm
import ca.landonjw.kotlinmon.common.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.common.pokemon.data.species.SpeciesRegistry
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

class PokemonEntity(type: EntityType<out PokemonEntity>, world: World) : TameableEntity(type, world) {

    companion object {
        fun prepareAttributes(): AttributeModifierMap {
            return registerAttributes()
                .createMutableAttribute(Attributes.FOLLOW_RANGE)
                .create()
        }

        val dwSpecies: DataParameter<String> = EntityDataManager.createKey(PokemonEntity::class.java, DataSerializers.STRING)
        val dwForm: DataParameter<String> = EntityDataManager.createKey(PokemonEntity::class.java, DataSerializers.STRING)
    }

    var species: PokemonSpecies
        get() = SpeciesRegistry[dataManager.get(dwSpecies)]!!
        set(value) {
            dataManager.set(dwSpecies, value.name)
        }

    var form: PokemonForm
        get() = species.forms.single { it.name == dataManager.get(dwForm) }
        set(value) {
            dataManager.set(dwForm, value.name)
        }

    init {
        val defaultSpecies = SpeciesRegistry.species.first()
        val defaultForm = defaultSpecies.forms.first()

        dataManager.register(dwSpecies, defaultSpecies.name)
        dataManager.register(dwForm, defaultForm.name)
    }

    override fun getSize(poseIn: Pose): EntitySize = super.getSize(poseIn).scale(2f, 1f)

    override fun createChild(world: ServerWorld, mate: AgeableEntity): AgeableEntity? = null

    override fun createSpawnPacket() = NetworkHooks.getEntitySpawningPacket(this)

}