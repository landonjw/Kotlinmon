package ca.landonjw.kotlinmon.common.pokeball

import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.common.EntityRegistry
import ca.landonjw.kotlinmon.common.ItemRegistry
import ca.landonjw.kotlinmon.common.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.PokeBallEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class DefaultPokeBallFactory: PokeBallFactory {

    override fun createItem(pokeBall: PokeBall, amount: Int): ItemStack {
        return ItemStack(ItemRegistry.POKEBALL.get(), amount).apply {
            getOrCreateChildTag("PokeBall").putString("Type", pokeBall.name)
        }
    }

    override fun createEntity(pokeBall: PokeBall, world: World): EmptyPokeBallEntity {
        return EmptyPokeBallEntity(EntityRegistry.EMPTY_POKEBALL.get(), world).apply {
            type = pokeBall
        }
    }

    override fun createEntity(pokeBall: PokeBall, world: World, occupier: Pokemon): OccupiedPokeBallEntity {
        return OccupiedPokeBallEntity(EntityRegistry.OCCUPIED_POKEBALL.get(), world).apply {
            pokemon = occupier
            type = pokeBall
        }
    }

}