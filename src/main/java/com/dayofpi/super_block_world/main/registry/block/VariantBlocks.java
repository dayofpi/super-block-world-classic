package com.dayofpi.super_block_world.main.registry.block;

import com.dayofpi.super_block_world.main.common.block.cloud.CloudSlabBlock;
import com.dayofpi.super_block_world.main.common.block.cloud.CloudStairsBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;

public class VariantBlocks {
    public static final Block CLOUD_SLAB = new CloudSlabBlock(FabricBlockSettings.copyOf(BlockRegistry.CLOUD_BLOCK));
    public static final Block CLOUD_STAIRS = new CloudStairsBlock(FabricBlockSettings.copyOf(BlockRegistry.CLOUD_BLOCK));

    public static final Block BRONZE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK));
    public static final Block BRONZE_STAIRS = new StairsBlock(BlockRegistry.BRONZE_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK)){};

    public static final Block GRITZY_SANDSTONE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.GRITZY_SANDSTONE));
    public static final Block GRITZY_SANDSTONE_STAIRS = new StairsBlock(BlockRegistry.GRITZY_SANDSTONE.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.GRITZY_SANDSTONE)){};

    public static final Block AMANITA_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.AMANITA_PLANKS));
    public static final Block AMANITA_STAIRS = new StairsBlock(BlockRegistry.AMANITA_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.AMANITA_PLANKS)){};
    public static final Block AMANITA_FENCE = new FenceBlock(FabricBlockSettings.copyOf(BlockRegistry.AMANITA_PLANKS));
    public static final Block AMANITA_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.copyOf(BlockRegistry.AMANITA_PLANKS));

    public static final Block DARK_AMANITA_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.DARK_AMANITA_PLANKS));
    public static final Block DARK_AMANITA_STAIRS = new StairsBlock(BlockRegistry.DARK_AMANITA_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.DARK_AMANITA_PLANKS)){};
    public static final Block DARK_AMANITA_FENCE = new FenceBlock(FabricBlockSettings.copyOf(BlockRegistry.DARK_AMANITA_PLANKS));
    public static final Block DARK_AMANITA_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.copyOf(BlockRegistry.DARK_AMANITA_PLANKS));

    public static final Block SEASTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.SEASTONE_BRICKS));
    public static final Block SEASTONE_BRICK_STAIRS = new StairsBlock(BlockRegistry.SEASTONE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.SEASTONE)){};
    public static final Block SEASTONE_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(BlockRegistry.SEASTONE_BRICKS));

    public static final Block TOADSTONE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.TOADSTONE));
    public static final Block TOADSTONE_STAIRS = new StairsBlock(BlockRegistry.TOADSTONE.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.TOADSTONE)){};
    public static final Block TOADSTONE_WALL = new WallBlock(FabricBlockSettings.copyOf(BlockRegistry.TOADSTONE));

    public static final Block TOADSTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.TOADSTONE_BRICKS));
    public static final Block TOADSTONE_BRICK_STAIRS = new StairsBlock(BlockRegistry.TOADSTONE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.TOADSTONE_BRICKS)){};
    public static final Block TOADSTONE_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(BlockRegistry.TOADSTONE_BRICKS));

    public static final Block GLOOMSTONE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.GLOOMSTONE));
    public static final Block GLOOMSTONE_STAIRS = new StairsBlock(BlockRegistry.GLOOMSTONE.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.GLOOMSTONE)){};
    public static final Block GLOOMSTONE_WALL = new WallBlock(FabricBlockSettings.copyOf(BlockRegistry.GLOOMSTONE));

    public static final Block GLOOMSTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.GLOOMSTONE_BRICKS));
    public static final Block GLOOMSTONE_BRICK_STAIRS = new StairsBlock(BlockRegistry.GLOOMSTONE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.GLOOMSTONE_BRICKS)){};
    public static final Block GLOOMSTONE_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(BlockRegistry.GLOOMSTONE_BRICKS));

    public static final Block POLISHED_HARDSTONE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.POLISHED_HARDSTONE));
    public static final Block POLISHED_HARDSTONE_STAIRS = new StairsBlock(BlockRegistry.POLISHED_HARDSTONE.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.POLISHED_HARDSTONE)){};
    public static final Block POLISHED_HARDSTONE_WALL = new WallBlock(FabricBlockSettings.copyOf(BlockRegistry.POLISHED_HARDSTONE));

    public static final Block HARDSTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.HARDSTONE_BRICKS));
    public static final Block HARDSTONE_BRICK_STAIRS = new StairsBlock(BlockRegistry.HARDSTONE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.POLISHED_HARDSTONE)){};
    public static final Block HARDSTONE_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(BlockRegistry.HARDSTONE_BRICKS));

    public static final Block GOLDEN_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.GOLDEN_BRICKS));
    public static final Block GOLDEN_BRICK_STAIRS = new StairsBlock(BlockRegistry.GOLDEN_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.GOLDEN_BRICKS)){};
    public static final Block GOLDEN_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(BlockRegistry.GOLDEN_BRICKS));

    public static final Block CRYSTAL_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.CRYSTAL_BRICKS));
    public static final Block CRYSTAL_BRICK_STAIRS = new StairsBlock(BlockRegistry.CRYSTAL_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.CRYSTAL_BRICKS)){};
    public static final Block CRYSTAL_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(BlockRegistry.CRYSTAL_BRICKS));

    public static final Block VANILLATE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.VANILLATE));
    public static final Block VANILLATE_STAIRS = new StairsBlock(BlockRegistry.VANILLATE.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.VANILLATE)){};

    public static final Block VANILLATE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.VANILLATE_BRICKS));
    public static final Block VANILLATE_BRICK_STAIRS = new StairsBlock(BlockRegistry.VANILLATE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.VANILLATE_BRICKS)){};

    public static final Block FROSTY_VANILLATE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.FROSTY_VANILLATE));
    public static final Block FROSTY_VANILLATE_STAIRS = new StairsBlock(BlockRegistry.FROSTY_VANILLATE.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.FROSTY_VANILLATE)){};

    public static final Block FROSTY_VANILLATE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.FROSTY_VANILLATE_BRICKS));
    public static final Block FROSTY_VANILLATE_BRICK_STAIRS = new StairsBlock(BlockRegistry.FROSTY_VANILLATE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.FROSTY_VANILLATE_BRICKS)){};

    public static final Block ROYALITE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.ROYALITE_BRICKS));
    public static final Block ROYALITE_BRICK_STAIRS = new StairsBlock(BlockRegistry.ROYALITE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.ROYALITE_BRICKS)){};
    public static final Block ROYALITE_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(BlockRegistry.ROYALITE_BRICKS));

    public static final Block CERISE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.CERISE_BRICKS));
    public static final Block CERISE_BRICK_STAIRS = new StairsBlock(BlockRegistry.CERISE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.CERISE_BRICKS)){};

    public static final Block CERISE_TILE_SLAB = new SlabBlock(FabricBlockSettings.copyOf(BlockRegistry.CERISE_TILES));
    public static final Block CERISE_TILE_STAIRS = new StairsBlock(BlockRegistry.CERISE_TILES.getDefaultState(), FabricBlockSettings.copyOf(BlockRegistry.CERISE_TILES)){};
}
