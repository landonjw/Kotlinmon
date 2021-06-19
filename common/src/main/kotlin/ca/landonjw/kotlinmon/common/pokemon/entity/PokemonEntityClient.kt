package ca.landonjw.kotlinmon.common.pokemon.entity

import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import net.minecraft.network.datasync.EntityDataManager

class PokemonEntityClient(
    private val dataManager: EntityDataManager,
    private val speciesRepository: PokemonSpeciesRepository
) {

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

}