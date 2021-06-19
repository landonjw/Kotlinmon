package ca.landonjw.kotlinmon.api.storage

data class StorageSlot<T>(
    val index: Int,
    val item: T
)