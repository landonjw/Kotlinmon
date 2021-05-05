package ca.landonjw.kotlinmon.common.pokemon.data.species

import ca.landonjw.kotlinmon.Kotlinmon
import ca.landonjw.kotlinmon.common.pokemon.data.species.builder.species
import ca.landonjw.kotlinmon.common.pokemon.data.type.PokemonTypes
import ca.landonjw.kotlinmon.common.util.Registry
import net.minecraft.util.ResourceLocation

object SpeciesRegistry: Registry<String, PokemonSpecies>(allowOverrides = false) {

    val speciesByName: Map<String, PokemonSpecies>
        get() = super.values.toMap()

    val species: List<PokemonSpecies>
        get() = super.values.values.toList()

    override fun get(key: String): PokemonSpecies? = super.get(key.toLowerCase())

    override fun register(key: String, value: PokemonSpecies) = super.register(key.toLowerCase(), value)

    fun register(species: PokemonSpecies) = this.register(species.name, species)

    override fun registerDefaultValues() {
        val krabby = species("Krabby") {
            form("default") {
                model = ResourceLocation(Kotlinmon.MODID, "pokemon/krabby/krabby.pqc")
                baseStats {
                    health = 30
                    attack = 105
                    defence = 90
                    specialAttack = 25
                    specialDefence = 25
                    speed = 50
                }
                types(PokemonTypes.WATER)
            }
        }
        register(krabby)

        val kingler = species("Kingler") {
            form("default") {
                model = ResourceLocation(Kotlinmon.MODID, "pokemon/kingler/kingler.pqc")
                baseStats {
                    health = 55
                    attack = 130
                    defence = 115
                    specialAttack = 50
                    specialDefence = 50
                    speed = 75
                }
                types(PokemonTypes.WATER)
            }
        }
        register(kingler)

        val corphish = species("Corphish") {
            form("default") {
                model = ResourceLocation(Kotlinmon.MODID, "pokemon/corphish/corphish.pqc")
                baseStats {
                    health = 55
                    attack = 130
                    defence = 115
                    specialAttack = 50
                    specialDefence = 50
                    speed = 75
                }
                types(PokemonTypes.WATER)
            }
        }
        register(corphish)

        val sirfetchd = species("Sirfetchd") {
            form("default") {
                model = ResourceLocation(Kotlinmon.MODID, "pokemon/sirfetchd/sirfetchd.pqc")
                baseStats {
                    health = 55
                    attack = 130
                    defence = 115
                    specialAttack = 50
                    specialDefence = 50
                    speed = 75
                }
                types(PokemonTypes.FLYING, PokemonTypes.FIGHTING)
            }
        }
        register(sirfetchd)
    }

}