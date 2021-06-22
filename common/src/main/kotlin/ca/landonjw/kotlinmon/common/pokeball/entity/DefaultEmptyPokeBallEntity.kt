package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.api.pokeball.PokeBall
import ca.landonjw.kotlinmon.api.pokeball.PokeBallFactory
import ca.landonjw.kotlinmon.api.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.api.pokemon.entity.PokemonEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.network.IPacket
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks
import org.kodein.di.instance

class DefaultEmptyPokeBallEntity : EmptyPokeBallEntity, DefaultPokeBallEntity {

    private val pokeBallFactory: PokeBallFactory by Kotlinmon.DI.instance()

    constructor(
        type: EntityType<out DefaultEmptyPokeBallEntity>,
        world: World,
        pokeBallType: PokeBall,
        pokeBallControllerFactory: (Entity) -> PokeBallTypeController,
        orientationControllerFactory: (Entity) -> OrientationController
    ) : super(type, world, pokeBallType, pokeBallControllerFactory, orientationControllerFactory)

    override fun onBlockImpact() {
        // Remove the poke ball entity.
        kill()
        // Spawn a poke ball item where the impact took place.
        val pokeBallItem = pokeBallFactory.createItem(pokeBallType)
        spawnAtLocation(pokeBallItem)
    }

    override fun onPokemonImpact(pokemon: PokemonEntity) {
        TODO("Add capture hooks")
    }

    override fun getAddEntityPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

}