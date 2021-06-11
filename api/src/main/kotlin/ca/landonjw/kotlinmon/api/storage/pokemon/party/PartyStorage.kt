package ca.landonjw.kotlinmon.api.storage.pokemon.party

import ca.landonjw.kotlinmon.api.storage.StorageTransaction
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import java.util.*

interface PartyStorage {

    val owner: UUID

    val capacity: Int

    operator fun get(slot: Int): Pokemon?

    fun getAll(): List<Pokemon>

    operator fun set(slot: Int, pokemon: Pokemon?): StorageTransaction

    fun add(pokemon: Pokemon): StorageTransaction

    fun isFull(): Boolean

}