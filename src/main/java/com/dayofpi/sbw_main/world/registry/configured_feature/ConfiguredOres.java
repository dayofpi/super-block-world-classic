package com.dayofpi.sbw_main.world.registry.configured_feature;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
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
    private static final RuleTest VANILLATE = new BlockMatchRuleTest(ModBlocks.VANILLATE);
    private static final RuleTest ROYALITE = new BlockMatchRuleTest(ModBlocks.ROYALITE);
    private static final RuleTest FROSTY_VANILLATE = new BlockMatchRuleTest(ModBlocks.FROSTY_VANILLATE);
    private static final RuleTest TOADSTOOL_SOIL = new BlockMatchRuleTest(ModBlocks.TOADSTOOL_SOIL);
    private static final RuleTest TOPPED_VANILLATE = new BlockMatchRuleTest(ModBlocks.TOPPED_VANILLATE);

    private static final List<OreFeatureConfig.Target> CRUMBLE_ORES = List.of(OreFeatureConfig.createTarget(VANILLATE, ModBlocks.VANILLATE_CRUMBLE.getDefaultState()), OreFeatureConfig.createTarget(FROSTY_VANILLATE, ModBlocks.FROSTY_VANILLATE_CRUMBLE.getDefaultState()), OreFeatureConfig.createTarget(TOADSTOOL_SOIL, ModBlocks.COARSE_TOADSTOOL_SOIL.getDefaultState()));
    private static final List<OreFeatureConfig.Target> BRONZE_ORES = List.of(OreFeatureConfig.createTarget(VANILLATE, ModBlocks.BRONZE_ORE.getDefaultState()), OreFeatureConfig.createTarget(TOPPED_VANILLATE, ModBlocks.BRONZE_ORE.getDefaultState()));
    private static final List<OreFeatureConfig.Target> AMETHYST_ORES = List.of(OreFeatureConfig.createTarget(VANILLATE, ModBlocks.AMETHYST_ORE.getDefaultState()));
    private static final List<OreFeatureConfig.Target> CERISE_ORES = List.of(OreFeatureConfig.createTarget(ROYALITE, ModBlocks.CERISE_ORE.getDefaultState()));

    public static final ConfiguredFeature<?, ?> ORE_CRUMBLE = Feature.SCATTERED_ORE.configure(new OreFeatureConfig(CRUMBLE_ORES, 8));
    public static final ConfiguredFeature<?, ?> ORE_BRONZE = Feature.ORE.configure(new OreFeatureConfig(BRONZE_ORES,18));
    public static final ConfiguredFeature<?, ?> ORE_AMETHYST = Feature.SCATTERED_ORE.configure(new OreFeatureConfig(AMETHYST_ORES,4));
    public static final ConfiguredFeature<?, ?> ORE_CERISE = Feature.ORE.configure(new OreFeatureConfig(CERISE_ORES,9));
    public static final ConfiguredFeature<?, ?> ORE_HARDSTONE = Feature.ORE.configure(new OreFeatureConfig(VANILLATE, ModBlocks.HARDSTONE.getDefaultState(),64, 0.3F));
    public static final ConfiguredFeature<?, ?> TOPPING_COAL = Feature.ORE.configure(new OreFeatureConfig(TOPPED_VANILLATE, ModBlocks.COAL_TOPPED_VANILLATE.getDefaultState(),16));
    public static final ConfiguredFeature<?, ?> TOPPING_IRON = Feature.ORE.configure(new OreFeatureConfig(TOPPED_VANILLATE, ModBlocks.IRON_TOPPED_VANILLATE.getDefaultState(),16));
    public static final ConfiguredFeature<?, ?> TOPPING_GOLD = Feature.ORE.configure(new OreFeatureConfig(TOPPED_VANILLATE, ModBlocks.GOLD_TOPPED_VANILLATE.getDefaultState(),14));
    public static final ConfiguredFeature<?, ?> PIT_PLANT = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(SimpleBlockStateProvider.of(PlantBlocks.PIT_PLANT.getDefaultState())));
    public static final ConfiguredFeature<?, ?> DISK_SAND = Feature.DISK.configure(new DiskFeatureConfig(Blocks.SAND.getDefaultState(), UniformIntProvider.create(2, 6), 2, List.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState(), ModBlocks.TOADSTOOL_SOIL.getDefaultState(), ModBlocks.TOADSTOOL_PATH.getDefaultState(), ModBlocks.TOADSTONE.getDefaultState(), ModBlocks.GRASSY_TOADSTONE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> VANILLATE_TOPPING = Feature.VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(new Identifier("super_block_world:apply_topping_to"), SimpleBlockStateProvider.of(ModBlocks.TOPPED_VANILLATE.getDefaultState()), VegetationConfiguredFeatures.VINES::withPlacement, VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.0F, 5, 0F, UniformIntProvider.create(5, 8), 0.3F));
    public static final ConfiguredFeature<?, ?> QUICKSAND = Feature.VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(new Identifier("super_block_world:apply_quicksand_to"), SimpleBlockStateProvider.of(ModBlocks.QUICKSAND.getDefaultState()), PIT_PLANT::withPlacement, VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.8F, 1, 0.02F, UniformIntProvider.create(7, 11), 0.6F));
}
