package ca.landonjw.kotlinmon.common.pokeball

import ca.landonjw.kotlinmon.common.init.EntityRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World

class ItemPokeball : Item(Properties()) {

    override fun onItemRightClick(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        if (!world.isRemote) {
            val pokeball = PokeballEntity(EntityRegistry.POKEBALL.get(), world)
            pokeball.setPosition(player.posX, player.posY, player.posZ)
            pokeball.setDirectionAndMovement(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F)
            world.addEntity(pokeball)
        }
        return super.onItemRightClick(world, player, hand)
    }

}