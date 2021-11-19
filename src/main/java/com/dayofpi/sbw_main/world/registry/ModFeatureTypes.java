package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.world.feature.types.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.DiskFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class ModFeatureTypes {
    public static final Feature<CustomHugeMushroomFeatureConfig> HUGE_MUSHROOM = new CustomHugeMushroomFeature(CustomHugeMushroomFeatureConfig.CODEC);
    public static final Feature<DiskFeatureConfig> DISK = new CustomUnderwaterDiskFeature(DiskFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> COLUMN_CORAL = new ColumnCoralFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> JELLYBEAM = new JellybeamFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> UNDERWATER_PIPE = new UnderwaterPipeFeature(DefaultFeatureConfig.CODEC);

    public static void registerFeatureTypes() {
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "huge_mushroom"), HUGE_MUSHROOM);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "disk"), DISK);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "column_coral"), COLUMN_CORAL);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "jellybeam"), JELLYBEAM);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "underwater_pipe"), UNDERWATER_PIPE);
    }
}
