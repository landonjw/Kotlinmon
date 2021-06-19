package ca.landonjw.kotlinmon.client.render.models.smd.repository

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.SmdPQCLoader
import net.minecraft.util.ResourceLocation

class DefaultModelRepository(
    private val pqcLoader: SmdPQCLoader
) : ModelRepository {

    override suspend fun getModelAsync(location: ResourceLocation): SmdModel = pqcLoader.load(location)

    override fun getModel(location: ResourceLocation): SmdModel = pqcLoader.load(location)

}