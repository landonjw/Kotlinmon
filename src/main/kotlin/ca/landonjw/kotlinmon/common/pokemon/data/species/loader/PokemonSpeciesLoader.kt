package ca.landonjw.kotlinmon.common.pokemon.data.species.loader

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.PokemonType
import com.google.gson.GsonBuilder
import net.minecraft.util.ResourceLocation
import java.io.InputStream

object PokemonSpeciesLoader {

    private val gson = GsonBuilder()
        .disableHtmlEscaping()
        .registerTypeAdapter(PokemonType::class.java, PokemonTypeAdapter())
        .registerTypeAdapter(ResourceLocation::class.java, ResourceLocation.Serializer())
        .create()

    fun load(location: ResourceLocation): PokemonSpecies {
        getResourceStream(location).use { stream ->
            return gson.fromJson(stream.bufferedReader(), PokemonSpecies::class.java)
        }
    }

    private fun getResourceStream(location: ResourceLocation): InputStream {
        val namespace = location.namespace
        val path = location.path
        val stream = Kotlinmon::class.java.getResourceAsStream("/assets/$namespace/$path")
        return stream
    }

}