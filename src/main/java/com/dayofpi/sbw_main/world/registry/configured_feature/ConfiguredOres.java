package com.dayofpi.sbw_main.world.registry.configured_feature;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import java.util.List;

public class ConfiguredOres {
    public static final RuleTest VANILLATE = new BlockMatchRuleTest(ModBlocks.VANILLATE);
    public static final RuleTest FROSTY_VANILLATE = new BlockMatchRuleTest(ModBlocks.FROSTY_VANILLATE);
    public static final RuleTest TOADSTOOL_SOIL = new BlockMatchRuleTest(ModBlocks.TOADSTOOL_SOIL);
    public static final RuleTest TOPPED_VANILLATE = new BlockMatchRuleTest(ModBlocks.TOPPED_VANILLATE);

    public static final List<OreFeatureConfig.Target> CRUMBLE_ORES = List.of(OreFeatureConfig.createTarget(VANILLATE, ModBlocks.VANILLATE_CRUMBLE.getDefaultState()), OreFeatureConfig.createTarget(FROSTY_VANILLATE, ModBlocks.FROSTY_VANILLATE_CRUMBLE.getDefaultState()), OreFeatureConfig.createTarget(TOADSTOOL_SOIL, ModBlocks.COARSE_TOADSTOOL_SOIL.getDefaultState()));
    public static final List<OreFeatureConfig.Target> BRONZE_ORES = List.of(OreFeatureConfig.createTarget(VANILLATE, ModBlocks.BRONZE_ORE.getDefaultState()), OreFeatureConfig.createTarget(TOPPED_VANILLATE, ModBlocks.BRONZE_ORE.getDefaultState()));
    public static final List<OreFeatureConfig.Target> AMETHYST_ORES = List.of(OreFeatureConfig.createTarget(VANILLATE, ModBlocks.BRONZE_ORE.getDefaultState()), OreFeatureConfig.createTarget(TOPPED_VANILLATE, ModBlocks.BRONZE_ORE.getDefaultState()));

    public static final ConfiguredFeature<?, ?> ORE_CRUMBLE = Feature.SCATTERED_ORE.configure(new OreFeatureConfig(CRUMBLE_ORES, 16));
    public static final ConfiguredFeature<?, ?> ORE_BRONZE = Feature.ORE.configure(new OreFeatureConfig(BRONZE_ORES,16));
    public static final ConfiguredFeature<?, ?> ORE_AMETHYST = Feature.ORE.configure(new OreFeatureConfig(AMETHYST_ORES,4));
    public static final ConfiguredFeature<?, ?> ORE_HARDSTONE = Feature.ORE.configure(new OreFeatureConfig(VANILLATE, ModBlocks.HARDSTONE.getDefaultState(),60, 0.1F));
    public static final ConfiguredFeature<?, ?> TOPPING_COAL = Feature.ORE.configure(new OreFeatureConfig(TOPPED_VANILLATE, ModBlocks.COAL_TOPPED_VANILLATE.getDefaultState(),16));
    public static final ConfiguredFeature<?, ?> TOPPING_IRON = Feature.ORE.configure(new OreFeatureConfig(TOPPED_VANILLATE, ModBlocks.IRON_TOPPED_VANILLATE.getDefaultState(),16));
    public static final ConfiguredFeature<?, ?> TOPPING_GOLD = Feature.ORE.configure(new OreFeatureConfig(TOPPED_VANILLATE, ModBlocks.GOLD_TOPPED_VANILLATE.getDefaultState(),14));
    public static final ConfiguredFeature<?, ?> DISK_SAND = Feature.DISK.configure(new DiskFeatureConfig(Blocks.SAND.getDefaultState(), UniformIntProvider.create(2, 6), 2, List.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState(), ModBlocks.TOADSTOOL_SOIL.getDefaultState(), ModBlocks.TOADSTOOL_PATH.getDefaultState(), ModBlocks.TOADSTONE.getDefaultState(), ModBlocks.GRASSY_TOADSTONE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> VANILLATE_TOPPING = Feature.VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(new Identifier("super_block_world:apply_topping_to"), SimpleBlockStateProvider.of(ModBlocks.TOPPED_VANILLATE.getDefaultState()), ORE_BRONZE::withPlacement, VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.0F, 5, 0F, UniformIntProvider.create(6, 12), 0.3F));
}
