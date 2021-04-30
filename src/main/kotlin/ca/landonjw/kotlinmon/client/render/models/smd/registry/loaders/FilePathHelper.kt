package ca.landonjw.kotlinmon.client.render.models.smd.registry.loaders

import net.minecraft.util.ResourceLocation

internal fun getParentPath(location: ResourceLocation): String {
    val locationPathArgs = location.path
        .split("/")

    return locationPathArgs
        .subList(0, locationPathArgs.size - 1)
        .reduce { acc, s -> "$acc/$s" }
}