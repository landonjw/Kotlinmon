package ca.landonjw.kotlinmon.client.render.models.smd.loaders

import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.files.SmdPQCFileLoader
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.files.schemas.PQCSchema
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.*
import net.minecraft.util.ResourceLocation

/**
 * Loads a [SmdModel] and any (optional) animations and render properties supplied in the file.
 * This loading is done synchronously by default, and should often be handled otherwise
 * in any implementations that use it. If you're looking to simply retrieve `.pqc` models,
 * see [SmdModelRegistry].
 *
 * @author landonjw
 */
class SmdPQCLoader(
    private val pqcFileLoader: SmdPQCFileLoader,
    private val smdModelLoader: SmdModelLoader,
    private val smdAnimationLoader: SmdAnimationLoader
) {

    /**
     * Loads a [SmdModel] from a `.pqc` file, adding any optional
     * animations or render properties along the way.
     *
     * @param location the location to load `.pqc` file from
     * @return an smd model with any optional animations and/or properties added from the `.pqc` file
     */
    fun load(location: ResourceLocation): SmdModel {
        val schema = pqcFileLoader.load(location)
        val model = smdModelLoader.load(schema.modelPath)
        for (pqcAnimation in schema.animations) {
            val animation = smdAnimationLoader.load(pqcAnimation.path, model)
            model.addAnimation(pqcAnimation.name, animation)
        }
        addPropertiesToModel(model, schema)
        return model
    }

    private fun addPropertiesToModel(model: SmdModel, schema: PQCSchema) {
        val properties = mutableListOf<SmdRenderProperty<*>>()
        if (schema.scale != null) properties.add(Scale(schema.scale))
        if (schema.rotationOffset != null) properties.add(RotationOffset(schema.rotationOffset))
        if (schema.positionOffset != null) properties.add(PositionOffset(schema.positionOffset))
        model.renderProperties.addAll(properties)
    }

}