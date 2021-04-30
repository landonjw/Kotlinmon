package ca.landonjw.kotlinmon.pokemon

import ca.landonjw.kotlinmon.pokemon.species.PokemonSpecies
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