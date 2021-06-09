package ca.landonjw.kotlinmon.api.player.storage

import net.minecraft.util.text.ITextComponent

class StorageTransaction(val result: ca.landonjw.kotlinmon.api.player.storage.StorageTransaction.Result, val message: ITextComponent? = null) {

    companion object {
        fun fail(message: ITextComponent? = null) = ca.landonjw.kotlinmon.api.player.storage.StorageTransaction(
            ca.landonjw.kotlinmon.api.player.storage.StorageTransaction.Result.FAILURE,
            message
        )
        fun success(message: ITextComponent? = null) = ca.landonjw.kotlinmon.api.player.storage.StorageTransaction(
            ca.landonjw.kotlinmon.api.player.storage.StorageTransaction.Result.SUCCESS,
            message
        )
    }

    enum class Result {
        SUCCESS, FAILURE
    }
}