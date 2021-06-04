package ca.landonjw.kotlinmon.client.party

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.network.KotlinmonNetworkChannel
import ca.landonjw.kotlinmon.common.network.server.packets.storage.party.SynchronizePartyRequest
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

/**
 * Synchronizes the client side party to the server that the client is currently connected to.
 *
 * If a player is on a server and hasn't yet been sent the data of the player's party,
 * this will intermittently send requests to the server for the party.
 *
 * @author landonjw
 */
object ClientPartySynchronizer {

    private val party: ClientPartyStorage by KotlinmonDI.inject()
    private val networkChannel: KotlinmonNetworkChannel by KotlinmonDI.inject()

    private const val SYNC_CHECK_INTERVAL_TICKS: Int = 60
    private var ticksSinceLastCheck: Int = 0

    /**
     * If the client party is currently synchronized with the server.
     * This is used to determine if the client should continue making requests to the server.
     */
    private var partySynchronized: Boolean = false

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    /**
     * On every client tick, this tries to keep the client's party synchronized with the server they are
     * currently on.
     *
     * If the client is currently not connected to a server (ie. in main title screen)
     * this will cleanup their client party for the next join.
     */
    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        // Check if player has had their party synchronized on interval
        if (event.phase == TickEvent.Phase.END) return
        ticksSinceLastCheck = (ticksSinceLastCheck + 1) % SYNC_CHECK_INTERVAL_TICKS
        if (ticksSinceLastCheck == 0) {
            attemptSynchronization()
        }
    }

    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load) {
        if (!event.world.isRemote) {
            attemptSynchronization()
        }
    }

    /**
     * Tries to keep the client's party synchronized with the server they are connected to.
     *
     * If the client is currently not connected to a server, this will clear their client party
     * for the next time they join a server.
     */
    fun attemptSynchronization() {
        // Check if player is in a world and not just sitting at main screen
        if (Minecraft.getInstance().world == null) {
            // Reset the player's party if they're sitting in main screen to prevent desyncs.
            party.clear()
            partySynchronized = false
            return
        }
        else {
            // If client party hasn't been populated, send a request to server to retrieve party
            if (partySynchronized) return
            requestPartyFromServer()
        }
    }

    /**
     * Makes a request from the client to the server asking the server to synchronize the party for the client.
     */
    private fun requestPartyFromServer() {
        networkChannel.sendToServer(SynchronizePartyRequest())
    }

    /**
     * Synchronizes the client's party with the server given a list of data slots.
     *
     * This should be delegated to when initially synchronizing from the server in order
     * to mark that the party has been synchronized and stop sending requests.
     */
    fun synchronizeClientParty(slots: List<ClientPokemonSlot>) {
        // Clears the party to make sure there's no garbage left over from previous sessions
        party.clear()
        // Sets the slots in the client party storage
        for (slot in slots) {
            party[slot.index] = slot.pokemon
        }
        // Marks the party as synchronized
        partySynchronized = true
    }

}