package ca.landonjw.kotlinmon.api.data

interface KeyConsumer<in T : Key<*>> {

    fun offer(key: T): Boolean?

}