package ca.landonjw.kotlinmon.common.pokemon.data.species.loader

import ca.landonjw.kotlinmon.KotlinmonDI
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.PokemonType
import ca.landonjw.kotlinmon.api.pokemon.data.species.type.PokemonTypeRepository
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class PokemonTypeAdapter: TypeAdapter<PokemonType>() {

    private val typeRepository: PokemonTypeRepository by KotlinmonDI.inject()

    override fun write(out: JsonWriter?, value: PokemonType?) {
        if (value == null || out == null) return
        out.value(value.name)
    }

    override fun read(reader: JsonReader?): PokemonType {
        val typeStr = reader?.nextString() ?: throw IllegalArgumentException("expected type")
        return typeRepository[typeStr] ?: throw IllegalArgumentException("type $typeStr not registered")
    }

}