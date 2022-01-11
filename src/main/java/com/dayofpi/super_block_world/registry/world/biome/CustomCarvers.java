package com.dayofpi.super_block_world.registry.world.biome;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

@SuppressWarnings("SameParameterValue")
public class CustomCarvers {
    public static final ConfiguredCarver<RavineCarverConfig> WIDE_CANYON = registerCarver("super_block_world:wide_canyon", Carver.RAVINE.configure(new RavineCarverConfig(0.05f, UniformHeightProvider.create(YOffset.fixed(63), YOffset.fixed(90)), ConstantFloatProvider.create(3.0f), YOffset.aboveBottom(8), CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.getDefaultState()), UniformFloatProvider.create(-0.125f, 0.125f), new RavineCarverConfig.Shape(UniformFloatProvider.create(0.75f, 1.0f), UniformFloatProvider.create(6.0f, 8.0f), 3, UniformFloatProvider.create(0.75f, 1.0f), 1.0f, 0.0f))));

    private static <WC extends CarverConfig> ConfiguredCarver<WC> registerCarver(String id, ConfiguredCarver<WC> configuredCarver) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_CARVER, id, configuredCarver);
    }
}
