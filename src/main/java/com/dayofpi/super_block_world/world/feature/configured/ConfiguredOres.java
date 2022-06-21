package com.dayofpi.super_block_world.world.feature.configured;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.world.feature.utility.RuleTests;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ConfiguredOres {
    public static final ConfiguredFeature<?, ?> ORE_FROZEN;
    public static final ConfiguredFeature<?, ?> ORE_CRUMBLE;
    public static final ConfiguredFeature<?, ?> ORE_BRONZE;
    public static final ConfiguredFeature<?, ?> ORE_VANILLATE_COAL;
    public static final ConfiguredFeature<?, ?> ORE_VANILLATE_IRON = Feature.ORE.configure(new OreFeatureConfig(RuleTests.VANILLATE, BlockInit.VANILLATE_IRON_ORE.getDefaultState(),16));
    public static final ConfiguredFeature<?, ?> ORE_CERISE;
    public static final ConfiguredFeature<?, ?> ORE_TOADSTONE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.VANILLATE, BlockInit.TOADSTONE.getDefaultState(), 64, 0.03F));
    public static final ConfiguredFeature<?, ?> ORE_CHISELED_TOADSTONE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.TOADSTONE, BlockInit.CHISELED_TOADSTONE_BRICKS.getDefaultState(), 20, 0.0F));
    public static final ConfiguredFeature<?, ?> ORE_GLOOMSTONE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.VANILLATE, BlockInit.GLOOMSTONE.getDefaultState(), 64, 0.03F));
    public static final ConfiguredFeature<?, ?> ORE_HARDSTONE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.FROSTY_VANILLATE, BlockInit.HARDSTONE.getDefaultState(),64, 0.3F));
    public static final ConfiguredFeature<?, ?> TOPPING_GOLD = Feature.ORE.configure(new OreFeatureConfig(RuleTests.TOPPED_VANILLATE, BlockInit.GOLD_TOPPED_VANILLATE.getDefaultState(),14));
    public static final ConfiguredFeature<?, ?> TOPPING_AMETHYST = Feature.ORE.configure(new OreFeatureConfig(RuleTests.TOPPED_VANILLATE, BlockInit.AMETHYST_TOPPED_VANILLATE.getDefaultState(),14));
    public static final ConfiguredFeature<?, ?> DISK_SAND = Feature.DISK.configure(new DiskFeatureConfig(Blocks.SAND.getDefaultState(), UniformIntProvider.create(2, 6), 2, List.of(BlockInit.TOADSTOOL_GRASS.getDefaultState(), BlockInit.TOADSTOOL_SOIL.getDefaultState(), BlockInit.TOADSTOOL_PATH.getDefaultState(), BlockInit.SHERBET_SOIL.getDefaultState(), BlockInit.SNOWY_SHERBET_SOIL.getDefaultState(), BlockInit.HARDSTONE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> DISK_SEASTONE = Feature.DISK.configure(new DiskFeatureConfig(BlockInit.SEASTONE.getDefaultState(), UniformIntProvider.create(2, 6), 2, List.of(BlockInit.SHORESAND.getDefaultState(), BlockInit.SHOREGRASS.getDefaultState(), BlockInit.TOADSTOOL_SOIL.getDefaultState())));
    public static final List<OreFeatureConfig.Target> ORE_CRUMBLE_TARGET = List.of(OreFeatureConfig.createTarget(RuleTests.VANILLATE, BlockInit.VANILLATE_CRUMBLE.getDefaultState()), OreFeatureConfig.createTarget(RuleTests.FROSTY_VANILLATE, BlockInit.FROSTY_VANILLATE_CRUMBLE.getDefaultState()), OreFeatureConfig.createTarget(RuleTests.TOADSTOOL_SOIL, BlockInit.COARSE_TOADSTOOL_SOIL.getDefaultState()), OreFeatureConfig.createTarget(RuleTests.TOADSTONE, BlockInit.TOADSTONE_BRICKS.getDefaultState()));
    public static final List<OreFeatureConfig.Target> ORE_CERISE_TARGET = List.of(OreFeatureConfig.createTarget(RuleTests.ROYALITE, BlockInit.CERISE_ORE.getDefaultState()), OreFeatureConfig.createTarget(RuleTests.CHARROCK, Blocks.MAGMA_BLOCK.getDefaultState()));

    static {
        ORE_FROZEN = Feature.ORE.configure(new OreFeatureConfig(RuleTests.FROSTED_VANILLATE, BlockInit.FROZEN_ORE.getDefaultState(), 8));
        ORE_CRUMBLE = Feature.ORE.configure(new OreFeatureConfig(ORE_CRUMBLE_TARGET, 8));
        ORE_CERISE = Feature.ORE.configure(new OreFeatureConfig(ORE_CERISE_TARGET, 12));
        ORE_BRONZE = Feature.ORE.configure(new OreFeatureConfig(RuleTests.VANILLATE, BlockInit.BRONZE_ORE.getDefaultState(), 16));
        ORE_VANILLATE_COAL = Feature.ORE.configure(new OreFeatureConfig(RuleTests.VANILLATE, BlockInit.VANILLATE_COAL_ORE.getDefaultState(), 18));
    }
}
