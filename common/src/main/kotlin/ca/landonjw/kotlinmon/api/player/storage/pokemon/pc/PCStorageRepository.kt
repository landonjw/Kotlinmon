package ca.landonjw.kotlinmon.api.player.storage.pokemon.pc

import net.minecraft.entity.player.ServerPlayerEntity

interface PCStorageRepository {

    operator fun get(player: ServerPlayerEntity): PCStorage

}