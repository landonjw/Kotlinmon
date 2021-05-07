package ca.landonjw.kotlinmon.api.pokeball

import ca.landonjw.kotlinmon.common.pokeball.entity.PokeBallEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.World

interface PokeBallFactory {

    fun createItem(pokeBall: PokeBall, amount: Int = 1): ItemStack

    fun createEntity(pokeBall: PokeBall, world: World): PokeBallEntity

}