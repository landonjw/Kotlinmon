package ca.landonjw.kotlinmon.api.storage.pokemon.party

import ca.landonjw.kotlinmon.api.storage.StorageTransaction
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import java.util.*

interface PartyStorage {

    /**
     * Gets the UUID of the owner of the party storage.
     */
    val owner: UUID

    /**
     * Gets the total number of Pokemon that may be stored at any given time.
     */
    val capacity: Int

    /**
     * Gets a [Pokemon] from a slot, if available.
     *
     * @param slot The index of the slot to get.
     * @return A [Pokemon] if there is one in the slot, or null if empty.
     */
    operator fun get(slot: Int): Pokemon?

    /**
     * Gets all [Pokemon] in the party.
     *
     * This will not container any null elements.
     */
    fun getAll(): List<Pokemon>

    /**
     * Sets a slot in the party to a pokemon, or null if empty.
     *
     * @param slot The index of the slot to set.
     * @param pokemon The pokemon to set in the slot, or null to make it empty.
     *
     * @return A storage transaction containing the result of the operation, or if it failed
     */
    operator fun set(slot: Int, pokemon: Pokemon?): StorageTransaction

    /**
     * Adds a [Pokemon] to the party if there is space to do so.
     *
     * @param pokemon The pokemon to add to the party.
     *
     * @return A storage transaction containing the result of the operation, or if it failed.
     *         This will return a failed transaction if there is no room in the party.
     */
    fun add(pokemon: Pokemon): StorageTransaction

    /**
     * Returns if the party is at maximum capacity, and can not store any more [Pokemon].
     */
    fun isFull(): Boolean

}