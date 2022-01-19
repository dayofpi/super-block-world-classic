package com.dayofpi.super_block_world.world;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.TrapezoidFloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

public class CustomCarvers {
    public static final ConfiguredCarver<CaveCarverConfig> CAVE = registerCarver("super_block_world:cave", Carver.CAVE.configure(new CaveCarverConfig(0.2f, UniformHeightProvider.create(YOffset.aboveBottom(8), YOffset.fixed(180)), UniformFloatProvider.create(0.2f, 0.9f), YOffset.fixed(-16), CarverDebugConfig.create(false, Blocks.CRIMSON_BUTTON.getDefaultState()), UniformFloatProvider.create(0.7f, 1.4f), UniformFloatProvider.create(0.8f, 1.3f), UniformFloatProvider.create(-1.0f, -0.4f))));
    public static final ConfiguredCarver<CaveCarverConfig> CAVE_EXTRA_UNDERGROUND = registerCarver("super_block_world:cave_extra_underground", Carver.CAVE.configure(new CaveCarverConfig(0.07f, UniformHeightProvider.create(YOffset.aboveBottom(8), YOffset.fixed(47)), UniformFloatProvider.create(0.1f, 0.9f), YOffset.fixed(-16), CarverDebugConfig.create(false, Blocks.OAK_BUTTON.getDefaultState()), UniformFloatProvider.create(0.7f, 1.4f), UniformFloatProvider.create(0.8f, 1.3f), UniformFloatProvider.create(-1.0f, -0.4f))));
    public static final ConfiguredCarver<RavineCarverConfig> CANYON = registerCarver("super_block_world:canyon", Carver.RAVINE.configure(new RavineCarverConfig(0.01f, UniformHeightProvider.create(YOffset.fixed(10), YOffset.fixed(67)), ConstantFloatProvider.create(3.0f), YOffset.fixed(-16), CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.getDefaultState()), UniformFloatProvider.create(-0.125f, 0.125f), new RavineCarverConfig.Shape(UniformFloatProvider.create(0.75f, 1.0f), TrapezoidFloatProvider.create(0.0f, 6.0f, 2.0f), 3, UniformFloatProvider.create(0.75f, 1.0f), 1.0f, 0.0f))));
    public static final ConfiguredCarver<RavineCarverConfig> WIDE_CANYON = registerCarver("super_block_world:wide_canyon", Carver.RAVINE.configure(new RavineCarverConfig(0.05f, UniformHeightProvider.create(YOffset.fixed(63), YOffset.fixed(90)), ConstantFloatProvider.create(3.0f), YOffset.fixed(-16), CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.getDefaultState()), UniformFloatProvider.create(-0.125f, 0.125f), new RavineCarverConfig.Shape(UniformFloatProvider.create(0.75f, 1.0f), UniformFloatProvider.create(6.0f, 8.0f), 3, UniformFloatProvider.create(0.75f, 1.0f), 1.0f, 0.0f))));

    private static <WC extends CarverConfig> ConfiguredCarver<WC> registerCarver(String id, ConfiguredCarver<WC> configuredCarver) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_CARVER, id, configuredCarver);
    }
}
