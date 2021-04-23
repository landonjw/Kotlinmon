package ca.landonjw.kotlinmon.client.render.models.smd.loaders

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import io.mockk.mockk
import net.minecraft.util.ResourceLocation
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SmdModelAnimationFileLoaderTest {

    private val kinglerModel: SmdModel = mockk()
    private val kinglerWalkAnimation = ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/animations/walk.smd")

    @Test
    fun `load doesnt cause exception`() {
        val animation = SmdCache.getModelAnimation(kinglerWalkAnimation, kinglerModel)
    }

}