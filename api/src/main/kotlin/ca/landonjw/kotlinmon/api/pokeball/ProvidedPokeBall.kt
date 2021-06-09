package ca.landonjw.kotlinmon.api.pokeball

import ca.landonjw.kotlinmon.api.pokeball.capture.modifiers.*
import ca.landonjw.kotlinmon.api.pokemon.Pokemon
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.ResourceLocation
import ca.landonjw.kotlinmon.api.pokeball.PokeBall as IPokeBall

enum class ProvidedPokeBall: IPokeBall {
    CherishBall,
    DiveBall,
    DreamBall,
    DuskBall,
    FastBall(FastBallCatchRateStrategy()),
    FriendBall,
    GreatBall(BasicCaptureModifier(1.5f)),
    HealBall,
    HeavyBall,
    LevelBall,
    LoveBall,
    LureBall,
    LuxuryBall,
    MasterBall(GuaranteedCatchRateStrategy()),
    MoonBall,
    NestBall,
    NetBall(NetBallCatchRateStrategy()),
    PokeBall,
    PremierBall,
    QuickBall,
    RepeatBall,
    SafariBall,
    SportBall,
    TimerBall,
    UltraBall(BasicCaptureModifier(2.0f));

    override val modelLocation: ResourceLocation
    private val catchRateStrategy: PokeBallCatchRateStrategy

    constructor(catchRateStrategy: PokeBallCatchRateStrategy? = null) {
        modelLocation = getResourceForPokeBall()
        this.catchRateStrategy = catchRateStrategy ?: BasicCaptureModifier(1.0f)
    }

    private fun getResourceForPokeBall(): ResourceLocation {
        val name = this.name.toLowerCase()
        return ResourceLocation("kotlinmon", "pokeballs/$name/$name.pqc")
    }

    override fun getCatchRate(thrower: ServerPlayerEntity, pokemon: Pokemon): Int {
        return catchRateStrategy.getCatchRate(thrower, pokemon)
    }

}