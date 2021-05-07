package ca.landonjw.kotlinmon.client.render.pokeball

import ca.landonjw.kotlinmon.common.pokeball.item.PokeBallItem
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.ItemRenderer as ItemEntityRenderer
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.item.ItemStack

/**
 * Replaces the standard item renderer in Minecraft.
 *
 * This is done to avoid unnecessary rendering of potentially high-poly models being rendered
 * numerous times for item stacks, thus dropping frames.
 *
 * This reduces the strain by only rendering one model per stack, rather than the standard 5 for a full stack.
 * Right now this just affects poke balls, but more items may be included at later dates.
 *
 * @author landonjw
 */
class CompressingItemRenderer(
    manager: EntityRendererManager,
    itemRenderer: ItemRenderer
): ItemEntityRenderer(manager, itemRenderer) {

    override fun getModelCount(stack: ItemStack): Int {
        return if (stack.item is PokeBallItem) 1 else super.getModelCount(stack)
    }

}