package ca.landonjw.kotlinmon.common

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.api.pokeball.entity.EmptyPokeBallEntity
import ca.landonjw.kotlinmon.api.pokeball.entity.OccupiedPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultEmptyPokeBallEntity
import ca.landonjw.kotlinmon.common.pokeball.entity.DefaultOccupiedPokeBallEntity
import ca.landonjw.kotlinmon.common.pokemon.entity.DefaultPokemonEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

@Mod.EventBusSubscriber(modid = Kotlinmon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
class EntityRegistry(
    private val pokemonEntityFactory: (EntityFactoryParams<DefaultPokemonEntity>) -> DefaultPokemonEntity,
    private val emptyPokeBallEntityFactory: (EntityFactoryParams<DefaultEmptyPokeBallEntity>) -> DefaultEmptyPokeBallEntity,
    private val occupiedPokeBallEntityFactory: (EntityFactoryParams<DefaultOccupiedPokeBallEntity>) -> DefaultOccupiedPokeBallEntity
) {

    val ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Kotlinmon.MOD_ID)

    val POKEMON: RegistryObject<EntityType<DefaultPokemonEntity>> = registerEntity(
        name = "pokemon",
        classification = EntityClassification.MISC,
        factory = { type, world -> pokemonEntityFactory(EntityFactoryParams(type, world)) },
        builderModifiers = { builder -> builder.size(1f, 1f).immuneToFire() }
    )

    val EMPTY_POKEBALL: RegistryObject<EntityType<DefaultEmptyPokeBallEntity>> = registerEntity(
        name = "empty_pokeball",
        classification = EntityClassification.MISC,
        factory = { type, world -> emptyPokeBallEntityFactory(EntityFactoryParams(type, world)) },
        builderModifiers = { builder -> builder.size(1f, 1f).immuneToFire() }
    )

    val OCCUPIED_POKEBALL: RegistryObject<EntityType<DefaultOccupiedPokeBallEntity>> = registerEntity(
        name = "occupied_pokeball",
        classification = EntityClassification.MISC,
        factory = { type, world -> occupiedPokeBallEntityFactory(EntityFactoryParams(type, world)) },
        builderModifiers = { builder -> builder.size(1f, 1f).immuneToFire() }
    )

    inline fun <reified T : Entity> registerEntity(
        name: String,
        classification: EntityClassification,
        crossinline factory: (EntityType<T>, World) -> T,
        builderModifiers: (EntityType.Builder<T>) -> EntityType.Builder<T>
    ): RegistryObject<EntityType<T>> {
        val resourceLoc = ResourceLocation(Kotlinmon.MOD_ID, name)
        val entityFactory: EntityType.IFactory<T> = EntityType.IFactory { type, world ->
            return@IFactory factory(type, world)
        }
        val builder = EntityType.Builder.create(entityFactory, classification)
        val registry = builderModifiers(builder).build(resourceLoc.toString())
        return ENTITIES.register(name) { registry }
    }

    fun registryAttributes(event: EntityAttributeCreationEvent) {
        println("onEntityAttributeCreation")
        event.put(POKEMON.get(), DefaultPokemonEntity.prepareAttributes())
    }

    fun register() {
        ENTITIES.register(FMLJavaModLoadingContext.get().modEventBus)
    }

}

data class EntityFactoryParams<T : Entity>(
    val type: EntityType<T>,
    val world: World
)