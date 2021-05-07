package ca.landonjw.kotlinmon.api.pokeball.capture.modifiers

import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import net.minecraft.entity.player.ServerPlayerEntity

class BasicCaptureModifier(val multiplier: Float): PokeBallCatchRateStrategy {
    override fun getCatchRate(thrower: ServerPlayerEntity, pokemon: Pokemon): Int {
        return (pokemon.catchRate() * multiplier).toInt().coerceIn(1, 255)
    }

}