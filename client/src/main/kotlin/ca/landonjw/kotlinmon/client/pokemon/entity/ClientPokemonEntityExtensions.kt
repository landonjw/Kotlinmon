package ca.landonjw.kotlinmon.client.pokemon.entity

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.client.pokemon.getModelLocation
import ca.landonjw.kotlinmon.client.render.models.smd.SmdModel
import ca.landonjw.kotlinmon.client.render.models.smd.repository.ModelRepository
import ca.landonjw.kotlinmon.common.pokemon.entity.DefaultPokemonEntity
import org.kodein.di.instance

fun DefaultPokemonEntity.getModel(): SmdModel? {
    val modelRepository: ModelRepository by Kotlinmon.DI.instance("async")

    return try {
        val species = this.clientComponent.species
        val form = this.clientComponent.form
        modelRepository[getModelLocation(species, form)]
    } catch (e: IllegalStateException) { null }
}