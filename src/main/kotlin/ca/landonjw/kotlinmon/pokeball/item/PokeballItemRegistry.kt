package ca.landonjw.kotlinmon.pokeball.item

import ca.landonjw.kotlinmon.Kotlinmon
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object PokeballItemRegistry {
    private val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Kotlinmon.MODID)

    val POKEBALL = ITEMS.register("pokeball") { PokeballItem() }

    fun register() {
        ITEMS.register(FMLJavaModLoadingContext.get().modEventBus)
    }
}