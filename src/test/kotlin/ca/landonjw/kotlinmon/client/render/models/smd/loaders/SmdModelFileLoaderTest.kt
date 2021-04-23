package ca.landonjw.kotlinmon.client.render.models.smd.loaders

import ca.landonjw.kotlinmon.Kotlinmon
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3f
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SmdModelFileLoaderTest {

    private val kinglerModel = ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/model/kingler.smd")

    @Test
    fun `load doesn't cause exception`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
    }

    @Test
    fun `kingler model has correct smd version`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        assertEquals(modelFile.version, 1)
    }

    @Test
    fun `kingler model has correct amount of bones`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        assertEquals(modelFile.boneLocations.size, 23)
    }

    @Test
    fun `first bone location is always bone 0`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val boneLoc = modelFile.boneLocations[0]
        assertEquals(boneLoc.boneId, 0)
    }

    @Test
    fun `first bone location always has 0 location vector`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val boneLoc = modelFile.boneLocations[0]
        assertEquals(boneLoc.location.x, 0f)
        assertEquals(boneLoc.location.y, 0f)
        assertEquals(boneLoc.location.z, 0f)
    }

    @Test
    fun `first bone location always has 0 orientation vector`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val boneLoc = modelFile.boneLocations.first()
        assertEquals(boneLoc.orientation.x, 0f)
        assertEquals(boneLoc.orientation.y, 0f)
        assertEquals(boneLoc.orientation.z, 0f)
    }

    @Test
    fun `second bone location is correct for kingler model`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val boneLoc = modelFile.boneLocations[1]
        assertEquals(boneLoc.location.x, 0f)
        assertEquals(boneLoc.location.y, 0f)
        assertEquals(boneLoc.location.z, 0f)
    }

    @Test
    fun `last bone location is correct for kingler model`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val boneLoc = modelFile.boneLocations.last()
        assertEquals(boneLoc.location.x, 0f)
        assertEquals(boneLoc.location.y, 1.783186f)
        assertEquals(boneLoc.location.z, -0f)
    }

    @Test
    fun `second bone orientation is correct for kingler model`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val boneLoc = modelFile.boneLocations[1]
        assertEquals(boneLoc.orientation.x, 1.570796f)
        assertEquals(boneLoc.orientation.y, -0f)
        assertEquals(boneLoc.orientation.z, 0f)
    }

    @Test
    fun `last bone orientation is correct for kingler model`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val boneLoc = modelFile.boneLocations.last()
        assertEquals(boneLoc.orientation.x, -1.465789f)
        assertEquals(boneLoc.orientation.y, 0.266362f)
        assertEquals(boneLoc.orientation.z, -0.744819f)
    }

    @Test
    fun `material for kingler is correct`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        modelFile.polygonMesh.forEach { assertEquals(it.material, "Material.002") }
    }

    @Test
    fun `first mesh triangle has correct vertex positions`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val firstMeshTriangle = modelFile.polygonMesh.first()
        assertEquals(firstMeshTriangle.vertex1.position, Vector3f(-5.517581f, -3.337905f, 8.317562f))
        assertEquals(firstMeshTriangle.vertex2.position, Vector3f(-5.631848f, -3.375354f, 7.572900f))
        assertEquals(firstMeshTriangle.vertex3.position, Vector3f(-4.285896f, -3.701882f, 7.699278f))
    }
    @Test
    fun `first mesh triangle has correct vertex normal`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val firstMeshTriangle = modelFile.polygonMesh.first()
        assertEquals(firstMeshTriangle.vertex1.normal, Vector3f(-0.731437f, -0.562975f, 0.384716f))
        assertEquals(firstMeshTriangle.vertex2.normal, Vector3f(-0.756584f, -0.636158f, 0.151128f))
        assertEquals(firstMeshTriangle.vertex3.normal, Vector3f(0.678671f, -0.724754f, -0.118809f))
    }

    @Test
    fun `first mesh triangle has correct vertex uv map`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val firstMeshTriangle = modelFile.polygonMesh.first()
        assertEquals(firstMeshTriangle.vertex1.uvMap.a, 0.374970f)
        assertEquals(firstMeshTriangle.vertex1.uvMap.b, 1 - 0.601654f)

        assertEquals(firstMeshTriangle.vertex2.uvMap.a, 0.362360f)
        assertEquals(firstMeshTriangle.vertex2.uvMap.b, 1 - 0.610679f)

        assertEquals(firstMeshTriangle.vertex3.uvMap.a, 0.347226f)
        assertEquals(firstMeshTriangle.vertex3.uvMap.b, 1 - 0.567103f)
    }

    @Test
    fun `first mesh triangle has correct vertex link numbers`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val firstMeshTriangle = modelFile.polygonMesh.first()
        assertEquals(firstMeshTriangle.vertex1.links?.size, 1)
        assertEquals(firstMeshTriangle.vertex2.links?.size, 1)
        assertEquals(firstMeshTriangle.vertex3.links?.size, 1)
    }

    @Test
    fun `first mesh triangle has correct vertex weights`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val firstMeshTriangle = modelFile.polygonMesh.first()
        assertEquals(firstMeshTriangle.vertex1.links?.get(13), 1f)
        assertEquals(firstMeshTriangle.vertex2.links?.get(13), 1f)
        assertEquals(firstMeshTriangle.vertex3.links?.get(13), 1f)
    }

    @Test
    fun `last mesh triangle has correct vertex positions`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val firstMeshTriangle = modelFile.polygonMesh.last()
        assertEquals(firstMeshTriangle.vertex1.position, Vector3f(3.549457f, -3.962313f, 7.360795f))
        assertEquals(firstMeshTriangle.vertex2.position, Vector3f(3.789595f, -3.962313f, 7.873519f))
        assertEquals(firstMeshTriangle.vertex3.position, Vector3f(3.668145f, -2.998441f, 6.475130f))
    }

    @Test
    fun `last mesh triangle has correct vertex normal`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val firstMeshTriangle = modelFile.polygonMesh.last()
        assertEquals(firstMeshTriangle.vertex1.normal, Vector3f(-0.545213f, -0.616047f, 0.568468f))
        assertEquals(firstMeshTriangle.vertex2.normal, Vector3f(-0.790826f, -0.446852f, 0.418165f))
        assertEquals(firstMeshTriangle.vertex3.normal, Vector3f(-0.271218f, 0.030335f, 0.962004f))
    }

    @Test
    fun `last mesh triangle has correct vertex uv map`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val firstMeshTriangle = modelFile.polygonMesh.last()
        assertEquals(firstMeshTriangle.vertex1.uvMap.a, 0.620653f)
        assertEquals(firstMeshTriangle.vertex1.uvMap.b, 1 - 0.077836f)

        assertEquals(firstMeshTriangle.vertex2.uvMap.a, 0.604916f)
        assertEquals(firstMeshTriangle.vertex2.uvMap.b, 1 - 0.077127f)

        assertEquals(firstMeshTriangle.vertex3.uvMap.a, 0.621982f)
        assertEquals(firstMeshTriangle.vertex3.uvMap.b, 1 - 0.048332f)
    }

    @Test
    fun `last mesh triangle has correct vertex link numbers`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val firstMeshTriangle = modelFile.polygonMesh.last()
        assertEquals(firstMeshTriangle.vertex1.links?.size, 1)
        assertEquals(firstMeshTriangle.vertex2.links?.size, 1)
        assertEquals(firstMeshTriangle.vertex3.links?.size, 1)
    }

    @Test
    fun `last mesh triangle has correct vertex weights`() {
        val modelFile = SmdModelFileLoader.load(kinglerModel)
        val firstMeshTriangle = modelFile.polygonMesh.last()
        assertEquals(firstMeshTriangle.vertex1.links?.get(9), 1f)
        assertEquals(firstMeshTriangle.vertex2.links?.get(9), 1f)
        assertEquals(firstMeshTriangle.vertex3.links?.get(9), 1f)
    }

}