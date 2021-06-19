package ca.landonjw.kotlinmon.client.render.models.smd.repository

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import net.minecraft.util.ResourceLocation

interface ModelRepository {

    suspend fun getModelAsync(location: ResourceLocation): SmdModel?

    fun getModel(location: ResourceLocation): SmdModel?

}