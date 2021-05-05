package ca.landonjw.kotlinmon.common.util.data

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import java.util.concurrent.TimeUnit

/**
 * Simple cache convenience wrapper for asynchronous loading.
 *
 * @param expirationTime the amount of time before an item in the cache expires
 * @param expirationUnit the time unit used for defining expiration time
 * @property supplier a function used to load new values, based off a unique identifier
 *
 * @author landonjw
 */
class AsyncCache<K, V>(
    expirationTime: Long,
    expirationUnit: TimeUnit,
    val supplier: suspend (K) -> V,
) {

    private val cache: LoadingCache<K, Deferred<V>>

    init {
        val loader = object : CacheLoader<K, Deferred<V>>() {
            override fun load(key: K): Deferred<V> {
                return CoroutineScope(IO).async {
                    return@async supplier(key)
                }
            }
        }
        cache = CacheBuilder.newBuilder()
            .expireAfterAccess(expirationTime, expirationUnit)
            .build(loader)
    }

    operator fun get(key: K): Deferred<V> = cache.getUnchecked(key)

}