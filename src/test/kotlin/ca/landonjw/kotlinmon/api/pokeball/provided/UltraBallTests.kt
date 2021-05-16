package ca.landonjw.kotlinmon.api.pokeball.provided

import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import io.mockk.every
import io.mockk.mockk
import net.minecraft.entity.player.ServerPlayerEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class UltraBallTests {

    @Test
    fun `gives catch rate of 2 for pokemon with catch rate of 1`() {
        val pokeBall = ProvidedPokeBall.UltraBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 1

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 2)
    }

    @Test
    fun `gives catch rate of 90 for pokemon with catch rate of 45`() {
        val pokeBall = ProvidedPokeBall.UltraBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 45

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 90)
    }

    /** This should result in 255 because the highest catch rate value is capped at 255. */
    @Test
    fun `gives catch rate of 255 for pokemon with catch rate of 255`() {
        val pokeBall = ProvidedPokeBall.UltraBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 255

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 255)
    }

}