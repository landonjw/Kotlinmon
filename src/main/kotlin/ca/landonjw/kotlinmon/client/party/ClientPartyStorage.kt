package ca.landonjw.kotlinmon.client.party

import ca.landonjw.kotlinmon.api.player.storage.StorageTransaction
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import ca.landonjw.kotlinmon.client.pokemon.ClientPokemonData
import net.minecraft.util.text.TranslationTextComponent
import java.lang.IllegalArgumentException

class ClientPartyStorage {

    val capacity = 6
    private val pokemon: Array<ClientPokemonData?> = arrayOfNulls(capacity)

    var selectedSlot: Int? = null
        set(value) {
            if (selectedSlot == null){
                field = value
            }
            else {
                validateSlotInCapacity(value!!)
                if (pokemon[value] == null) throw IllegalArgumentException("you can only select a filled slot")
                field = value
            }
        }

    fun add(pokemon: ClientPokemonData): StorageTransaction {
        val emptyIndex = this.pokemon.indexOfFirst { it == null }

        if (emptyIndex == -1) {
            return StorageTransaction(StorageTransaction.Result.FAILURE, TranslationTextComponent("kotlinmon.storage.party.party-full"))
        }

        this.pokemon[emptyIndex] = pokemon
        return StorageTransaction(StorageTransaction.Result.SUCCESS)
    }

    fun remove(slot: Int): StorageTransaction {
        validateSlotInCapacity(slot)
        pokemon[slot] = null
        checkForSelectedSlotUpdate()
        return StorageTransaction(StorageTransaction.Result.SUCCESS)
    }

    operator fun set(slot: Int, pokemon: ClientPokemonData?): StorageTransaction {
        validateSlotInCapacity(slot)
        this.pokemon[slot] = pokemon
        checkForSelectedSlotUpdate()
        return StorageTransaction(StorageTransaction.Result.SUCCESS)
    }

    fun swap(oldSlot: Int, newSlot: Int): StorageTransaction {
        validateSlotInCapacity(oldSlot)
        validateSlotInCapacity(newSlot)

        // Store Pokemon from new slot so that we can retrieve it after overwrite
        val temp = pokemon[newSlot]
        pokemon[newSlot] = pokemon[oldSlot]
        pokemon[oldSlot] = temp
        checkForSelectedSlotUpdate()
        return StorageTransaction(StorageTransaction.Result.SUCCESS)
    }

    operator fun get(slot: Int): ClientPokemonData? {
        validateSlotInCapacity(slot)
        return pokemon[slot]
    }

    private fun checkForSelectedSlotUpdate() {
        if (selectedSlot == null) return
        if (pokemon[selectedSlot!!] == null) selectedSlot = getAvailablePokemonSlot()
    }

    fun getSelectedPokemon(): ClientPokemonData? = if (selectedSlot == null) null else pokemon[selectedSlot!!]

    fun selectPreviousPokemon() {
        when {
            selectedSlot == null -> {
                selectedSlot = getAvailablePokemonSlot()
            }
            selectedSlot == 0 -> return
            else -> {
                for (slot in (selectedSlot!! - 1) downTo 0) {
                    if (pokemon[slot] != null) {
                        selectedSlot = slot
                        return
                    }
                }
            }
        }
    }

    fun selectNextPokemon() {
        when {
            selectedSlot == null -> {
                selectedSlot = getAvailablePokemonSlot()
            }
            selectedSlot == capacity - 1 -> return
            else -> {
                for (slot in (selectedSlot!! + 1) until capacity) {
                    if (pokemon[slot] != null) {
                        selectedSlot = slot
                        return
                    }
                }
            }
        }
    }

    private fun getAvailablePokemonSlot(): Int? {
        val slot = pokemon.indexOfFirst { it != null }
        return if (slot == -1) null else slot
    }

    private fun validateSlotInCapacity(slot: Int) {
        if (slot !in 0 until capacity) throw IllegalArgumentException("slot $slot not within bounds (0-$capacity)")
    }

}