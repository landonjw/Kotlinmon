package ca.landonjw.kotlinmon.client.render.models.smd.repository

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import net.minecraft.util.ResourceLocation

interface ModelRepository {

    operator fun get(location: ResourceLocation): SmdModel? = getModel(location)

    fun getModel(location: ResourceLocation): SmdModel?

}