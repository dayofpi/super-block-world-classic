package com.dayofpi.super_block_world.mixin.main.world;

import com.dayofpi.super_block_world.common.util.mixin_aid.SeedSupplier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoiseChunkGenerator.class)
// source: https://github.com/Haven-King/seedy-behavior
public abstract class NoiseChunkGeneratorMixin extends ChunkGenerator {
    private static long LAST_SEED = SeedSupplier.MARKER;

    public NoiseChunkGeneratorMixin(BiomeSource biomeSource, StructuresConfig structuresConfig) {
        super(biomeSource, structuresConfig);
    }

    @Redirect(method = "method_28550(Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;)Lcom/mojang/datafixers/kinds/App;", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/codecs/PrimitiveCodec;fieldOf(Ljava/lang/String;)Lcom/mojang/serialization/MapCodec;", ordinal = 0))
    private static MapCodec<Long> giveUsRandomSeeds(PrimitiveCodec<Long> codec, final String name) {
        return codec.fieldOf(name).orElseGet(SeedSupplier::getSeed);
    }

    @ModifyVariable(method = "<init>(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/world/biome/source/BiomeSource;Lnet/minecraft/world/biome/source/BiomeSource;JLjava/util/function/Supplier;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/gen/chunk/NoiseChunkGenerator;seed:J", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER), ordinal = 0, argsOnly = true)
    private long replaceSeed(long seed) {
        if (seed == SeedSupplier.MARKER) {
            return LAST_SEED;
        } else {
            LAST_SEED = seed;
        }

        return seed;
    }
}