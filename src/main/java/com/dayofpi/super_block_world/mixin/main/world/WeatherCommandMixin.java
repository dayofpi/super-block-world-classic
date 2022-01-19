package com.dayofpi.super_block_world.mixin.main.world;

import com.dayofpi.super_block_world.world.MushroomKingdom;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.WeatherCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatherCommand.class)
public class WeatherCommandMixin {

    @Inject(at=@At("TAIL"), method = "executeClear")
    private static void executeClear(ServerCommandSource source, int duration, CallbackInfoReturnable<Integer> info) {
        if (source.getWorld().getRegistryKey() == MushroomKingdom.WORLD_KEY) {
            ServerWorld world = source.getServer().getWorld(World.OVERWORLD);
            if (world != null)
                world.setWeather(duration, 0, false, false);
        }
    }

    @Inject(at=@At("TAIL"), method = "executeRain")
    private static void executeRain(ServerCommandSource source, int duration, CallbackInfoReturnable<Integer> info) {
        if (source.getWorld().getRegistryKey() == MushroomKingdom.WORLD_KEY) {
            ServerWorld world = source.getServer().getWorld(World.OVERWORLD);
            if (world != null)
                world.setWeather(0, duration, true, false);
        }
    }

    @Inject(at=@At("TAIL"), method = "executeThunder")
    private static void executeThunder(ServerCommandSource source, int duration, CallbackInfoReturnable<Integer> info) {
        if (source.getWorld().getRegistryKey() == MushroomKingdom.WORLD_KEY) {
            ServerWorld world = source.getServer().getWorld(World.OVERWORLD);
            if (world != null)
                world.setWeather(0, duration, true, true);
        }
    }
}
