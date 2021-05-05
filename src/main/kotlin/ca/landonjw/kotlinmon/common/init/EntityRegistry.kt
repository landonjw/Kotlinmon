package ca.landonjw.kotlinmon.common.init

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.common.pokeball.PokeballEntity
import ca.landonjw.kotlinmon.common.pokemon.PokemonEntity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.util.ResourceLocation
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

    val POKEMON: RegistryObject<EntityType<PokemonEntity>> = registerPokemon()
    val POKEBALL: RegistryObject<EntityType<PokeballEntity>> = registerPokeball()

    fun registerPokemon(): RegistryObject<EntityType<PokemonEntity>> {
        val resourceLoc = ResourceLocation(Kotlinmon.MODID, "pokemon")
        val factory: EntityType.IFactory<PokemonEntity> = EntityType.IFactory { type, world ->
            return@IFactory PokemonEntity(type, world)
        }
        val builder = EntityType.Builder.create(factory, EntityClassification.MISC)
        val registry = builder
            .size(1f, 1f)
            .immuneToFire()
            .build(resourceLoc.toString())

        return ENTITIES.register("pokemon") { registry }
    }

    fun registerPokeball(): RegistryObject<EntityType<PokeballEntity>> {
        val resourceLoc = ResourceLocation(Kotlinmon.MODID, "pokeball")
        val factory: EntityType.IFactory<PokeballEntity> = EntityType.IFactory { type, world ->
            return@IFactory PokeballEntity(type, world)
        }
        val builder = EntityType.Builder.create(factory, EntityClassification.MISC)
        val registry = builder
            .size(1f, 1f)
            .immuneToFire()
            .build(resourceLoc.toString())

        return ENTITIES.register("pokeball") { registry }
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