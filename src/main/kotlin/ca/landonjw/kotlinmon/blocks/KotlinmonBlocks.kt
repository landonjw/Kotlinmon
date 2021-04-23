package ca.landonjw.kotlinmon.blocks

import ca.landonjw.kotlinmon.Kotlinmon
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object KotlinmonBlocks {
    private val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Kotlinmon.MODID)

    val TEST_BLOCK = BLOCKS.register("testblock") { TestBlock() }

    fun register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().modEventBus)
    }
}