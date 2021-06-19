package ca.landonjw.kotlinmon.api.pokeball.capture.modifiers

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import net.minecraft.entity.player.ServerPlayerEntity

class FastBallCatchRateStrategy: PokeBallCatchRateStrategy {
    override fun getCatchRate(thrower: ServerPlayerEntity, pokemon: Pokemon): Int {
        val pokemonSpeed = pokemon.form.baseStats.speed
        return when {
            pokemonSpeed >= 100 -> (pokemon.catchRate() * 4).coerceIn(1, 255)
            else -> pokemon.catchRate()
        }
    }
}