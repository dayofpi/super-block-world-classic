package com.dayofpi.super_block_world.mixin.main.world;

import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(ChunkGenerator.class)
public interface ChunkGeneratorAccessor {
    @Mutable
    @Final
    @Accessor("worldSeed")
    void setWorldSeed(long worldSeed);
}
