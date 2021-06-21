package ca.landonjw.kotlinmon.client.render.models.smd.repository

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import com.google.common.cache.LoadingCache
import net.minecraft.util.ResourceLocation

class CachedModelRepository(
    private val cache: LoadingCache<ResourceLocation, SmdModel>
): ModelRepository {

    override suspend fun getModelAsync(location: ResourceLocation): SmdModel? {
        return cache[location]
    }

    override fun getModel(location: ResourceLocation): SmdModel {
        return cache[location]
    }

}