package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.main.world.dimension.ModBiomeSource;
import com.dayofpi.super_block_world.main.world.dimension.MushroomKingdom;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class DimensionOptions {
    @Shadow @Final public static RegistryKey<DimensionType> THE_NETHER_REGISTRY_KEY;

    @Shadow @Final public static RegistryKey<DimensionType> THE_END_REGISTRY_KEY;

    @Inject(at=@At("HEAD"), method = "createDefaultDimensionOptions(Lnet/minecraft/util/registry/DynamicRegistryManager;JZ)Lnet/minecraft/util/registry/SimpleRegistry;")
    private static void createDefaultDimensionOptions(DynamicRegistryManager registryManager, long seed, boolean bl, CallbackInfoReturnable<SimpleRegistry<net.minecraft.world.dimension.DimensionOptions>> info) {
        SimpleRegistry<net.minecraft.world.dimension.DimensionOptions> simpleRegistry = new SimpleRegistry<net.minecraft.world.dimension.DimensionOptions>(Registry.DIMENSION_KEY, Lifecycle.experimental());
        Registry<DimensionType> registry = registryManager.get(Registry.DIMENSION_TYPE_KEY);
        Registry<Biome> registry2 = registryManager.get(Registry.BIOME_KEY);
        Registry<ChunkGeneratorSettings> registry3 = registryManager.get(Registry.CHUNK_GENERATOR_SETTINGS_KEY);
        Registry<DoublePerlinNoiseSampler.NoiseParameters> registry4 = registryManager.get(Registry.NOISE_WORLDGEN);
        simpleRegistry.add(net.minecraft.world.dimension.DimensionOptions.NETHER, new net.minecraft.world.dimension.DimensionOptions(() -> registry.getOrThrow(THE_NETHER_REGISTRY_KEY), new NoiseChunkGenerator(registry4, MultiNoiseBiomeSource.Preset.NETHER.getBiomeSource(registry2, bl), seed, () -> registry3.getOrThrow(ChunkGeneratorSettings.NETHER))), Lifecycle.stable());
        simpleRegistry.add(MushroomKingdom.DIMENSION_KEY, new net.minecraft.world.dimension.DimensionOptions(() -> registry.getOrThrow(MushroomKingdom.DIMENSION_TYPE_KEY), new NoiseChunkGenerator(registry4, ModBiomeSource.MUSHROOM_KINGDOM.getBiomeSource(registry2, bl), seed, () -> registry3.getOrThrow(MushroomKingdom.GENERATOR_SETTINGS_KEY))), Lifecycle.stable());
        simpleRegistry.add(net.minecraft.world.dimension.DimensionOptions.END, new net.minecraft.world.dimension.DimensionOptions(() -> registry.getOrThrow(THE_END_REGISTRY_KEY), new NoiseChunkGenerator(registry4, new TheEndBiomeSource(registry2, seed), seed, () -> registry3.getOrThrow(ChunkGeneratorSettings.END))), Lifecycle.stable());
        info.setReturnValue(simpleRegistry);
    }
}
