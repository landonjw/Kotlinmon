package ca.landonjw.kotlinmon.tangrowth

import ca.landonjw.kotlinmon.Kotlinmon
import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.fml.network.NetworkHooks

class PokemonEntity(
    type: EntityType<out PokemonEntity>,
    world: World
) : TameableEntity(type, world) {

    val species: String // TODO: Just here for now. Remove later.
    var texture: ResourceLocation
    val rotation: Float

    val scale: Float = 1.0f

    init {
        species = getRandomSpecies()
        texture = ResourceLocation(Kotlinmon.MODID, "pokemon/textures/$species.png")
        rotation = listOf(0f, 0.62f, -0.62f).random()
    }

    fun getRandomSpecies() = listOf("slakoth", "arceus", "krabby", "bidoof", "dratini").random()

    override fun createChild(world: ServerWorld, mate: AgeableEntity): AgeableEntity? = null
    override fun createSpawnPacket() = NetworkHooks.getEntitySpawningPacket(this)
    override fun tick() { super.tick() }

    companion object {
        fun prepareAttributes(): AttributeModifierMap {
            return TameableEntity.registerAttributes()
                .createMutableAttribute(Attributes.FOLLOW_RANGE)
                .create()
        }
    }

}