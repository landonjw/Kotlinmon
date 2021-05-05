package ca.landonjw.kotlinmon.common.init

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.common.pokeball.ItemPokeball
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ItemRegistry {
    val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Kotlinmon.MODID)

    val POKEBALL: RegistryObject<ItemPokeball> = ITEMS.register("pokeball") { ItemPokeball() }

    fun register() {
        ITEMS.register(FMLJavaModLoadingContext.get().modEventBus)
    }
}