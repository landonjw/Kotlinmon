package ca.landonjw.kotlinmon.common.pokeball

import ca.landonjw.kotlinmon.common.init.ItemRegistry
import ca.landonjw.kotlinmon.common.pokemon.PokemonEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.projectile.ThrowableEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.EntityRayTraceResult
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class PokeballEntity(type: EntityType<out PokeballEntity>, world: World): ThrowableEntity(type, world) {

    override fun registerData() { }

    override fun onImpact(result: RayTraceResult) {
        if (!world.isRemote) {
            when (result.type) {
                RayTraceResult.Type.BLOCK -> {
                    onBlockImpact()
                }
                RayTraceResult.Type.ENTITY -> {
                    val entity = (result as EntityRayTraceResult).entity
                    if (entity is PokemonEntity) onPokemonImpact(entity)
                }
            }
        }
        super.onImpact(result)
    }

    private fun onBlockImpact() {
        setDead()
        entityDropItem(ItemStack(ItemRegistry.POKEBALL.get()))
    }

    private fun onPokemonImpact(pokemon: PokemonEntity) {
        // Catch logic
    }

    override fun createSpawnPacket() = NetworkHooks.getEntitySpawningPacket(this)

}