package ca.landonjw.kotlinmon.client.render.models.smd.repository

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import net.minecraft.util.ResourceLocation
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

internal class CachedModelRepositoryTest {

    @Test
    fun `getModelAsync does not block main thread`() {
        val processTimeMillis = 3000
        val loader = object : CacheLoader<ResourceLocation, SmdModel>() {
            override fun load(key: ResourceLocation): SmdModel {
                Thread.sleep(3000)
                return mockk(relaxed = true)
            }
        }
        val cache: LoadingCache<ResourceLocation, SmdModel> = CacheBuilder.newBuilder()
            .expireAfterAccess(60, TimeUnit.SECONDS)
            .build(loader)

        val repository = CachedModelRepository(cache)
        val resourceLocation: ResourceLocation = mockk(relaxed = true)

        val timeToExecute = measureTimeMillis {
            CoroutineScope(IO).async {
                repository.getModelAsync(resourceLocation)
            }
        }

        assertTrue(timeToExecute < processTimeMillis,
            "expected execution time to be less than 50ms but it took ${timeToExecute}ms")
    }

    @Test
    fun `getModel blocks main thread`() {
        val processTimeMillis = 3000
        val loader = object : CacheLoader<ResourceLocation, SmdModel>() {
            override fun load(key: ResourceLocation): SmdModel {
                Thread.sleep(3000)
                return mockk(relaxed = true)
            }
        }
        val cache: LoadingCache<ResourceLocation, SmdModel> = CacheBuilder.newBuilder()
            .expireAfterAccess(60, TimeUnit.SECONDS)
            .build(loader)

        val repository = CachedModelRepository(cache)
        val resourceLocation: ResourceLocation = mockk(relaxed = true)

        val timeToExecute = measureTimeMillis {
            repository.getModel(resourceLocation)
        }

        assertTrue(timeToExecute >= processTimeMillis,
            "expected execution time to be over 3000ms but it took ${timeToExecute}ms")
    }

}