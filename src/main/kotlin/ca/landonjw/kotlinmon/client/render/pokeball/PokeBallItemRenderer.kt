package ca.landonjw.kotlinmon.client.render.pokeball

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokeball.ProvidedPokeBall
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.RotationOffset
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.Scale
import ca.landonjw.kotlinmon.client.render.models.smd.renderer.SmdModelRenderer
import ca.landonjw.kotlinmon.client.render.models.smd.repository.ModelRepository
import ca.landonjw.kotlinmon.common.pokeball.item.PokeBallItem
import ca.landonjw.kotlinmon.util.math.geometry.toRadians
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.model.ItemCameraTransforms
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.vector.Vector3f

class PokeBallItemRenderer: ItemStackTileEntityRenderer() {

    val modelRepository: ModelRepository by KotlinmonDI.inject("async")
    val modelRenderer: SmdModelRenderer by KotlinmonDI.inject()

    /**
     * Used to render the item.
     *
     * @param p_239207_2_ where the item is being rendered (gui, first person hand, third person hand, etc.)
     */
    override fun func_239207_a_(
        stack: ItemStack,
        p_239207_2_: ItemCameraTransforms.TransformType,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        combinedLight: Int,
        combinedOverlay: Int
    ) {
        val item = stack.item as? PokeBallItem ?: return
        val pokeBall = item.getPokeBall(stack) ?: ProvidedPokeBall.PokeBall
        val pokeBallModel = modelRepository[pokeBall.modelLocation] ?: return

        matrixStack.translate(0.5, -0.5, 0.5)

        // Do different transformations depending on where the item is being rendered.
        when (p_239207_2_) {
            ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND -> {
                globalTransform(
                    model = pokeBallModel,
                    rotation = Vector3f(35f.toRadians(), 90f.toRadians(), -(15f.toRadians())),
                    scale = Vector3f(0.1f, 0.1f, 0.1f)
                )
            }
            ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND -> {
                globalTransform(
                    model = pokeBallModel,
                    rotation = Vector3f(85f.toRadians(), -(90f.toRadians()), -(25f.toRadians())),
                    scale = Vector3f(0.1f, 0.1f, 0.1f)
                )
            }
            ItemCameraTransforms.TransformType.GUI -> {
                globalTransform(
                    model = pokeBallModel,
                    rotation = Vector3f(85f.toRadians(), -(25f.toRadians()), -(25f.toRadians())),
                    scale = Vector3f(0.14f, 0.14f, 0.14f)
                )
            }
            ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND -> {
                globalTransform(
                    model = pokeBallModel,
                    rotation = Vector3f(35f.toRadians(), 90f.toRadians(), -(15f.toRadians())),
                    scale = Vector3f(0.06f, 0.06f, 0.06f)
                )
            }
            ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND -> {
                globalTransform(
                    model = pokeBallModel,
                    rotation = Vector3f(85f.toRadians(), -(90f.toRadians()), -(25f.toRadians())),
                    scale = Vector3f(0.06f, 0.06f, 0.06f)
                )
            }
            ItemCameraTransforms.TransformType.GROUND -> {
                globalTransform(
                    model = pokeBallModel,
                    rotation = Vector3f(85f.toRadians(), -(90f.toRadians()), -(25f.toRadians())),
                    scale = Vector3f(0.06f, 0.06f, 0.06f)
                )
            }
        }
        modelRenderer.render(matrixStack, pokeBallModel)
    }

    private fun globalTransform(model: SmdModel, rotation: Vector3f, scale: Vector3f) {
        model.replaceProperty(RotationOffset(rotation))
        model.replaceProperty(Scale(scale))
    }

}