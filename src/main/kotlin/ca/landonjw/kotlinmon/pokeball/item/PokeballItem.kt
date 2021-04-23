package ca.landonjw.kotlinmon.pokeball.item

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World

class PokeballItem : Item(
    Properties().group(ItemGroup.COMBAT)
) {

    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

}