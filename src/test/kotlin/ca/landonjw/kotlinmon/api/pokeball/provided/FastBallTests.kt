package ca.landonjw.kotlinmon.api.pokeball.provided

import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import io.mockk.every
import io.mockk.mockk
import net.minecraft.entity.player.ServerPlayerEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class FastBallTests {

    @Test
    fun `gives catch rate of 50 when pokemon catch rate is 50 and speed is below 100`() {
        val pokeBall = ProvidedPokeBall.FastBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 50
        every { pokemon.form.baseStats.speed } returns 1

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 50)
    }

    @Test
    fun `gives catch rate of 50 when pokemon catch rate is 50 and speed is 99`() {
        val pokeBall = ProvidedPokeBall.FastBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 50
        every { pokemon.form.baseStats.speed } returns 99

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 50)
    }

    @Test
    fun `gives catch rate of 200 when pokemon catch rate is 50 and speed is 100`() {
        val pokeBall = ProvidedPokeBall.FastBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 50
        every { pokemon.form.baseStats.speed } returns 100

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 200)
    }

    @Test
    fun `gives catch rate of 255 when pokemon catch rate is 255 and speed is 100`() {
        val pokeBall = ProvidedPokeBall.FastBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 255
        every { pokemon.form.baseStats.speed } returns 100

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 255)
    }

}