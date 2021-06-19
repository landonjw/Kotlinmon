package ca.landonjw.kotlinmon.common

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.common.pokeball.item.PokeBallItem
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

class ItemRegistry(
    private val pokeballProvider: () -> PokeBallItem
) {

    val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Kotlinmon.MOD_ID)

    val POKEBALL: RegistryObject<PokeBallItem> = ITEMS.register("pokeball") { pokeballProvider() }

    fun register() {
        ITEMS.register(FMLJavaModLoadingContext.get().modEventBus)
    }

}