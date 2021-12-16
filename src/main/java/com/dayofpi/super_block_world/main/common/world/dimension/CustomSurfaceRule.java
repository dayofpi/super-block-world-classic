package com.dayofpi.super_block_world.main.common.world.dimension;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.util.ModBiomeKeys;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class CustomSurfaceRule {
    private static MaterialRules.MaterialRule block(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }

    public static MaterialRules.MaterialRule createSurfaceRule() {
        MaterialRules.MaterialRule grassAndSoil = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING_WITH_SURFACE_DEPTH, block(BlockRegistry.TOADSTOOL_SOIL)), block(BlockRegistry.TOADSTOOL_GRASS));
        MaterialRules.MaterialRule toadstone = MaterialRules.sequence(MaterialRules.condition(MaterialRules.waterWithStoneDepth(-1, 0), block(BlockRegistry.TOADSTOOL_SOIL)), block(BlockRegistry.TOADSTOOL_GRASS));

        MaterialRules.MaterialCondition grassyBiome = MaterialRules.biome(ModBiomeKeys.MUSHROOM_GRASSLANDS, ModBiomeKeys.AMANITA_FOREST, ModBiomeKeys.AUTUMN_FOREST, ModBiomeKeys.MOO_MOO_MEADOW, ModBiomeKeys.FOREST_OF_ILLUSION);
        MaterialRules.MaterialCondition fossilFalls = MaterialRules.biome(ModBiomeKeys.FOSSIL_FALLS);

        MaterialRules.MaterialRule applyGrass = MaterialRules.condition(grassyBiome, grassAndSoil);
        MaterialRules.MaterialRule applyGrass2 = MaterialRules.condition(fossilFalls, toadstone);
        ImmutableList.Builder<MaterialRules.MaterialRule> builder = ImmutableList.builder();

        builder.add(applyGrass, applyGrass2);
        builder.add(MaterialRules.condition(MaterialRules.verticalGradient("deepslate", YOffset.fixed(-8), YOffset.fixed(-4)), block(BlockRegistry.ROYALITE)));
        builder.add(MaterialRules.condition(MaterialRules.verticalGradient("bedrock_floor", YOffset.getBottom(), YOffset.aboveBottom(5)), block(Blocks.BEDROCK)));
        return MaterialRules.sequence(builder.build().toArray(MaterialRules.MaterialRule[]::new));
    }
}
