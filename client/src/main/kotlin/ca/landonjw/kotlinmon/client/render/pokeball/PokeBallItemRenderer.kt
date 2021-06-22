package ca.landonjw.kotlinmon.client.render.pokeball

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
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.client.renderer.model.ItemCameraTransforms
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.vector.Vector3f
import net.minecraftforge.client.ForgeHooksClient

class PokeBallItemRenderer(
    private val modelRepository: ModelRepository,
    private val modelRenderer: SmdModelRenderer
) : ItemStackTileEntityRenderer() {

    private val itemRenderer: ItemRenderer
        get() = Minecraft.getInstance().itemRenderer

    /**
     * Used to render the item.
     *
     * @param p_239207_2_ where the item is being rendered (gui, first person hand, third person hand, etc.)
     */

    override fun renderByItem(
        stack: ItemStack,
        p_239207_2_: ItemCameraTransforms.TransformType,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        combinedLight: Int,
        combinedOverlay: Int
    ) {
        val item = stack.item as? PokeBallItem ?: return
        val pokeBall = item.getPokeBall(stack) ?: ProvidedPokeBall.PokeBall
        val pokeBallModel = modelRepository.getModel(pokeBall.modelLocation) ?: return // TODO: This is synchronous

        // Do different transformations depending on where the item is being rendered.
        when (p_239207_2_) {
            ItemCameraTransforms.TransformType.GUI -> {
                renderItem(stack, matrixStack, p_239207_2_, buffer, combinedLight, combinedOverlay)
            }
            ItemCameraTransforms.TransformType.GROUND -> {
                matrixStack.scale(0.5f, 0.5f, 0.5f)
                matrixStack.translate(0.64, 0.5, 0.5)
                renderItem(stack, matrixStack, p_239207_2_, buffer, combinedLight, combinedOverlay)
            }
            ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND -> {
                renderModel(
                    matrixStack = matrixStack,
                    model = pokeBallModel,
                    rotation = Vector3f(35f.toRadians(), 90f.toRadians(), -(15f.toRadians())),
                    scalars = Vector3f(0.1f, 0.1f, 0.1f)
                )
            }
            ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND -> {
                renderModel(
                    matrixStack = matrixStack,
                    model = pokeBallModel,
                    rotation = Vector3f(85f.toRadians(), -(90f.toRadians()), -(25f.toRadians())),
                    scalars = Vector3f(0.1f, 0.1f, 0.1f)
                )
            }
            ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND -> {
                renderModel(
                    matrixStack = matrixStack,
                    model = pokeBallModel,
                    rotation = Vector3f(35f.toRadians(), 90f.toRadians(), -(15f.toRadians())),
                    scalars = Vector3f(0.06f, 0.06f, 0.06f)
                )
            }
            ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND -> {
                renderModel(
                    matrixStack = matrixStack,
                    model = pokeBallModel,
                    rotation = Vector3f(85f.toRadians(), -(90f.toRadians()), -(25f.toRadians())),
                    scalars = Vector3f(0.06f, 0.06f, 0.06f)
                )
            }
        }
    }

    private fun renderItem(
        stack: ItemStack,
        matrixStack: MatrixStack,
        cameraType: ItemCameraTransforms.TransformType,
        buffer: IRenderTypeBuffer,
        combinedLight: Int,
        combinedOverlay: Int
    ) {
        if (stack.isEmpty) return

        var itemModel = itemRenderer.itemModelShaper.getItemModel(stack)
        itemModel = ForgeHooksClient.handleCameraTransforms(matrixStack, itemModel, cameraType, false)

        matrixStack.pushPose()

        if (itemModel.isLayered) {
            ForgeHooksClient.drawItemLayered(
                itemRenderer,
                itemModel,
                stack,
                matrixStack,
                buffer,
                combinedLight,
                combinedOverlay,
                true
            )
        } else {
            val renderType = RenderTypeLookup.getRenderType(stack, true)
            val vertexBuilder = ItemRenderer.getFoilBuffer(buffer, renderType, true, stack.isEnchanted)
            itemRenderer.renderModelLists(itemModel, stack, combinedLight, combinedOverlay, matrixStack, vertexBuilder)
        }
        matrixStack.popPose()
    }

    private fun renderModel(matrixStack: MatrixStack, model: SmdModel, rotation: Vector3f, scalars: Vector3f) {
        matrixStack.translate(0.5, -0.5, 0.5)
        globalTransform(model, rotation, scalars)
        modelRenderer.render(matrixStack, model)
    }

    private fun globalTransform(model: SmdModel, rotation: Vector3f, scale: Vector3f) {
        model.replaceProperty(RotationOffset(rotation))
        model.replaceProperty(Scale(scale))
    }

}