package ca.landonjw.kotlinmon.client.render.models.smd.registry

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.SmdPQCLoader
import ca.landonjw.kotlinmon.util.data.AsyncCache
import net.minecraft.util.ResourceLocation
import java.util.concurrent.TimeUnit

object SmdModelRegistry {
    val cache = AsyncCache<ResourceLocation, SmdModel>(3, TimeUnit.MINUTES) { SmdPQCLoader.load(it) }

    fun getOrLoad(location: ResourceLocation) = cache[location]

}