package ca.landonjw.kotlinmon.api.pokeball.provided

import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import io.mockk.every
import io.mockk.mockk
import net.minecraft.entity.player.ServerPlayerEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PokeBallTests {

    @Test
    fun `gives catch rate of 1 for pokemon with catch rate of 1`() {
        val pokeBall = ProvidedPokeBall.PokeBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 1

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 1)
    }

    @Test
    fun `gives catch rate of 45 for pokemon with catch rate of 45`() {
        val pokeBall = ProvidedPokeBall.PokeBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 45

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 45)
    }

    @Test
    fun `gives catch rate of 255 for pokemon with catch rate of 255`() {
        val pokeBall = ProvidedPokeBall.PokeBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 255

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 255)
    }

}