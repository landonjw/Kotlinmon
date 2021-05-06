package ca.landonjw.kotlinmon.client.render.models.smd.repository

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.SmdPQCLoader
import net.minecraft.util.ResourceLocation

class SyncModelRepository: ModelRepository {

    override fun getModel(location: ResourceLocation): SmdModel = SmdPQCLoader.load(location)

}