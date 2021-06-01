package ca.landonjw.kotlinmon.server.player.storage.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.player.storage.StorageTransaction
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyNetworkService
import ca.landonjw.kotlinmon.api.player.storage.pokemon.party.PartyStorage
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fml.server.ServerLifecycleHooks
import java.util.*

class DefaultPartyStorage(override val owner: UUID) : PartyStorage {

    override val capacity = 6
    private val pokemon: Array<Pokemon?> = arrayOfNulls(capacity)
    private val partyNetworkService: PartyNetworkService by KotlinmonDI.inject()

    override fun get(slot: Int): Pokemon? {
        validateSlotInCapacity(slot)
        return pokemon[slot]
    }

    override fun getAll(): List<Pokemon> {
        return pokemon.filterNotNull().toList()
    }

    override fun set(slot: Int, pokemon: Pokemon?): StorageTransaction {
        validateSlotInCapacity(slot)
        this.pokemon[slot] = pokemon
        getOwnerAsPlayer()?.let {
            this.partyNetworkService.updatePartySlot(it, slot)
        }
        return StorageTransaction(StorageTransaction.Result.SUCCESS)
    }

    override fun add(pokemon: Pokemon): StorageTransaction {
        val emptyIndex = this.pokemon.indexOfFirst { it == null }
        if (emptyIndex == -1) return StorageTransaction.fail(TranslationTextComponent("kotlinmon.storage.party.party-full"))

        this.pokemon[emptyIndex] = pokemon
        getOwnerAsPlayer()?.let {
            this.partyNetworkService.updatePartySlot(it, emptyIndex)
        }
        return StorageTransaction.success()
    }

    override fun isFull() = pokemon.count { it == null } != 0

    private fun validateSlotInCapacity(slot: Int) {
        if (slot !in 0 until capacity) throw IllegalArgumentException("slot $slot not within bounds (0-$capacity)")
    }

    private fun getOwnerAsPlayer(): ServerPlayerEntity? {
        val server = ServerLifecycleHooks.getCurrentServer()
        return server.playerList.getPlayerByUUID(owner)
    }

}