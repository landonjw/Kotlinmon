package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.PokeBallRepository
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.projectile.ThrowableEntity
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.util.math.EntityRayTraceResult
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class PokeBallEntity(type: EntityType<out PokeBallEntity>, world: World): ThrowableEntity(type, world) {

    private val pokeBallRepository: PokeBallRepository by KotlinmonDI.inject()
    private val pokeBallFactory: PokeBallFactory by KotlinmonDI.inject()

    var type: PokeBall
        get() = pokeBallRepository[dataManager.get(dwType)] ?: ProvidedPokeBall.PokeBall
        set(value) = dataManager.set(dwType, value.name)

    companion object {
        val dwType: DataParameter<String> = EntityDataManager.createKey(PokemonEntity::class.java, DataSerializers.STRING)
    }

    init {
        dataManager.register(dwType, ProvidedPokeBall.PokeBall.name)
    }

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
        val pokeBallItem = pokeBallFactory.createItem(type)
        entityDropItem(pokeBallItem)
    }

    private fun onPokemonImpact(pokemon: PokemonEntity) {
        // TODO: Catch logic
    }

    override fun createSpawnPacket() = NetworkHooks.getEntitySpawningPacket(this)

}