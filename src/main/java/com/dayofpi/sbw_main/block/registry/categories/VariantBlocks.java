package com.dayofpi.sbw_main.block.registry.categories;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.types.CloudSlabBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;

public class VariantBlocks {
    public static final Block CLOUD_SLAB = new CloudSlabBlock(FabricBlockSettings.copyOf(ModBlocks.CLOUD_BLOCK));

    public static final Block BRONZE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.BRONZE_BLOCK));
    public static final Block BRONZE_STAIRS = new StairsBlock(ModBlocks.BRONZE_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.BRONZE_BLOCK)){};

    public static final Block GRITZY_SANDSTONE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.GRITZY_SANDSTONE));
    public static final Block GRITZY_SANDSTONE_STAIRS = new StairsBlock(ModBlocks.GRITZY_SANDSTONE.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.GRITZY_SANDSTONE)){};

    public static final Block AMANITA_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.AMANITA_PLANKS));
    public static final Block AMANITA_STAIRS = new StairsBlock(ModBlocks.AMANITA_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.AMANITA_PLANKS)){};

    public static final Block SEASTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.SEASTONE_BRICKS));
    public static final Block SEASTONE_BRICK_STAIRS = new StairsBlock(ModBlocks.SEASTONE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.SEASTONE)){};

    public static final Block TOADSTONE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.TOADSTONE));
    public static final Block TOADSTONE_STAIRS = new StairsBlock(ModBlocks.TOADSTONE.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.TOADSTONE)){};
    public static final Block TOADSTONE_WALL = new WallBlock(FabricBlockSettings.copyOf(ModBlocks.TOADSTONE));

    public static final Block TOADSTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.TOADSTONE_BRICKS));
    public static final Block TOADSTONE_BRICK_STAIRS = new StairsBlock(ModBlocks.TOADSTONE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.TOADSTONE_BRICKS)){};
    public static final Block TOADSTONE_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(ModBlocks.TOADSTONE_BRICKS));

    public static final Block GLOOMSTONE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.GLOOMSTONE));
    public static final Block GLOOMSTONE_STAIRS = new StairsBlock(ModBlocks.GLOOMSTONE.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.GLOOMSTONE)){};
    public static final Block GLOOMSTONE_WALL = new WallBlock(FabricBlockSettings.copyOf(ModBlocks.GLOOMSTONE));

    public static final Block GLOOMSTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.GLOOMSTONE_BRICKS));
    public static final Block GLOOMSTONE_BRICK_STAIRS = new StairsBlock(ModBlocks.GLOOMSTONE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.GLOOMSTONE_BRICKS)){};
    public static final Block GLOOMSTONE_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(ModBlocks.GLOOMSTONE_BRICKS));

    public static final Block POLISHED_HARDSTONE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.POLISHED_HARDSTONE));
    public static final Block POLISHED_HARDSTONE_STAIRS = new StairsBlock(ModBlocks.POLISHED_HARDSTONE.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.POLISHED_HARDSTONE)){};

    public static final Block HARDSTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.HARDSTONE_BRICKS));
    public static final Block HARDSTONE_BRICK_STAIRS = new StairsBlock(ModBlocks.HARDSTONE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.POLISHED_HARDSTONE)){};

    public static final Block GOLDEN_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.GOLDEN_BRICKS));
    public static final Block GOLDEN_BRICK_STAIRS = new StairsBlock(ModBlocks.GOLDEN_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.GOLDEN_BRICKS)){};

    public static final Block CRYSTAL_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.CRYSTAL_BRICKS));
    public static final Block CRYSTAL_BRICK_STAIRS = new StairsBlock(ModBlocks.CRYSTAL_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.CRYSTAL_BRICKS)){};

    public static final Block VANILLATE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.VANILLATE));
    public static final Block VANILLATE_STAIRS = new StairsBlock(ModBlocks.VANILLATE.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.VANILLATE)){};

    public static final Block VANILLATE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.VANILLATE_BRICKS));
    public static final Block VANILLATE_BRICK_STAIRS = new StairsBlock(ModBlocks.VANILLATE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.VANILLATE_BRICKS)){};

    public static final Block FROSTY_VANILLATE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.FROSTY_VANILLATE));
    public static final Block FROSTY_VANILLATE_STAIRS = new StairsBlock(ModBlocks.FROSTY_VANILLATE.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.FROSTY_VANILLATE)){};

    public static final Block FROSTY_VANILLATE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.FROSTY_VANILLATE_BRICKS));
    public static final Block FROSTY_VANILLATE_BRICK_STAIRS = new StairsBlock(ModBlocks.FROSTY_VANILLATE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.FROSTY_VANILLATE_BRICKS)){};

    public static final Block ROYALITE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.ROYALITE_BRICKS));
    public static final Block ROYALITE_BRICK_STAIRS = new StairsBlock(ModBlocks.ROYALITE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.ROYALITE_BRICKS)){};
    public static final Block ROYALITE_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(ModBlocks.ROYALITE_BRICKS));

    public static final Block AMANITA_FENCE = new FenceBlock(FabricBlockSettings.copyOf(ModBlocks.AMANITA_PLANKS));
    public static final Block AMANITA_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.copyOf(ModBlocks.AMANITA_PLANKS));
}
