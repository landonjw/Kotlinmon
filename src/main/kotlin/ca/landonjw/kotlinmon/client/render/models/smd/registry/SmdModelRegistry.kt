package ca.landonjw.kotlinmon.client.render.models.smd.registry

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.registry.loaders.SmdPQCLoader
import ca.landonjw.kotlinmon.util.data.AsyncCache
import net.minecraft.util.ResourceLocation
import java.util.concurrent.TimeUnit

/**
 * The primary access point for getting a [SmdModel] from a `.pqc` file.
 * This will handle the loading asynchronously, and temporarily cache models to prevent
 * repetitive loading.
 *
 * @author landonjw
 */
object SmdModelRegistry {

    /** The cache for models, with an access expiration of three minutes. */
    val cache = AsyncCache<ResourceLocation, SmdModel>(3, TimeUnit.MINUTES) { SmdPQCLoader.load(it) }

    /**
     * Gets or loads a model from a supplied `.pqc` resource location.
     * This will load all animations and render properties defined within the `.pqc` file.
     *
     * @param the location to load model from
     */
    fun getOrLoad(location: ResourceLocation) = cache[location]

}