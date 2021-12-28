package com.dayofpi.super_block_world.main.registry.world.feature.configured;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ConfiguredOres {
    private static final RuleTest VANILLATE = new BlockMatchRuleTest(BlockRegistry.VANILLATE);
    private static final RuleTest TOADSTONE = new BlockMatchRuleTest(BlockRegistry.TOADSTONE);
    private static final RuleTest ROYALITE = new BlockMatchRuleTest(BlockRegistry.ROYALITE);
    private static final RuleTest FROSTY_VANILLATE = new BlockMatchRuleTest(BlockRegistry.FROSTY_VANILLATE);
    private static final RuleTest FROSTED_VANILLATE = new BlockMatchRuleTest(BlockRegistry.FROSTED_VANILLATE);
    private static final RuleTest TOADSTOOL_SOIL = new BlockMatchRuleTest(BlockRegistry.TOADSTOOL_SOIL);
    private static final RuleTest TOADSTOOL_PATH = new BlockMatchRuleTest(BlockRegistry.TOADSTOOL_PATH);
    private static final RuleTest TOPPED_VANILLATE = new BlockMatchRuleTest(BlockRegistry.TOPPED_VANILLATE);

    private static final List<OreFeatureConfig.Target> ORE_CRUMBLE_TARGET = List.of(OreFeatureConfig.createTarget(VANILLATE, BlockRegistry.VANILLATE_CRUMBLE.getDefaultState()), OreFeatureConfig.createTarget(FROSTY_VANILLATE, BlockRegistry.FROSTY_VANILLATE_CRUMBLE.getDefaultState()), OreFeatureConfig.createTarget(TOADSTOOL_SOIL, BlockRegistry.COARSE_TOADSTOOL_SOIL.getDefaultState()), OreFeatureConfig.createTarget(TOADSTOOL_PATH, BlockRegistry.COARSE_TOADSTOOL_SOIL.getDefaultState()), OreFeatureConfig.createTarget(TOADSTONE, BlockRegistry.TOADSTONE_BRICKS.getDefaultState()));
    private static final List<OreFeatureConfig.Target> ORE_BRONZE_TARGET = List.of(OreFeatureConfig.createTarget(VANILLATE, BlockRegistry.BRONZE_ORE.getDefaultState()), OreFeatureConfig.createTarget(TOPPED_VANILLATE, BlockRegistry.BRONZE_ORE.getDefaultState()));
    private static final List<OreFeatureConfig.Target> ORE_AMETHYST_TARGET = List.of(OreFeatureConfig.createTarget(VANILLATE, BlockRegistry.AMETHYST_ORE.getDefaultState()));
    private static final List<OreFeatureConfig.Target> ORE_CERISE_TARGET = List.of(OreFeatureConfig.createTarget(ROYALITE, BlockRegistry.CERISE_ORE.getDefaultState()));

    public static final ConfiguredFeature<?, ?> ORE_FROZEN = Feature.ORE.configure(new OreFeatureConfig(FROSTED_VANILLATE, BlockRegistry.FROZEN_ORE.getDefaultState(), 8));
    public static final ConfiguredFeature<?, ?> ORE_CRUMBLE = Feature.SCATTERED_ORE.configure(new OreFeatureConfig(ORE_CRUMBLE_TARGET, 10));
    public static final ConfiguredFeature<?, ?> ORE_BRONZE = Feature.ORE.configure(new OreFeatureConfig(ORE_BRONZE_TARGET,18));
    public static final ConfiguredFeature<?, ?> ORE_AMETHYST = Feature.SCATTERED_ORE.configure(new OreFeatureConfig(ORE_AMETHYST_TARGET,4));
    public static final ConfiguredFeature<?, ?> ORE_CERISE = Feature.ORE.configure(new OreFeatureConfig(ORE_CERISE_TARGET,9));
    public static final ConfiguredFeature<?, ?> ORE_HARDSTONE = Feature.ORE.configure(new OreFeatureConfig(VANILLATE, BlockRegistry.HARDSTONE.getDefaultState(),64, 0.3F));
    public static final ConfiguredFeature<?, ?> TOPPING_COAL = Feature.ORE.configure(new OreFeatureConfig(TOPPED_VANILLATE, BlockRegistry.COAL_TOPPED_VANILLATE.getDefaultState(),16));
    public static final ConfiguredFeature<?, ?> TOPPING_IRON = Feature.ORE.configure(new OreFeatureConfig(TOPPED_VANILLATE, BlockRegistry.IRON_TOPPED_VANILLATE.getDefaultState(),16));
    public static final ConfiguredFeature<?, ?> TOPPING_GOLD = Feature.ORE.configure(new OreFeatureConfig(TOPPED_VANILLATE, BlockRegistry.GOLD_TOPPED_VANILLATE.getDefaultState(),14));
    public static final ConfiguredFeature<?, ?> PIT_PLANT = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(PlantBlocks.PIT_PLANT.getDefaultState())));
    public static final ConfiguredFeature<?, ?> DISK_SAND = Feature.DISK.configure(new DiskFeatureConfig(Blocks.SAND.getDefaultState(), UniformIntProvider.create(2, 6), 2, List.of(BlockRegistry.TOADSTOOL_GRASS.getDefaultState(), BlockRegistry.TOADSTOOL_SOIL.getDefaultState(), BlockRegistry.TOADSTOOL_PATH.getDefaultState(), BlockRegistry.SHERBET_SOIL.getDefaultState(), BlockRegistry.SNOWY_SHERBET_SOIL.getDefaultState(), BlockRegistry.TOADSTONE.getDefaultState(), BlockRegistry.GRASSY_TOADSTONE.getDefaultState(), BlockRegistry.HARDSTONE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> DISK_SEASTONE = Feature.DISK.configure(new DiskFeatureConfig(BlockRegistry.SEASTONE.getDefaultState(), UniformIntProvider.create(2, 6), 2, List.of(BlockRegistry.SHORESAND.getDefaultState(), BlockRegistry.SHOREGRASS.getDefaultState(), BlockRegistry.TOADSTOOL_SOIL.getDefaultState())));
}
