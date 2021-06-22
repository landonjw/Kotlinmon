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

    override fun getName(stack: ItemStack): ITextComponent {
        val name = getPokeBall(stack)?.name ?: "Poke Ball"
        return StringTextComponent(name)
    }

    fun getPokeBall(stack: ItemStack): PokeBall? {
        val type = stack.getOrCreateTagElement("PokeBall").getString("Type")
        return pokeBallRepository[type]
    }

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        if (!world.isClientSide) {
            // Get the type of poke ball the player is holding.
            val itemInHand = player.getItemInHand(hand)
            val pokeBall = getPokeBall(itemInHand) ?: ProvidedPokeBall.PokeBall

            // Create the entity
            val pokeBallEntity = pokeBallFactory.createEntity(pokeBall, world)
            // Set it's motion to fly forwards from the player.
            pokeBallEntity.asMinecraftEntity().apply {
                setPos(player.x, player.y, player.z)
                shootFromRotation(player, player.xRot - 7, player.yRot, 0.0f, 1.5f, 1.0f)
            }
            // Spawn it in the world.
            world.addFreshEntity(pokeBallEntity.asMinecraftEntity())
        }
        return ActionResult.pass(player.getItemInHand(hand))
    }

}