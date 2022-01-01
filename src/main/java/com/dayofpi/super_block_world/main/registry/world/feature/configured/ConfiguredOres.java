package com.dayofpi.super_block_world.main.registry.world.feature.configured;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.utility.RuleTests;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ConfiguredOres {
    public static final ConfiguredFeature<?, ?> ORE_FROZEN;
    public static final ConfiguredFeature<?, ?> ORE_CRUMBLE;
    public static final ConfiguredFeature<?, ?> ORE_BRONZE;
    public static final ConfiguredFeature<?, ?> ORE_VANILLATE_COAL;
    public static final ConfiguredFeature<?, ?> ORE_VANILLATE_IRON = Feature.ORE.configure(new OreFeatureConfig(RuleTests.VANILLATE, BlockRegistry.VANILLATE_IRON_ORE.getDefaultState(),16));
    public static final ConfiguredFeature<?, ?> ORE_CERISE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.ROYALITE, BlockRegistry.CERISE_ORE.getDefaultState(),9));
    public static final ConfiguredFeature<?, ?> ORE_TOADSTONE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.VANILLATE, BlockRegistry.TOADSTONE.getDefaultState(), 64, 0.03F));
    public static final ConfiguredFeature<?, ?> ORE_CHISELED_TOADSTONE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.TOADSTONE, BlockRegistry.CHISELED_TOADSTONE_BRICKS.getDefaultState(), 20, 0.0F));
    public static final ConfiguredFeature<?, ?> ORE_GLOOMSTONE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.VANILLATE, BlockRegistry.GLOOMSTONE.getDefaultState(), 64, 0.03F));
    public static final ConfiguredFeature<?, ?> ORE_HARDSTONE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.FROSTY_VANILLATE, BlockRegistry.HARDSTONE.getDefaultState(),64, 0.3F));
    public static final ConfiguredFeature<?, ?> TOPPING_GOLD = Feature.ORE.configure(new OreFeatureConfig(RuleTests.TOPPED_VANILLATE, BlockRegistry.GOLD_TOPPED_VANILLATE.getDefaultState(),14));
    public static final ConfiguredFeature<?, ?> TOPPING_AMETHYST = Feature.ORE.configure(new OreFeatureConfig(RuleTests.TOPPED_VANILLATE, BlockRegistry.AMETHYST_TOPPED_VANILLATE.getDefaultState(),14));
    public static final ConfiguredFeature<?, ?> DISK_SAND = Feature.DISK.configure(new DiskFeatureConfig(Blocks.SAND.getDefaultState(), UniformIntProvider.create(2, 6), 2, List.of(BlockRegistry.TOADSTOOL_GRASS.getDefaultState(), BlockRegistry.TOADSTOOL_SOIL.getDefaultState(), BlockRegistry.TOADSTOOL_PATH.getDefaultState(), BlockRegistry.SHERBET_SOIL.getDefaultState(), BlockRegistry.SNOWY_SHERBET_SOIL.getDefaultState(), BlockRegistry.HARDSTONE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> DISK_SEASTONE = Feature.DISK.configure(new DiskFeatureConfig(BlockRegistry.SEASTONE.getDefaultState(), UniformIntProvider.create(2, 6), 2, List.of(BlockRegistry.SHORESAND.getDefaultState(), BlockRegistry.SHOREGRASS.getDefaultState(), BlockRegistry.TOADSTOOL_SOIL.getDefaultState())));
    public static final List<OreFeatureConfig.Target> ORE_CRUMBLE_TARGET = List.of(OreFeatureConfig.createTarget(RuleTests.VANILLATE, BlockRegistry.VANILLATE_CRUMBLE.getDefaultState()), OreFeatureConfig.createTarget(RuleTests.FROSTY_VANILLATE, BlockRegistry.FROSTY_VANILLATE_CRUMBLE.getDefaultState()), OreFeatureConfig.createTarget(RuleTests.TOADSTOOL_SOIL, BlockRegistry.COARSE_TOADSTOOL_SOIL.getDefaultState()), OreFeatureConfig.createTarget(RuleTests.TOADSTOOL_PATH, BlockRegistry.COARSE_TOADSTOOL_SOIL.getDefaultState()), OreFeatureConfig.createTarget(RuleTests.TOADSTONE, BlockRegistry.TOADSTONE_BRICKS.getDefaultState()));

    static {
        ORE_FROZEN = Feature.ORE.configure(new OreFeatureConfig(RuleTests.FROSTED_VANILLATE, BlockRegistry.FROZEN_ORE.getDefaultState(), 8));
        ORE_CRUMBLE = Feature.ORE.configure(new OreFeatureConfig(ORE_CRUMBLE_TARGET, 8));
        ORE_BRONZE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.VANILLATE, BlockRegistry.BRONZE_ORE.getDefaultState(),16));
        ORE_VANILLATE_COAL = Feature.ORE.configure(new OreFeatureConfig(RuleTests.VANILLATE, BlockRegistry.VANILLATE_COAL_ORE.getDefaultState(), 18));
    }
}
