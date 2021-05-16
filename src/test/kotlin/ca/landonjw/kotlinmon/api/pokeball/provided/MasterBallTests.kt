package ca.landonjw.kotlinmon.api.pokeball.provided

import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import io.mockk.every
import io.mockk.mockk
import net.minecraft.entity.player.ServerPlayerEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class MasterBallTests {

    @Test
    fun `gives catch rate of 255 (guaranteed) for pokemon with arbitrary catch rate (1)`() {
        val pokeBall = ProvidedPokeBall.MasterBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 1

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 255)
    }

    @Test
    fun `gives catch rate of 255 (guaranteed) for pokemon with arbitrary catch rate (45)`() {
        val pokeBall = ProvidedPokeBall.MasterBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 45

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 255)
    }

    @Test
    fun `gives catch rate of 255 (guaranteed) for pokemon with arbitrary catch rate (255)`() {
        val pokeBall = ProvidedPokeBall.MasterBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 255

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 255)
    }

}