package ca.landonjw.kotlinmon.client.render.models.smd.loaders

import ca.landonjw.kotlinmon.client.render.models.api.renderer.RenderProperty
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.PQCFileLoader
import ca.landonjw.kotlinmon.client.render.models.smd.loaders.schemas.PQCSchema
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.GlitchNoise
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.PositionOffset
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.RotationOffset
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.Scale
import net.minecraft.util.ResourceLocation

object SmdPQCLoader {

    fun load(location: ResourceLocation): SmdModel {
        val schema = PQCFileLoader.load(location)
        val model = SmdModelLoader.load(schema.modelPath)
        for (pqcAnimation in schema.animations) {
            val animation = SmdAnimationLoader.load(pqcAnimation.path, model)
            model.addAnimation(pqcAnimation.name, animation)
        }
        addPropertiesToModel(model, schema)
        return model
    }

    private fun addPropertiesToModel(model: SmdModel, schema: PQCSchema) {
        val properties = mutableListOf<RenderProperty<*>>()
        if (schema.scale != null) properties.add(Scale(schema.scale))
        if (schema.rotationOffset != null) properties.add(RotationOffset(schema.rotationOffset))
        if (schema.positionOffset != null) properties.add(PositionOffset(schema.positionOffset))
        if (schema.glitch != null) properties.add(GlitchNoise(schema.glitch))
        model.renderProperties.addAll(properties)
    }
}