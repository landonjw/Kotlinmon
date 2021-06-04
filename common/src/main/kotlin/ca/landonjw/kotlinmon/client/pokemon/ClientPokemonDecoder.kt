package ca.landonjw.kotlinmon.client.pokemon

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpeciesRepository
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import net.minecraft.network.PacketBuffer

class ClientPokemonDecoder {

    private val speciesRepository: PokemonSpeciesRepository by KotlinmonDI.inject()

    fun decode(buf: PacketBuffer): ClientPokemonData {
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

        return ClientPokemonData(
            species = species,
            form = form,
            texture = if (texture.isNullOrEmpty()) null else texture
        )
    }

}