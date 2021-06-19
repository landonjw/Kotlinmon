package ca.landonjw.kotlinmon.client.pokemon

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.api.pokemon.data.species.PokemonSpecies
import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm
import net.minecraft.util.ResourceLocation

// TODO: All of these should be accommodated for custom textures later on. Can't do it right now since not sure on impl - landonjw

fun getSpriteLocation(species: PokemonSpecies, form: PokemonForm, texture: String? = null): ResourceLocation {
    val texturePath = texture ?: form.name
    val path = "pokemon/${species.name}/forms/${form.name}/sprites/$texturePath.png".toLowerCase()
    return ResourceLocation(Kotlinmon.MOD_ID, path)
}

fun getModelTextureLocation(species: PokemonSpecies, form: PokemonForm, texture: String? = null): ResourceLocation {
    val texturePath = texture ?: form.name
    val path = "pokemon/${species.name}/forms/${form.name}/model/textures/$texturePath.png".toLowerCase()
    return ResourceLocation(Kotlinmon.MOD_ID, path)
}

fun getModelLocation(species: PokemonSpecies, form: PokemonForm): ResourceLocation {
    val path = "pokemon/${species.name}/forms/${form.name}/model/${form.name}.pqc".toLowerCase()
    return ResourceLocation(Kotlinmon.MOD_ID, path)
}