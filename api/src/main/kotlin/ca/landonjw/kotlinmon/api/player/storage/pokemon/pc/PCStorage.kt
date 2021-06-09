package ca.landonjw.kotlinmon.api.player.storage.pokemon.pc

import ca.landonjw.kotlinmon.api.pokemon.Pokemon

interface PCStorage {

    val numBoxes: Int

    fun getBox(slot: Int): ca.landonjw.kotlinmon.api.player.storage.pokemon.pc.PCBox?

    fun getBoxes(): List<ca.landonjw.kotlinmon.api.player.storage.pokemon.pc.PCBox>

    fun getAllPokemon(): List<Pokemon>

    fun add(pokemon: Pokemon)

}