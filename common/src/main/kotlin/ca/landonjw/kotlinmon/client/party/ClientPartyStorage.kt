package ca.landonjw.kotlinmon.client.party

import ca.landonjw.kotlinmon.api.storage.StorageTransaction
import ca.landonjw.kotlinmon.common.pokemon.PokemonDTO
import net.minecraft.util.text.TranslationTextComponent

class ClientPartyStorage {

    val capacity: Int = 6
    private val slots: Array<PokemonDTO?> = arrayOfNulls(capacity)

    val isEmpty: Boolean
        get() = slots.isEmpty()

    var selectedSlot: Int? = null
        set(value) {
            if (selectedSlot == null || value == null){
                field = value
            }
            else {
                validateSlotInCapacity(value)
                if (slots[value] == null) throw IllegalArgumentException("you can only select a filled slot")
                field = value
            }
        }

    fun add(pokemon: PokemonDTO): StorageTransaction {
        val emptyIndex = this.slots.indexOfFirst { it == null }

        if (emptyIndex == -1) {
            return StorageTransaction(StorageTransaction.Result.FAILURE, TranslationTextComponent("kotlinmon.storage.party.party-full"))
        }

        this.slots[emptyIndex] = pokemon
        return StorageTransaction(StorageTransaction.Result.SUCCESS)
    }

    fun remove(slot: Int): StorageTransaction {
        validateSlotInCapacity(slot)
        slots[slot] = null
        checkForSelectedSlotUpdate()
        return StorageTransaction(StorageTransaction.Result.SUCCESS)
    }

    fun clear() {
        // Make it so player is no longer selecting anything in party
        selectedSlot = null
        // Clear all pokemon from the party
        for (slot in 0 until capacity) {
            slots[slot] = null
        }
    }

    operator fun set(slot: Int, pokemon: PokemonDTO?): StorageTransaction {
        validateSlotInCapacity(slot)
        this.slots[slot] = pokemon
        checkForSelectedSlotUpdate()
        return StorageTransaction(StorageTransaction.Result.SUCCESS)
    }

    fun swap(oldSlot: Int, newSlot: Int): StorageTransaction {
        validateSlotInCapacity(oldSlot)
        validateSlotInCapacity(newSlot)

        // Store Pokemon from new slot so that we can retrieve it after overwrite
        val temp = slots[newSlot]
        slots[newSlot] = slots[oldSlot]
        slots[oldSlot] = temp
        checkForSelectedSlotUpdate()
        return StorageTransaction(StorageTransaction.Result.SUCCESS)
    }

    operator fun get(slot: Int): PokemonDTO? {
        validateSlotInCapacity(slot)
        return slots[slot]
    }

    private fun checkForSelectedSlotUpdate() {
        if (selectedSlot == null) return
        if (slots[selectedSlot!!] == null) selectedSlot = getAvailablePokemonSlot()
    }

    fun getSelectedPokemon(): PokemonDTO? = if (selectedSlot == null) null else slots[selectedSlot!!]

    fun selectPreviousPokemon() {
        when (selectedSlot) {
            null -> {
                selectedSlot = getAvailablePokemonSlot()
            }
            0 -> return
            else -> {
                for (slot in (selectedSlot!! - 1) downTo 0) {
                    if (slots[slot] != null) {
                        selectedSlot = slot
                        return
                    }
                }
            }
        }
    }

    fun selectNextPokemon() {
        when (selectedSlot) {
            null -> {
                selectedSlot = getAvailablePokemonSlot()
            }
            capacity - 1 -> return
            else -> {
                for (slot in (selectedSlot!! + 1) until capacity) {
                    if (slots[slot] != null) {
                        selectedSlot = slot
                        return
                    }
                }
            }
        }
    }

    private fun getAvailablePokemonSlot(): Int? {
        val slot = slots.indexOfFirst { it != null }
        return if (slot == -1) null else slot
    }

    private fun validateSlotInCapacity(slot: Int) {
        if (slot !in 0 until capacity) throw IllegalArgumentException("slot $slot not within bounds (0-$capacity)")
    }

}