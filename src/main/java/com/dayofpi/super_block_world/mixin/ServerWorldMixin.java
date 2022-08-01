package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.util.StarRainSpawner;
import com.google.common.collect.ImmutableList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.Spawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    private static final ImmutableList<Spawner> spawnerList = ImmutableList.of(new StarRainSpawner());


    @Inject(at=@At("HEAD"), method = "tickSpawners")
    public void tickSpawners(boolean spawnMonsters, boolean spawnAnimals, CallbackInfo ci) {
        for (Spawner spawner : spawnerList) {
            spawner.spawn((ServerWorld)(Object)this, spawnMonsters, spawnAnimals);
        }
    }
}
