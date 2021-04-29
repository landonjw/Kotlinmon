package ca.landonjw.kotlinmon.client.render.models.api.renderer

import ca.landonjw.kotlinmon.api.data.Key
import ca.landonjw.kotlinmon.client.render.models.api.Model
import com.mojang.blaze3d.matrix.MatrixStack

interface RenderProperty<T> : Key<T>

interface ModelRenderer<T : Model> {
    fun render(matrix: MatrixStack, model: T)
}