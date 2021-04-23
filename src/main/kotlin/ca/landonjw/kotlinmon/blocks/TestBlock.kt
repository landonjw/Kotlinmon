package ca.landonjw.kotlinmon.blocks

import ca.landonjw.kotlinmon.Kotlinmon
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class TestBlock : Block(
    Properties.create(Material.IRON)
        .sound(SoundType.METAL)
        .hardnessAndResistance(2.0f)
) {

    override fun onBlockPlacedBy(
        worldIn: World,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        stack: ItemStack
    ) {
        Kotlinmon.LOGGER.info("Test block placed!")
    }

    override fun getSlipperiness() = 5.0f

}