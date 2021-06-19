package ca.landonjw.kotlinmon.api.pokeball.capture.modifiers

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.ProvidedType
import net.minecraft.entity.player.ServerPlayerEntity

class NetBallCatchRateStrategy: PokeBallCatchRateStrategy {
    override fun getCatchRate(thrower: ServerPlayerEntity, pokemon: Pokemon): Int {
        val pokemonTypes = listOf(pokemon.form.type1, pokemon.form.type2)
        return when {
            ProvidedType.WATER in pokemonTypes || ProvidedType.BUG in pokemonTypes -> {
                (pokemon.catchRate() * 3.5).toInt().coerceIn(1, 255)
            }
            else -> pokemon.catchRate()
        }
    }
}