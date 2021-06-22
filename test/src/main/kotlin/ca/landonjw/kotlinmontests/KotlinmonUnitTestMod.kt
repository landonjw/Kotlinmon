package ca.landonjw.kotlinmontests

import net.minecraft.command.Commands
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod("kotlinmontests")
class KotlinmonUnitTestMod {

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun registerTestRunner(event: RegisterCommandsEvent) {
        val command = Commands.literal("runtests")
            .executes(RunUnitTestCommand())
            .requires { it.hasPermission(0) }
        event.dispatcher.register(command)
    }

}