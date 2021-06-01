package ca.landonjw.kotlinmon.api.player.storage.pokemon.pc

import ca.landonjw.kotlinmon.api.pokemon.Pokemon

interface PCBox {

    fun get(slot: Int): Pokemon?

    fun get(row: Int, col: Int): Pokemon?

    fun getAll(): List<Pokemon>

    fun set(slot: Int, pokemon: Pokemon?)

    fun set(row: Int, col: Int, pokemon: Pokemon?)

    fun add(pokemon: Pokemon)

    fun isFull(): Boolean

}