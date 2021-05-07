package ca.landonjw.kotlinmon.api.pokemon.data.species

import ca.landonjw.kotlinmon.api.pokemon.data.species.form.PokemonForm

class PokemonSpecies(
    val name: String,
    val catchRate: Int,
    val defaultForm: PokemonForm,
    val alternativeForms: List<PokemonForm>
) {

    fun hasAlternativeForms(): Boolean = alternativeForms.isNotEmpty()

    companion object {

        operator fun invoke(init: Builder.() -> Unit): PokemonSpecies {
            val builder = Builder()
            builder.init()
            return builder.build()
        }

        class Builder internal constructor() {
            var name: String? = null
            var catchRate: Int? = null
            var defaultForm: PokemonForm? = null
            val alternativeForms: MutableList<PokemonForm> = mutableListOf()

            fun defaultForm(init: PokemonForm.Companion.Builder.() -> Unit) {
                val builder = PokemonForm.Companion.Builder()
                builder.init()
                builder.name = builder.name ?: "default"
                defaultForm = builder.build()
            }

            fun form(name: String, init: PokemonForm.Companion.Builder.() -> Unit) {
                val builder = PokemonForm.Companion.Builder()
                builder.init()
                builder.name = name
                alternativeForms.add(builder.build())
            }

            fun build(): PokemonSpecies {
                validate()
                return PokemonSpecies(name!!, catchRate!!, defaultForm!!, alternativeForms)
            }

            private fun validate() {
                if (name.isNullOrEmpty()) throw IllegalStateException("species must have a name defined")
                if (defaultForm == null) throw IllegalStateException("species must have a default form defined")
            }
        }

    }

}