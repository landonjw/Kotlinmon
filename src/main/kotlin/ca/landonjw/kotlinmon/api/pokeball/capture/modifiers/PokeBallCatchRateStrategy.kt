package ca.landonjw.kotlinmon.api.pokeball.capture.modifiers

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import net.minecraft.entity.player.ServerPlayerEntity

interface PokeBallCatchRateStrategy {
    fun getCatchRate(thrower: ServerPlayerEntity, pokemon: Pokemon): Int
}