package ca.landonjw.kotlinmon.common.pokemon.entity

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import ca.landonjw.kotlinmon.client.pokemon.getModelLocation
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.repository.ModelRepository
import net.minecraft.network.datasync.EntityDataManager

class PokemonEntityClient(private val dataManager: EntityDataManager) {

    private val speciesRepository: PokemonSpeciesRepository by KotlinmonDI.inject()
    private val modelRepository: ModelRepository by KotlinmonDI.inject(tag = "async")

    val species: PokemonSpecies
        get() = speciesRepository[dataManager.get(DefaultPokemonEntity.dwSpecies)] ?: throw IllegalStateException()

    val form: PokemonForm
        get() {
            val formOrdinal = dataManager.get(DefaultPokemonEntity.dwForm)
            return if (formOrdinal == 0) species.defaultForm else species.alternativeForms[formOrdinal - 1]
        }

    val texture: String?
        get() {
            val texture = dataManager.get(DefaultPokemonEntity.dwTexture)
            return if (texture.isNullOrEmpty()) null else texture
        }

    val model: SmdModel?
        get() {
            return try {
                 modelRepository[getModelLocation(species, form)]
            }
            catch (e: IllegalStateException) {
                null
            }
        }

}