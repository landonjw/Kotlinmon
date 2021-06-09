package ca.landonjw.kotlinmon.api.player.storage

import net.minecraft.util.text.ITextComponent

class StorageTransaction(val result: Result, val message: ITextComponent? = null) {

    companion object {

        fun fail(message: ITextComponent? = null) = StorageTransaction(Result.FAILURE, message)

        fun success(message: ITextComponent? = null) = StorageTransaction(Result.SUCCESS, message)

    }

    enum class Result {
        SUCCESS, FAILURE
    }
}