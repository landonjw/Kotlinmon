package ca.landonjw.kotlinmon.common.pokeball.entity

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.PokemonFactory
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntity
import net.minecraft.entity.EntityType
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

/**
 * A poke ball that currently has a [Pokemon] occupying it.
 *
 * @author landonjw
 */
class OccupiedPokeBallEntity(type: EntityType<out OccupiedPokeBallEntity>, world: World) : PokeBallEntity(type, world) {

    private val pokemonFactory : PokemonFactory by KotlinmonDI.inject()

    /** The pokemon that is occupying the poke ball. */
    lateinit var pokemon: Pokemon

    /**
     * Upon collision with a block, this will spawn an entity for the pokemon that is inside the poke ball.
     */
    override fun onBlockImpact() {
        if (!world.isRemote) {
            val pokemonEntity = pokemonFactory.createEntity(pokemon, entityWorld).also {
                it.setPosition(posX, posY, posZ)
            }
            entityWorld.addEntity(pokemonEntity)
            setDead()
        }
    }

    override fun onPokemonImpact(pokemon: PokemonEntity) {
        TODO("Add battle hooks")
    }

    override fun createSpawnPacket() = NetworkHooks.getEntitySpawningPacket(this)

}