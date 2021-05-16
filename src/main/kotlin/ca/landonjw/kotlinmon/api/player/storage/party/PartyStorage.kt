package ca.landonjw.kotlinmon.api.player.storage.party

import ca.landonjw.kotlinmon.api.player.storage.StorageTransaction
import ca.landonjw.kotlinmon.api.pokemon.Pokemon

interface PartyStorage {

    val capacity: Int

    operator fun get(slot: Int): Pokemon?

    fun getAll(): List<Pokemon>

    operator fun set(slot: Int, pokemon: Pokemon?): StorageTransaction

    fun add(pokemon: Pokemon): StorageTransaction

    fun isFull(): Boolean

}