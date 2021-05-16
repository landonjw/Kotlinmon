package ca.landonjw.kotlinmon.api.pokeball.provided

import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.ProvidedType
import io.mockk.every
import io.mockk.mockk
import net.minecraft.entity.player.ServerPlayerEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class NetBallTests {

    @Test
    fun `gives catch rate of 50 for pokemon with catch rate of 50 that isn't bug or water type`() {
        val pokeBall = ProvidedPokeBall.NetBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 50
        every { pokemon.form.type1 } returns ProvidedType.NORMAL

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 50)
    }

    @Test
    fun `gives catch rate of 175 for pokemon with catch rate of 50 that is primary bug type`() {
        val pokeBall = ProvidedPokeBall.NetBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 50
        every { pokemon.form.type1 } returns ProvidedType.BUG

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 175)
    }

    @Test
    fun `gives catch rate of 175 for pokemon with catch rate of 50 that is primary water type`() {
        val pokeBall = ProvidedPokeBall.NetBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 50
        every { pokemon.form.type1 } returns ProvidedType.WATER

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 175)
    }

    @Test
    fun `gives catch rate of 175 for pokemon with catch rate of 50 that is secondary bug type`() {
        val pokeBall = ProvidedPokeBall.NetBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 50
        every { pokemon.form.type1 } returns ProvidedType.NORMAL
        every { pokemon.form.type2 } returns ProvidedType.BUG

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 175)
    }

    @Test
    fun `gives catch rate of 175 for pokemon with catch rate of 50 that is secondary water type`() {
        val pokeBall = ProvidedPokeBall.NetBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 50
        every { pokemon.form.type1 } returns ProvidedType.NORMAL
        every { pokemon.form.type2 } returns ProvidedType.WATER

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 175)
    }

    @Test
    fun `gives catch rate of 175 for pokemon with catch rate of 50 that is bug and water type`() {
        val pokeBall = ProvidedPokeBall.NetBall
        val player: ServerPlayerEntity = mockk(relaxed = true)
        val pokemon: Pokemon = mockk(relaxed = true)
        every { pokemon.species.catchRate } returns 50
        every { pokemon.form.type1 } returns ProvidedType.WATER
        every { pokemon.form.type2 } returns ProvidedType.BUG

        val catchRate = pokeBall.getCatchRate(player, pokemon)

        Assertions.assertEquals(catchRate, 175)
    }

}