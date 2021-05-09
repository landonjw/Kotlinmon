package ca.landonjw.kotlinmon.client.render.models

import net.minecraft.block.BlockState
import net.minecraft.client.renderer.model.IBakedModel
import net.minecraft.util.Direction
import java.util.*

/* TODO: I KNOW there is a better way to do this, but it's going to end up in a rabbit hole
 * For those courageous, I believe the correct avenue would be to implement a custom model loader.
 * See: https://discord.com/channels/313125603924639766/454376090362970122/797892085998813234
 * in The Forge Project discord (https://discord.gg/UvedJ9m) for a potential clue.
 *
 * - landonjw
 */
class CustomModelDecorator(private val model: IBakedModel) : IBakedModel {

    override fun getQuads(state: BlockState?, side: Direction?, rand: Random) = model.getQuads(state, side, rand)

    override fun isAmbientOcclusion() = model.isAmbientOcclusion

    override fun getParticleTexture() =  model.particleTexture

    override fun isGui3d() = model.isGui3d

    override fun isSideLit() = model.isSideLit

    override fun isBuiltInRenderer() = true

    override fun getOverrides() = model.overrides

}