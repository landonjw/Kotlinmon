package ca.landonjw.kotlinmon.common.util

open class Registry<K, V>(
    private val allowOverrides: Boolean,
) {

    protected val values: MutableMap<K, V> = mutableMapOf()

    open operator fun get(key: K): V? = values[key]

    init {
        registerDefaultValues()
    }

    open fun register(key: K, value: V) {
        if (!allowOverrides && values.containsKey(key)) throw IllegalArgumentException("key already registered")
        values[key] = value
    }

    protected open fun registerDefaultValues() {}

}