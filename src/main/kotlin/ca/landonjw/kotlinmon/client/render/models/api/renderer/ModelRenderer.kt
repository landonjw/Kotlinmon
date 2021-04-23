package ca.landonjw.kotlinmon.client.render.models.api.renderer

import ca.landonjw.kotlinmon.client.render.models.api.Model
import com.mojang.blaze3d.matrix.MatrixStack

interface ModelRenderer<T: Model> {
    fun render(matrix: MatrixStack, model: T)
}