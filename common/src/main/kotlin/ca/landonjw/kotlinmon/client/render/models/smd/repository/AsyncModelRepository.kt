package ca.landonjw.kotlinmon.client.render.models.smd.repository

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.SmdPQCLoader
import ca.landonjw.kotlinmon.util.data.AsyncCache
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.minecraft.util.ResourceLocation
import java.util.concurrent.TimeUnit

class AsyncModelRepository: ModelRepository {

    /** The cache for models, with an access expiration of three minutes. */
    val cache = AsyncCache<ResourceLocation, SmdModel>(3, TimeUnit.MINUTES) { SmdPQCLoader.load(it) }

    @ExperimentalCoroutinesApi
    override fun getModel(location: ResourceLocation): SmdModel? {
        val deferred = cache[location]
        if (deferred.isActive || deferred.isCancelled) return null
        return deferred.getCompleted()
    }

}