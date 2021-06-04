package ca.landonjw.kotlinmon.common

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.common.pokeball.item.PokeBallItem
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ItemRegistry {
    val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Kotlinmon.MODID)

    val POKEBALL: RegistryObject<PokeBallItem> = ITEMS.register("pokeball") { PokeBallItem() }

    fun register() {
        ITEMS.register(FMLJavaModLoadingContext.get().modEventBus)
    }
}