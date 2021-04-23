package ca.landonjw.kotlinmon.init

import ca.landonjw.kotlinmon.Kotlinmon
//import ca.landonjw.kotlinmon.pokeball.entity.PokeballEntity
import ca.landonjw.kotlinmon.tangrowth.PokemonEntity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

@Mod.EventBusSubscriber(modid = Kotlinmon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object EntityRegistry {
    val ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Kotlinmon.MODID)

//    val POKEBALL: RegistryObject<EntityType<PokeballEntity>> =
//        createPokeball("pokeball") { type, world -> PokeballEntity(type, world) }

    val POKEMON: RegistryObject<EntityType<PokemonEntity>> = registerPokemon()

//    private inline fun <reified T : PokeballEntity> createPokeball(
//        name: String,
//        noinline supplier: (EntityType<T>, World) -> T
//    ): RegistryObject<EntityType<T>> {
//        val resourceLoc = ResourceLocation(Kotlinmon.MODID, name)
//        val builder = EntityType.Builder.create<T>(EntityType.IFactory(supplier), EntityClassification.MISC)
//        val registry = builder
//            .size(3f, 3f)
//            .immuneToFire()
//            .build(resourceLoc.toString())
//
//        return ENTITIES.register(name) { registry }
//    }

    fun registerPokemon(): RegistryObject<EntityType<PokemonEntity>> {
        val resourceLoc = ResourceLocation(Kotlinmon.MODID, "pokemon")
        val factory: EntityType.IFactory<PokemonEntity> = EntityType.IFactory { type, world ->
            return@IFactory PokemonEntity(type, world)
        }
        val builder = EntityType.Builder.create<PokemonEntity>(factory, EntityClassification.MISC)
        val registry = builder
            .size(3f, 3f)
            .immuneToFire()
            .build(resourceLoc.toString())

        return ENTITIES.register("pokemon") { registry }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityAttributeCreation(event: EntityAttributeCreationEvent) {
        Kotlinmon.LOGGER.info("Registering entity attributes")
        event.put(POKEMON.get(), PokemonEntity.prepareAttributes())
    }

    fun register() {
        ENTITIES.register(FMLJavaModLoadingContext.get().modEventBus)
    }
}