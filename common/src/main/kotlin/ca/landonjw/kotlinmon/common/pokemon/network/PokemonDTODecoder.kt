package ca.landonjw.kotlinmon.common.pokemon.network

import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import ca.landonjw.kotlinmon.common.pokemon.PokemonDTO
import net.minecraft.network.PacketBuffer

class PokemonDTODecoder(
    private val speciesRepository: PokemonSpeciesRepository
) {

    fun decode(buf: PacketBuffer): PokemonDTO {
        // Parse species from the packet information or exit out early
        val speciesName = buf.readString()
        val species = speciesRepository[speciesName]
            ?: throw IllegalArgumentException("species $speciesName is not registered")

        // Parse form from the packet information or exit out early
        val formName = buf.readString()
        val form: PokemonForm
        if (formName == "default") {
            form = species.defaultForm
        } else {
            form = species.alternativeForms.firstOrNull { it.name == formName }
                ?: throw IllegalStateException("form $formName is not registered")
        }

        val texture = buf.readString()

        return PokemonDTO(
            species = species,
            form = form,
            texture = if (texture.isNullOrEmpty()) null else texture
        )
    }

}