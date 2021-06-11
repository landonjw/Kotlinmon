package ca.landonjw.kotlinmon.common.pokeball

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.api.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.ItemRegistry
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultEmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultOccupiedPokeBallEntity
import net.minecraft.entity.EntityType
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class DefaultPokeBallFactory(
    private val itemRegistry: ItemRegistry,
    private val emptyBallFactory: (PokeBallFactoryParams) -> EmptyPokeBallEntity,
    private val occupiedBallFactory: (PokeBallFactoryParams) -> OccupiedPokeBallEntity
): PokeBallFactory {

    override fun createItem(pokeBall: PokeBall, amount: Int): ItemStack {
        return ItemStack(itemRegistry.POKEBALL.get(), amount).apply {
            getOrCreateChildTag("PokeBall").putString("Type", pokeBall.name)
        }
    }

    override fun createEntity(pokeBall: PokeBall, world: World): EmptyPokeBallEntity {
        val params = PokeBallFactoryParams(pokeBall, world)
        return emptyBallFactory(params)
    }

    override fun createEntity(pokeBall: PokeBall, world: World, occupant: Pokemon): OccupiedPokeBallEntity {
        val params = PokeBallFactoryParams(pokeBall, world, occupant)
        return occupiedBallFactory(params)
    }

}

data class PokeBallFactoryParams(
    val pokeBall: PokeBall,
    val world: World,
    val occupant: Pokemon? = null
)