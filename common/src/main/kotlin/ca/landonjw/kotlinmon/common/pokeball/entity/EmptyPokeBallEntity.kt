package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntity
import net.minecraft.entity.EntityType
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

/**
 * A poke ball that does not have a [Pokemon] inside of it.
 *
 * @author landonjw
 */
class EmptyPokeBallEntity(type: EntityType<out EmptyPokeBallEntity>, world: World): PokeBallEntity(type, world) {

    private val pokeBallFactory: PokeBallFactory by KotlinmonDI.inject()

    /**
     * When the poke ball comes hits a block, this will kill the entity and
     * spawn a new item entity in it's place for the given poke ball type.
     */
    override fun onBlockImpact() {
        setDead()
        val pokeBallItem = pokeBallFactory.createItem(type)
        entityDropItem(pokeBallItem)
    }

    override fun onPokemonImpact(pokemon: PokemonEntity) {
        TODO("Add capture hooks")
    }

    override fun createSpawnPacket() = NetworkHooks.getEntitySpawningPacket(this)

}