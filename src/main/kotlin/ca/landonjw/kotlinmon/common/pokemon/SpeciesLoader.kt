package ca.landonjw.kotlinmon.common.pokemon

import ca.landonjw.kotlinmon.common.pokemon.data.species.PokemonSpecies
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.minecraft.util.ResourceLocation

object SpeciesLoader {

    private val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create()

    fun load(location: ResourceLocation): PokemonSpecies {
        TODO()
    }

}