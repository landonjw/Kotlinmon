package ca.landonjw.kotlinmon.common.pokeball.item

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRepository
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.world.World
import org.kodein.di.instance
import java.util.concurrent.Callable

class PokeBallItem(
    private val pokeBallRepository: PokeBallRepository,
    private val itemRenderer: ItemStackTileEntityRenderer,
) : Item(Properties().setISTER { Callable{ itemRenderer } }) {

    private val pokeBallFactory: PokeBallFactory by Kotlinmon.DI.instance()

    override fun getDisplayName(stack: ItemStack): ITextComponent {
        val name = getPokeBall(stack)?.name ?: "Poke Ball"
        return StringTextComponent(name)
    }

    fun getPokeBall(stack: ItemStack): PokeBall? {
        val type = stack.getOrCreateChildTag("PokeBall").getString("Type")
        return pokeBallRepository[type]
    }

    override fun onItemRightClick(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        if (!world.isRemote) {
            // Get the type of poke ball the player is holding.
            val itemInHand = player.getHeldItem(hand)
            val pokeBall = getPokeBall(itemInHand) ?: ProvidedPokeBall.PokeBall

            // Create the entity
            val pokeBallEntity = pokeBallFactory.createEntity(pokeBall, world)
            // Set it's motion to fly forwards from the player.
            pokeBallEntity.asMinecraftEntity().apply {
                setPosition(player.posX, player.posY, player.posZ)
                setDirectionAndMovement(player, player.rotationPitch - 7, player.rotationYawHead, 0.0f, 1.5f, 1.0f)
            }
            // Spawn it in the world.
            world.addEntity(pokeBallEntity.asMinecraftEntity())
        }
        return ActionResult.resultPass(player.getHeldItem(hand))
    }

}