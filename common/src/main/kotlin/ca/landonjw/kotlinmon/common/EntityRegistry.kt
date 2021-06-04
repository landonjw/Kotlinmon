package ca.landonjw.kotlinmon.common

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.common.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.PokeBallEntity
import ca.landonjw.kotlinmon.common.pokemon.entity.PokemonEntity
import net.minecraft.entity.Entity
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

    val POKEMON: RegistryObject<EntityType<PokemonEntity>> = registerEntity(
        name = "pokemon",
        classification = EntityClassification.MISC,
        factory = { type, world -> PokemonEntity(type, world) },
        builderModifiers = { builder -> builder.size(1f, 1f).immuneToFire() }
    )

    val EMPTY_POKEBALL: RegistryObject<EntityType<EmptyPokeBallEntity>> = registerEntity(
        name = "empty_pokeball",
        classification = EntityClassification.MISC,
        factory = { type, world -> EmptyPokeBallEntity(type, world) },
        builderModifiers = { builder -> builder.size(1f, 1f).immuneToFire() }
    )

    val OCCUPIED_POKEBALL: RegistryObject<EntityType<OccupiedPokeBallEntity>> = registerEntity(
        name = "occupied_pokeball",
        classification = EntityClassification.MISC,
        factory = { type, world -> OccupiedPokeBallEntity(type, world) },
        builderModifiers = { builder -> builder.size(1f, 1f).immuneToFire() }
    )

    inline fun <reified T : Entity> registerEntity(
        name: String,
        classification: EntityClassification,
        crossinline factory: (EntityType<T>, World) -> T,
        builderModifiers: (EntityType.Builder<T>) -> EntityType.Builder<T>
    ): RegistryObject<EntityType<T>> {
        val resourceLoc = ResourceLocation(Kotlinmon.MODID, name)
        val entityFactory: EntityType.IFactory<T> = EntityType.IFactory { type, world ->
            return@IFactory factory(type, world)
        }
        val builder = EntityType.Builder.create(entityFactory, classification)
        val registry = builderModifiers(builder).build(resourceLoc.toString())
        return ENTITIES.register(name) { registry }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityAttributeCreation(event: EntityAttributeCreationEvent) {
        event.put(POKEMON.get(), PokemonEntity.prepareAttributes())
    }

    fun register() {
        ENTITIES.register(FMLJavaModLoadingContext.get().modEventBus)
    }

}