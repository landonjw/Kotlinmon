package ca.landonjw.kotlinmon.client.party

import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonData

data class ClientPokemonSlot(
    val index: Int,
    val pokemon: ClientPokemonData
)