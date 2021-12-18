package com.dayofpi.super_block_world.main.registry.block;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.block.*;
import com.dayofpi.super_block_world.main.common.block.cloud.CloudBlock;
import com.dayofpi.super_block_world.main.common.block.cloud.HappyCloudBlock;
import com.dayofpi.super_block_world.main.common.block.fluid.PoisonBlock;
import com.dayofpi.super_block_world.main.common.block.reactive.CoinBlock;
import com.dayofpi.super_block_world.main.common.block.reactive.EmptyBlock;
import com.dayofpi.super_block_world.main.common.block.reactive.QuestionBlock;
import com.dayofpi.super_block_world.main.common.block.soil.*;
import com.dayofpi.super_block_world.main.registry.FluidRegistry;
import com.dayofpi.super_block_world.main.common.block.pipe.BronzePipeBlock;
import com.dayofpi.super_block_world.main.common.block.pipe.WarpPipeBlock;
import com.dayofpi.super_block_world.main.common.block.pipe.WarpPipeBodyBlock;
import com.dayofpi.super_block_world.main.util.mixin_aid.ModSignType;
import com.dayofpi.super_block_world.main.client.sound.ModSoundGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

import java.util.function.ToIntFunction;

@SuppressWarnings("SameReturnValue")

public class BlockRegistry {
    public static final Block WARP_FRAME = new PillarBlock(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).requiresTool().strength(3.0F, 200F).sounds(BlockSoundGroup.METAL));

    public static final Block BRONZE_PIPE = new BronzePipeBlock(FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_ORANGE).requiresTool().strength(3.0F, 20.0F).nonOpaque().sounds(BlockSoundGroup.COPPER));
    public static final Block BRONZE_PIPE_BODY = new WarpPipeBodyBlock(FabricBlockSettings.copyOf(BRONZE_PIPE));

    public static final Block WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(BRONZE_PIPE).mapColor(MapColor.EMERALD_GREEN));
    public static final Block WARP_PIPE_BODY = new WarpPipeBodyBlock(FabricBlockSettings.copyOf(WARP_PIPE));

    public static final Block EMPTY_BLOCK = new EmptyBlock(FabricBlockSettings.of(Material.METAL, MapColor.SPRUCE_BROWN).requiresTool().strength(3.0F, 200.0F).sounds(BlockSoundGroup.METAL));
    public static final Block QUESTION_BLOCK = new QuestionBlock(FabricBlockSettings.of(Material.METAL, MapColor.YELLOW).requiresTool().strength(3.0F, 200.0F).luminance(3).sounds(BlockSoundGroup.METAL));
    public static final Block COIN_BLOCK = new CoinBlock(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).requiresTool().strength(3.0F, 200.0F).luminance(3).sounds(BlockSoundGroup.METAL));
    public static final Block FAKE_BLOCK = new FakeBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_BROWN).strength(0.6F).sounds(ModSoundGroup.TOADSTONE));
    public static final Block GLOW_BLOCK = new GlowBlock(FabricBlockSettings.of(Material.METAL, MapColor.PALE_YELLOW).requiresTool().strength(3.0F, 200.0F).sounds(BlockSoundGroup.METAL).luminance(15));

    public static final Block JELLYBEAM = new JellybeamBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LIGHT_BLUE).luminance(12).sounds(BlockSoundGroup.SLIME));
    public static final Block HAPPY_CLOUD = new HappyCloudBlock(FabricBlockSettings.of(Material.SNOW_BLOCK, MapColor.WHITE).strength(0.1F).sounds(BlockSoundGroup.SNOW).dynamicBounds());
    public static final Block CLOUD_BLOCK = new CloudBlock(FabricBlockSettings.copyOf(HAPPY_CLOUD));
    public static final Block QUICKSAND = new QuicksandBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.TERRACOTTA_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.SAND).allowsSpawning(BlockRegistry::never).noCollision());
    public static final Block ICICLE = new IcicleBlock(FabricBlockSettings.of(Material.DENSE_ICE).strength(0.2F).nonOpaque().slipperiness(0.9F).sounds(BlockSoundGroup.GLASS));

    public static final Block TRAMPOLINE = new TrampolineBlock(FabricBlockSettings.of(Material.WOOD, MapColor.GREEN).sounds(BlockSoundGroup.WOOD).nonOpaque());
    public static final Block REDSTONE_TRAMPOLINE = new RedstoneTrampolineBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.GRAY).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK));
    public static final Block JUMP_BLOCK = new JumpBlock(FabricBlockSettings.of(Material.WOOD, MapColor.WHITE_GRAY).strength(0.8F).sounds(BlockSoundGroup.WOOD));

    public static final Block DONUT_BLOCK = new DonutBlock(FabricBlockSettings.of(Material.SOIL, MapColor.TERRACOTTA_ORANGE).strength(0.1F).sounds(BlockSoundGroup.GRAVEL).nonOpaque());
    public static final Block STONE_TORCH = new StoneTorchBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_BLUE).requiresTool().strength(1.2F, 6.0F).nonOpaque().luminance(createLightLevelFromLitBlockState(15, 0)));
    public static final Block BOO_LANTERN = new BooLanternBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.DARK_AQUA).strength(0.4F).nonOpaque().luminance(createLightLevelFromLitBlockState(14, 3)));
    public static final Block SPIKE_TRAP = new SpikeTrapBlock(FabricBlockSettings.of(Material.METAL, MapColor.GRAY).requiresTool().strength(1.0F, 7.0F).sounds(BlockSoundGroup.METAL));
    public static final Block GIRDER = new GirderBlock(FabricBlockSettings.of(Material.METAL, MapColor.RED).requiresTool().strength(1.0F, 7.0F).nonOpaque().sounds(BlockSoundGroup.COPPER));

    public static final Block TOADSTOOL_GRASS = new ToadstoolGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LIME).strength(0.5F, 4.0F).sounds(BlockSoundGroup.GRASS).ticksRandomly());
    public static final Block TOADSTOOL_TURF = new Block(FabricBlockSettings.copyOf(TOADSTOOL_GRASS));
    public static final Block TOADSTOOL_SOIL = new ToadstoolSoilBlock(FabricBlockSettings.of(Material.SOIL, MapColor.TERRACOTTA_YELLOW).strength(0.5F, 4.0F).sounds(BlockSoundGroup.GRAVEL));
    public static final Block COARSE_TOADSTOOL_SOIL = new ToadstoolSoilBlock(FabricBlockSettings.copyOf(TOADSTOOL_SOIL));
    public static final Block TOADSTOOL_FARMLAND = new ToadstoolFarmlandBlock(FabricBlockSettings.copyOf(TOADSTOOL_SOIL).ticksRandomly());
    public static final Block TOADSTOOL_PATH = new ToadstoolPathBlock(FabricBlockSettings.copyOf(TOADSTOOL_SOIL).mapColor(MapColor.BROWN));

    public static final Block GRITZY_SAND = new SandBlock(16372053, FabricBlockSettings.of(Material.AGGREGATE, MapColor.PALE_YELLOW).strength(1.0F, 3.0F).sounds(BlockSoundGroup.SAND));
    public static final Block GRITZY_SANDSTONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.YELLOW).strength(1.0F, 3.0F).requiresTool());
    public static final Block CHISELED_GRITZY_SANDSTONE = new Block(FabricBlockSettings.copyOf(GRITZY_SANDSTONE));
    public static final Block CUT_GRITZY_SANDSTONE = new Block(FabricBlockSettings.copyOf(GRITZY_SANDSTONE));
    public static final Block SMOOTH_GRITZY_SANDSTONE = new Block(FabricBlockSettings.copyOf(GRITZY_SANDSTONE));

    public static final Block SEASTONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.EMERALD_GREEN).sounds(ModSoundGroup.TOADSTONE).strength(1.0F).requiresTool());
    public static final Block SEASTONE_BRICKS = new BrickBlock(FabricBlockSettings.copyOf(SEASTONE));
    public static final Block STRAWBERRY_CORAL = new StrawberryCoralBlock(FabricBlockSettings.of(Material.PLANT, MapColor.PINK).strength(0.2F).sounds(BlockSoundGroup.CORAL).nonOpaque());
    public static final Block STRAWBERRY_CORAL_BLOCK = new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.PINK).strength(0.4F).requiresTool().sounds(BlockSoundGroup.CORAL));

    public static final Block VANILLATE = new VanillateBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_BLUE).requiresTool().strength(1.2F, 5.0F));
    public static final Block VANILLATE_BRICKS = new Block(FabricBlockSettings.copyOf(VANILLATE));
    public static final Block VANILLATE_TILES = new Block(FabricBlockSettings.copyOf(VANILLATE));
    public static final Block VANILLATE_CRUMBLE = new SandBlock(12636090, FabricBlockSettings.copyOf(VANILLATE).strength(0.5F).mapColor(MapColor.WHITE_GRAY));

    public static final Block AMETHYST_ORE = new AmethystOreBlock(FabricBlockSettings.copyOf(VANILLATE).strength(1.5F).luminance(5));
    public static final Block BRONZE_ORE = new Block(FabricBlockSettings.copyOf(VANILLATE).strength(1.5F));
    public static final Block RAW_BRONZE_BLOCK = new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).requiresTool().strength(5.0F, 6.0F));
    public static final Block BRONZE_BLOCK = new Block(FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_ORANGE).requiresTool().strength(5.0F, 8.0F).sounds(BlockSoundGroup.METAL));

    public static final Block TOPPED_VANILLATE = new Block(FabricBlockSettings.copyOf(VANILLATE).mapColor(MapColor.WHITE_GRAY));
    public static final Block COAL_TOPPED_VANILLATE = new OreBlock(FabricBlockSettings.copyOf(TOPPED_VANILLATE).strength(1.5F, 6.0F), UniformIntProvider.create(0, 1));
    public static final Block IRON_TOPPED_VANILLATE = new OreBlock(FabricBlockSettings.copyOf(TOPPED_VANILLATE).strength(1.5F, 6.0F), UniformIntProvider.create(0, 1));
    public static final Block GOLD_TOPPED_VANILLATE = new OreBlock(FabricBlockSettings.copyOf(TOPPED_VANILLATE).strength(1.5F, 6.0F), UniformIntProvider.create(0, 1));

    public static final Block FROSTY_VANILLATE = new VanillateBlock(FabricBlockSettings.copyOf(VANILLATE).sounds(ModSoundGroup.FROSTED_STONE).slipperiness(0.98F));
    public static final Block FROSTY_VANILLATE_BRICKS = new Block(FabricBlockSettings.copyOf(FROSTY_VANILLATE));
    public static final Block FROSTY_VANILLATE_TILES = new Block(FabricBlockSettings.copyOf(FROSTY_VANILLATE));
    public static final Block FROSTY_VANILLATE_CRUMBLE = new SandBlock(16119295, FabricBlockSettings.copyOf(FROSTY_VANILLATE).strength(0.5F).mapColor(MapColor.WHITE));
    public static final Block FROSTY_TOPPED_VANILLATE = new Block(FabricBlockSettings.copyOf(FROSTY_VANILLATE).slipperiness(1.0F));
    public static final Block FROSTY_TOPPED_VANILLATE_ORE = new FrostyVanillateOre(FabricBlockSettings.copyOf(FROSTY_VANILLATE).emissiveLighting(BlockRegistry::always).slipperiness(1.0F));

    public static final Block TOADSTONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).requiresTool().strength(1.2F, 1.0F).sounds(ModSoundGroup.TOADSTONE));
    public static final Block GRASSY_TOADSTONE = new ToadstoneGrassBlock(FabricBlockSettings.copyOf(TOADSTONE).mapColor(MapColor.DARK_GREEN).sounds(ModSoundGroup.GRASSY_TOADSTONE));
    public static final Block SMOOTH_TOADSTONE = new Block(FabricBlockSettings.copyOf(TOADSTONE));
    public static final Block CHISELED_TOADSTONE = new Block(FabricBlockSettings.copyOf(TOADSTONE));
    public static final Block TOADSTONE_BRICKS = new BrickBlock(FabricBlockSettings.copyOf(TOADSTONE));

    public static final Block GLOOMSTONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.DARK_AQUA).requiresTool().strength(1.2F, 1.0F).sounds(ModSoundGroup.TOADSTONE));
    public static final Block SMOOTH_GLOOMSTONE = new Block(FabricBlockSettings.copyOf(GLOOMSTONE));
    public static final Block CHISELED_GLOOMSTONE = new Block(FabricBlockSettings.copyOf(GLOOMSTONE));
    public static final Block GLOOMSTONE_BRICKS = new BrickBlock(FabricBlockSettings.copyOf(GLOOMSTONE));

    public static final Block HARDSTONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.LIGHT_GRAY).requiresTool().strength(4.0F, 10.0F));
    public static final Block POLISHED_HARDSTONE = new Block(FabricBlockSettings.copyOf(HARDSTONE));
    public static final Block CHISELED_HARDSTONE = new Block(FabricBlockSettings.copyOf(HARDSTONE));
    public static final Block HARDSTONE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(HARDSTONE));
    public static final Block HARDSTONE_BRICKS = new Block(FabricBlockSettings.copyOf(HARDSTONE));
    public static final Block CHISELED_HARDSTONE_BRICKS = new Block(FabricBlockSettings.copyOf(HARDSTONE_BRICKS));
    public static final Block CRACKED_HARDSTONE_BRICKS = new Block(FabricBlockSettings.copyOf(HARDSTONE_BRICKS).strength(2.0F, 5.0F));

    public static final Block ROYALITE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).requiresTool().strength(2.0F, 5.0F).sounds(BlockSoundGroup.CALCITE));
    public static final Block SMOOTH_ROYALITE = new Block(FabricBlockSettings.copyOf(ROYALITE));
    public static final Block ROYALITE_BRICKS = new Block(FabricBlockSettings.copyOf(ROYALITE));
    public static final Block CRACKED_ROYALITE_BRICKS = new Block(FabricBlockSettings.copyOf(ROYALITE));
    public static final Block CHISELED_ROYALITE_BRICKS = new Block(FabricBlockSettings.copyOf(ROYALITE));
    public static final Block CERISE_ORE = new Block(FabricBlockSettings.copyOf(ROYALITE).strength(2.3F));

    public static final Block CERISE_BLOCK =  new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_PINK).requiresTool().strength(2.0F, 5.0F));
    public static final Block CERISE_BRICKS = new Block(FabricBlockSettings.copyOf(CERISE_BLOCK));
    public static final Block CERISE_TILES = new Block(FabricBlockSettings.copyOf(CERISE_BLOCK));

    public static final Block GOLDEN_BRICKS = new BrickBlock(FabricBlockSettings.copyOf(TOADSTONE_BRICKS).sounds(BlockSoundGroup.METAL).mapColor(MapColor.GOLD));
    public static final Block CRYSTAL_BRICKS = new BrickBlock(FabricBlockSettings.copyOf(TOADSTONE_BRICKS).sounds(BlockSoundGroup.AMETHYST_BLOCK).mapColor(MapColor.PURPLE));

    public static final Block AMANITA_LOG = new WoodBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block AMANITA_WOOD = new WoodBlock(FabricBlockSettings.copyOf(AMANITA_LOG));
    public static final Block STRIPPED_AMANITA_LOG = new PillarBlock(FabricBlockSettings.copyOf(AMANITA_LOG));
    public static final Block STRIPPED_AMANITA_WOOD = new PillarBlock(FabricBlockSettings.copyOf(AMANITA_LOG).mapColor(MapColor.TERRACOTTA_YELLOW));

    public static final Block DARK_AMANITA_LOG = new WoodBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block DARK_AMANITA_WOOD = new WoodBlock(FabricBlockSettings.copyOf(DARK_AMANITA_LOG));
    public static final Block STRIPPED_DARK_AMANITA_LOG = new WoodBlock(FabricBlockSettings.copyOf(DARK_AMANITA_LOG));
    public static final Block STRIPPED_DARK_AMANITA_WOOD = new WoodBlock(FabricBlockSettings.copyOf(DARK_AMANITA_LOG).mapColor(MapColor.TERRACOTTA_BROWN));

    public static final Block AMANITA_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_YELLOW).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block DARK_AMANITA_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_BROWN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));

    public static final Block AMANITA_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlockRegistry::canSpawnOnLeaves).suffocates(BlockRegistry::never).blockVision(BlockRegistry::never));
    public static final Block FRUITING_AMANITA_LEAVES = new LeavesBlock(FabricBlockSettings.copyOf(AMANITA_LEAVES));
    public static final Block DARK_AMANITA_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlockRegistry::canSpawnOnLeaves).suffocates(BlockRegistry::never).blockVision(BlockRegistry::never));
    public static final Block FRUITING_DARK_AMANITA_LEAVES = new LeavesBlock(FabricBlockSettings.copyOf(DARK_AMANITA_LEAVES));
    public static final Block BEANSTALK_BLOCK = new BeanstalkBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.GREEN).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM));

    public static final Block AMANITA_DOOR = new DoorBlock(FabricBlockSettings.copyOf(AMANITA_PLANKS).strength(3.0F).nonOpaque()){};
    public static final Block AMANITA_TRAPDOOR = new TrapdoorBlock(FabricBlockSettings.copyOf(AMANITA_PLANKS).strength(3.0F).nonOpaque().allowsSpawning(BlockRegistry::never)){};
    public static final Block AMANITA_BUTTON = new WoodenButtonBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)){};
    public static final Block AMANITA_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)){};
    public static final Block AMANITA_SIGN = new SignBlock(FabricBlockSettings.copyOf(AMANITA_PLANKS).strength(1.0F).noCollision(), ModSignType.AMANITA);
    public static final Block AMANITA_WALL_SIGN = new WallSignBlock(FabricBlockSettings.copyOf(AMANITA_SIGN), ModSignType.AMANITA);

    public static final Block DARK_AMANITA_DOOR = new DoorBlock(FabricBlockSettings.copyOf(DARK_AMANITA_PLANKS).strength(3.0F).nonOpaque()){};
    public static final Block DARK_AMANITA_TRAPDOOR = new TrapdoorBlock(FabricBlockSettings.copyOf(DARK_AMANITA_PLANKS).strength(3.0F).nonOpaque().allowsSpawning(BlockRegistry::never)){};
    public static final Block DARK_AMANITA_BUTTON = new WoodenButtonBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)){};
    public static final Block DARK_AMANITA_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)){};
    public static final Block DARK_AMANITA_SIGN = new SignBlock(FabricBlockSettings.copyOf(DARK_AMANITA_PLANKS).strength(1.0F).noCollision(), ModSignType.DARK_AMANITA);
    public static final Block DARK_AMANITA_WALL_SIGN = new WallSignBlock(FabricBlockSettings.copyOf(DARK_AMANITA_SIGN), ModSignType.DARK_AMANITA);

    public static final Block POISON = new PoisonBlock(FluidRegistry.STILL_POISON, FabricBlockSettings.of(Material.LAVA, MapColor.PURPLE).noCollision().ticksRandomly().strength(100.0F).luminance(7));

    private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public static boolean always(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int onLevel, int offLevel) {
        return (state) -> (Boolean) state.get(Properties.LIT) ? onLevel : offLevel;
    }

    private static void registerBlock(String id, Block type) {
        Registry.register(Registry.BLOCK, new Identifier(Main.MOD_ID, id), type);
    }

    public static void registerBlocks() {
        registerBlock("warp_frame", WARP_FRAME);
        registerBlock("bronze_pipe", BRONZE_PIPE);
        registerBlock("bronze_pipe_body", BRONZE_PIPE_BODY);
        registerBlock("warp_pipe", WARP_PIPE);
        registerBlock("warp_pipe_body", WARP_PIPE_BODY);
        registerBlock("empty_block", EMPTY_BLOCK);
        registerBlock("glow_block", GLOW_BLOCK);
        registerBlock("question_block", QUESTION_BLOCK);
        registerBlock("coin_block", COIN_BLOCK);
        registerBlock("spike_trap", SPIKE_TRAP);
        registerBlock("girder", GIRDER);
        registerBlock("jump_block", JUMP_BLOCK);
        registerBlock("redstone_trampoline", REDSTONE_TRAMPOLINE);
        registerBlock("stone_torch", STONE_TORCH);
        registerBlock("boo_lantern", BOO_LANTERN);
        registerBlock("donut_block", DONUT_BLOCK);
        registerBlock("trampoline", TRAMPOLINE);
        registerBlock("toadstool_grass", TOADSTOOL_GRASS);
        registerBlock("toadstool_turf", TOADSTOOL_TURF);
        registerBlock("toadstool_soil", TOADSTOOL_SOIL);
        registerBlock("coarse_toadstool_soil", COARSE_TOADSTOOL_SOIL);
        registerBlock("toadstool_farmland", TOADSTOOL_FARMLAND);
        registerBlock("toadstool_path", TOADSTOOL_PATH);
        registerBlock("gritzy_sand", GRITZY_SAND);
        registerBlock("quicksand", QUICKSAND);
        registerBlock("gritzy_sandstone", GRITZY_SANDSTONE);
        registerBlock("chiseled_gritzy_sandstone", CHISELED_GRITZY_SANDSTONE);
        registerBlock("cut_gritzy_sandstone", CUT_GRITZY_SANDSTONE);
        registerBlock("smooth_gritzy_sandstone", SMOOTH_GRITZY_SANDSTONE);
        registerBlock("gritzy_sandstone_slab", VariantBlocks.GRITZY_SANDSTONE_SLAB);
        registerBlock("gritzy_sandstone_stairs", VariantBlocks.GRITZY_SANDSTONE_STAIRS);
        registerBlock("happy_cloud", HAPPY_CLOUD);
        registerBlock("cloud_block", CLOUD_BLOCK);
        registerBlock("cloud_slab", VariantBlocks.CLOUD_SLAB);
        registerBlock("cloud_stairs", VariantBlocks.CLOUD_STAIRS);
        registerBlock("vanillate", VANILLATE);
        registerBlock("vanillate_bricks", VANILLATE_BRICKS);
        registerBlock("vanillate_tiles", VANILLATE_TILES);
        registerBlock("vanillate_crumble", VANILLATE_CRUMBLE);
        registerBlock("vanillate_slab", VariantBlocks.VANILLATE_SLAB);
        registerBlock("vanillate_stairs", VariantBlocks.VANILLATE_STAIRS);
        registerBlock("vanillate_brick_slab", VariantBlocks.VANILLATE_BRICK_SLAB);
        registerBlock("vanillate_brick_stairs", VariantBlocks.VANILLATE_BRICK_STAIRS);
        registerBlock("frosty_vanillate", FROSTY_VANILLATE);
        registerBlock("frosty_vanillate_bricks", FROSTY_VANILLATE_BRICKS);
        registerBlock("frosty_vanillate_tiles", FROSTY_VANILLATE_TILES);
        registerBlock("frosty_vanillate_crumble", FROSTY_VANILLATE_CRUMBLE);
        registerBlock("frosty_vanillate_slab", VariantBlocks.FROSTY_VANILLATE_SLAB);
        registerBlock("frosty_vanillate_stairs", VariantBlocks.FROSTY_VANILLATE_STAIRS);
        registerBlock("frosty_vanillate_brick_slab", VariantBlocks.FROSTY_VANILLATE_BRICK_SLAB);
        registerBlock("frosty_vanillate_brick_stairs", VariantBlocks.FROSTY_VANILLATE_BRICK_STAIRS);
        registerBlock("frosty_topped_vanillate", FROSTY_TOPPED_VANILLATE);
        registerBlock("frosty_topped_vanillate_ore", FROSTY_TOPPED_VANILLATE_ORE);
        registerBlock("icicle", ICICLE);
        registerBlock("topped_vanillate", TOPPED_VANILLATE);
        registerBlock("coal_topped_vanillate", COAL_TOPPED_VANILLATE);
        registerBlock("iron_topped_vanillate", IRON_TOPPED_VANILLATE);
        registerBlock("gold_topped_vanillate", GOLD_TOPPED_VANILLATE);
        registerBlock("bronze_ore", BRONZE_ORE);
        registerBlock("amethyst_ore", AMETHYST_ORE);
        registerBlock("raw_bronze_block", RAW_BRONZE_BLOCK);
        registerBlock("bronze_block", BRONZE_BLOCK);
        registerBlock("bronze_slab", VariantBlocks.BRONZE_SLAB);
        registerBlock("bronze_stairs", VariantBlocks.BRONZE_STAIRS);
        registerBlock("fake_block", FAKE_BLOCK);
        registerBlock("toadstone", TOADSTONE);
        registerBlock("grassy_toadstone", GRASSY_TOADSTONE);
        registerBlock("smooth_toadstone", SMOOTH_TOADSTONE);
        registerBlock("chiseled_toadstone", CHISELED_TOADSTONE);
        registerBlock("toadstone_bricks", TOADSTONE_BRICKS);
        registerBlock("toadstone_slab", VariantBlocks.TOADSTONE_SLAB);
        registerBlock("toadstone_stairs", VariantBlocks.TOADSTONE_STAIRS);
        registerBlock("toadstone_wall", VariantBlocks.TOADSTONE_WALL);
        registerBlock("toadstone_brick_slab", VariantBlocks.TOADSTONE_BRICK_SLAB);
        registerBlock("toadstone_brick_stairs", VariantBlocks.TOADSTONE_BRICK_STAIRS);
        registerBlock("toadstone_brick_wall", VariantBlocks.TOADSTONE_BRICK_WALL);
        registerBlock("gloomstone", GLOOMSTONE);
        registerBlock("smooth_gloomstone", SMOOTH_GLOOMSTONE);
        registerBlock("chiseled_gloomstone", CHISELED_GLOOMSTONE);
        registerBlock("gloomstone_bricks", GLOOMSTONE_BRICKS);
        registerBlock("gloomstone_slab", VariantBlocks.GLOOMSTONE_SLAB);
        registerBlock("gloomstone_stairs", VariantBlocks.GLOOMSTONE_STAIRS);
        registerBlock("gloomstone_wall", VariantBlocks.GLOOMSTONE_WALL);
        registerBlock("gloomstone_brick_slab", VariantBlocks.GLOOMSTONE_BRICK_SLAB);
        registerBlock("gloomstone_brick_stairs", VariantBlocks.GLOOMSTONE_BRICK_STAIRS);
        registerBlock("gloomstone_brick_wall", VariantBlocks.GLOOMSTONE_BRICK_WALL);
        registerBlock("golden_bricks", GOLDEN_BRICKS);
        registerBlock("golden_brick_slab", VariantBlocks.GOLDEN_BRICK_SLAB);
        registerBlock("golden_brick_stairs", VariantBlocks.GOLDEN_BRICK_STAIRS);
        registerBlock("golden_brick_wall", VariantBlocks.GOLDEN_BRICK_WALL);
        registerBlock("crystal_bricks", CRYSTAL_BRICKS);
        registerBlock("crystal_brick_slab", VariantBlocks.CRYSTAL_BRICK_SLAB);
        registerBlock("crystal_brick_stairs", VariantBlocks.CRYSTAL_BRICK_STAIRS);
        registerBlock("crystal_brick_wall", VariantBlocks.CRYSTAL_BRICK_WALL);
        registerBlock("hardstone", HARDSTONE);
        registerBlock("polished_hardstone", POLISHED_HARDSTONE);
        registerBlock("chiseled_hardstone", CHISELED_HARDSTONE);
        registerBlock("hardstone_pillar", HARDSTONE_PILLAR);
        registerBlock("hardstone_bricks", HARDSTONE_BRICKS);
        registerBlock("chiseled_hardstone_bricks", CHISELED_HARDSTONE_BRICKS);
        registerBlock("cracked_hardstone_bricks", CRACKED_HARDSTONE_BRICKS);
        registerBlock("polished_hardstone_slab", VariantBlocks.POLISHED_HARDSTONE_SLAB);
        registerBlock("polished_hardstone_stairs", VariantBlocks.POLISHED_HARDSTONE_STAIRS);
        registerBlock("polished_hardstone_wall", VariantBlocks.POLISHED_HARDSTONE_WALL);
        registerBlock("hardstone_brick_slab", VariantBlocks.HARDSTONE_BRICK_SLAB);
        registerBlock("hardstone_brick_stairs", VariantBlocks.HARDSTONE_BRICK_STAIRS);
        registerBlock("hardstone_brick_wall", VariantBlocks.HARDSTONE_BRICK_WALL);
        registerBlock("royalite", ROYALITE);
        registerBlock("smooth_royalite", SMOOTH_ROYALITE);
        registerBlock("royalite_bricks", ROYALITE_BRICKS);
        registerBlock("cracked_royalite_bricks", CRACKED_ROYALITE_BRICKS);
        registerBlock("chiseled_royalite_bricks", CHISELED_ROYALITE_BRICKS);
        registerBlock("royalite_brick_slab", VariantBlocks.ROYALITE_BRICK_SLAB);
        registerBlock("royalite_brick_stairs", VariantBlocks.ROYALITE_BRICK_STAIRS);
        registerBlock("royalite_brick_wall", VariantBlocks.ROYALITE_BRICK_WALL);
        registerBlock("cerise_ore", CERISE_ORE);
        registerBlock("cerise_block", CERISE_BLOCK);
        registerBlock("cerise_bricks", CERISE_BRICKS);
        registerBlock("cerise_tiles", CERISE_TILES);
        registerBlock("cerise_brick_slab", VariantBlocks.CERISE_BRICK_SLAB);
        registerBlock("cerise_tile_slab", VariantBlocks.CERISE_TILE_SLAB);
        registerBlock("cerise_brick_stairs", VariantBlocks.CERISE_BRICK_STAIRS);
        registerBlock("cerise_tile_stairs", VariantBlocks.CERISE_TILE_STAIRS);
        registerBlock("amanita_log", AMANITA_LOG);
        registerBlock("amanita_wood", AMANITA_WOOD);
        registerBlock("stripped_amanita_log", STRIPPED_AMANITA_LOG);
        registerBlock("stripped_amanita_wood", STRIPPED_AMANITA_WOOD);
        registerBlock("dark_amanita_log", DARK_AMANITA_LOG);
        registerBlock("dark_amanita_wood", DARK_AMANITA_WOOD);
        registerBlock("stripped_dark_amanita_log", STRIPPED_DARK_AMANITA_LOG);
        registerBlock("stripped_dark_amanita_wood", STRIPPED_DARK_AMANITA_WOOD);
        registerBlock("amanita_planks", AMANITA_PLANKS);
        registerBlock("dark_amanita_planks", DARK_AMANITA_PLANKS);
        registerBlock("amanita_slab", VariantBlocks.AMANITA_SLAB);
        registerBlock("amanita_stairs", VariantBlocks.AMANITA_STAIRS);
        registerBlock("dark_amanita_slab", VariantBlocks.DARK_AMANITA_SLAB);
        registerBlock("dark_amanita_stairs", VariantBlocks.DARK_AMANITA_STAIRS);
        registerBlock("amanita_fence", VariantBlocks.AMANITA_FENCE);
        registerBlock("amanita_fence_gate", VariantBlocks.AMANITA_FENCE_GATE);
        registerBlock("amanita_door", AMANITA_DOOR);
        registerBlock("amanita_trapdoor", AMANITA_TRAPDOOR);
        registerBlock("amanita_button", AMANITA_BUTTON);
        registerBlock("amanita_pressure_plate", AMANITA_PRESSURE_PLATE);
        registerBlock("amanita_sign", AMANITA_SIGN);
        registerBlock("amanita_wall_sign", AMANITA_WALL_SIGN);
        registerBlock("dark_amanita_fence", VariantBlocks.DARK_AMANITA_FENCE);
        registerBlock("dark_amanita_fence_gate", VariantBlocks.DARK_AMANITA_FENCE_GATE);
        registerBlock("dark_amanita_door", DARK_AMANITA_DOOR);
        registerBlock("dark_amanita_trapdoor", DARK_AMANITA_TRAPDOOR);
        registerBlock("dark_amanita_button", DARK_AMANITA_BUTTON);
        registerBlock("dark_amanita_pressure_plate", DARK_AMANITA_PRESSURE_PLATE);
        registerBlock("dark_amanita_sign", DARK_AMANITA_SIGN);
        registerBlock("dark_amanita_wall_sign", DARK_AMANITA_WALL_SIGN);
        registerBlock("amanita_leaves", AMANITA_LEAVES);
        registerBlock("fruiting_amanita_leaves", FRUITING_AMANITA_LEAVES);
        registerBlock("dark_amanita_leaves", DARK_AMANITA_LEAVES);
        registerBlock("fruiting_dark_amanita_leaves", FRUITING_DARK_AMANITA_LEAVES);
        registerBlock("amanita_sapling", PlantBlocks.AMANITA_SAPLING);
        registerBlock("dark_amanita_sapling", PlantBlocks.DARK_AMANITA_SAPLING);
        registerBlock("horsetail", PlantBlocks.HORSETAIL);
        registerBlock("bush", PlantBlocks.BUSH);
        registerBlock("vegetable", PlantBlocks.VEGETABLE);
        registerBlock("beanstalk", PlantBlocks.BEANSTALK);
        registerBlock("beanstalk_plant", PlantBlocks.BEANSTALK_PLANT);
        registerBlock("budding_beanstalk", PlantBlocks.BUDDING_BEANSTALK);
        registerBlock("piranha_lily", PlantBlocks.PIRANHA_LILY);
        registerBlock("pit_plant", PlantBlocks.PIT_PLANT);
        registerBlock("muncher", PlantBlocks.MUNCHER);
        registerBlock("frozen_muncher", PlantBlocks.FROZEN_MUNCHER);
        registerBlock("cave_mushrooms", PlantBlocks.CAVE_MUSHROOMS);
        registerBlock("strawberry_coral", STRAWBERRY_CORAL);
        registerBlock("strawberry_coral_block", STRAWBERRY_CORAL_BLOCK);
        registerBlock("jellybeam", JELLYBEAM);
        registerBlock("seastone", SEASTONE);
        registerBlock("seastone_bricks", SEASTONE_BRICKS);
        registerBlock("seastone_brick_slab", VariantBlocks.SEASTONE_BRICK_SLAB);
        registerBlock("seastone_brick_stairs", VariantBlocks.SEASTONE_BRICK_STAIRS);
        registerBlock("seastone_brick_wall", VariantBlocks.SEASTONE_BRICK_WALL);
        registerBlock("beanstalk_block", BEANSTALK_BLOCK);
        registerBlock("mushroom_stem", MushroomBlocks.MUSHROOM_STEM);
        registerBlock("brown_mushroom_cap", MushroomBlocks.BROWN_MUSHROOM_CAP);
        registerBlock("red_mushroom_cap", MushroomBlocks.RED_MUSHROOM_CAP);
        registerBlock("yellow_mushroom_cap", MushroomBlocks.YELLOW_MUSHROOM_CAP);
        registerBlock("green_mushroom_cap", MushroomBlocks.GREEN_MUSHROOM_CAP);
        registerBlock("pink_mushroom_cap", MushroomBlocks.PINK_MUSHROOM_CAP);
        registerBlock("purple_mushroom_cap", MushroomBlocks.PURPLE_MUSHROOM_CAP);
        registerBlock("orange_mushroom_cap", MushroomBlocks.ORANGE_MUSHROOM_CAP);
        registerBlock("yellow_mushroom", MushroomBlocks.YELLOW_MUSHROOM);
        registerBlock("green_mushroom", MushroomBlocks.GREEN_MUSHROOM);
        registerBlock("pink_mushroom", MushroomBlocks.PINK_MUSHROOM);
        registerBlock("purple_mushroom", MushroomBlocks.PURPLE_MUSHROOM);
        registerBlock("orange_mushroom", MushroomBlocks.ORANGE_MUSHROOM);
        registerBlock("amanita_carpet", PlantBlocks.AMANITA_CARPET);
        registerBlock("yellow_flowerbed", PlantBlocks.YELLOW_FLOWERBED);
        registerBlock("white_flowerbed", PlantBlocks.WHITE_FLOWERBED);
        registerBlock("blue_songflower", PlantBlocks.BLUE_SONGFLOWER);
        registerBlock("pink_songflower", PlantBlocks.PINK_SONGFLOWER);
        registerBlock("yellow_songflower", PlantBlocks.YELLOW_SONGFLOWER);
        registerBlock("pawflower", PlantBlocks.PAWFLOWER);
        registerBlock("fire_tulip", PlantBlocks.FIRE_TULIP);
        registerBlock("white_bronze", ColoredBlocks.WHITE_BRONZE);
        registerBlock("orange_bronze", ColoredBlocks.ORANGE_BRONZE);
        registerBlock("magenta_bronze", ColoredBlocks.MAGENTA_BRONZE);
        registerBlock("light_blue_bronze", ColoredBlocks.LIGHT_BLUE_BRONZE);
        registerBlock("yellow_bronze", ColoredBlocks.YELLOW_BRONZE);
        registerBlock("lime_bronze", ColoredBlocks.LIME_BRONZE);
        registerBlock("pink_bronze", ColoredBlocks.PINK_BRONZE);
        registerBlock("gray_bronze", ColoredBlocks.GRAY_BRONZE);
        registerBlock("light_gray_bronze", ColoredBlocks.LIGHT_GRAY_BRONZE);
        registerBlock("cyan_bronze", ColoredBlocks.CYAN_BRONZE);
        registerBlock("purple_bronze", ColoredBlocks.PURPLE_BRONZE);
        registerBlock("blue_bronze", ColoredBlocks.BLUE_BRONZE);
        registerBlock("brown_bronze", ColoredBlocks.BROWN_BRONZE);
        registerBlock("green_bronze", ColoredBlocks.GREEN_BRONZE);
        registerBlock("red_bronze", ColoredBlocks.RED_BRONZE);
        registerBlock("black_bronze", ColoredBlocks.BLACK_BRONZE);
        registerBlock("poison", POISON);
        registerBlock("potted_fire_tulip", PottedBlocks.POTTED_FIRE_TULIP);
        registerBlock("potted_muncher", PottedBlocks.POTTED_MUNCHER);
        registerBlock("potted_cave_mushrooms", PottedBlocks.POTTED_CAVE_MUSHROOMS);
        registerBlock("potted_piranha_lily", PottedBlocks.POTTED_PIRANHA_LILY);
        registerBlock("potted_pit_plant", PottedBlocks.POTTED_PIT_PLANT);
        registerBlock("potted_blue_songflower", PottedBlocks.POTTED_BLUE_SONGFLOWER);
        registerBlock("potted_pink_songflower", PottedBlocks.POTTED_PINK_SONGFLOWER);
        registerBlock("potted_yellow_songflower", PottedBlocks.POTTED_YELLOW_SONGFLOWER);
        registerBlock("potted_pawflower", PottedBlocks.POTTED_PAWFLOWER);
        registerBlock("potted_yellow_mushroom", PottedBlocks.POTTED_YELLOW_MUSHROOM);
        registerBlock("potted_green_mushroom", PottedBlocks.POTTED_GREEN_MUSHROOM);
        registerBlock("potted_pink_mushroom", PottedBlocks.POTTED_PINK_MUSHROOM);
        registerBlock("potted_purple_mushroom", PottedBlocks.POTTED_PURPLE_MUSHROOM);
        registerBlock("potted_orange_mushroom", PottedBlocks.POTTED_ORANGE_MUSHROOM);
        registerBlock("potted_amanita_sapling", PottedBlocks.POTTED_AMANITA_SAPLING);
        registerBlock("potted_dark_amanita_sapling", PottedBlocks.POTTED_DARK_AMANITA_SAPLING);
        registerBlock("potted_horsetail", PottedBlocks.POTTED_HORSETAIL);
        registerBlock("potted_bush", PottedBlocks.POTTED_BUSH);
        registerBlock("potted_beanstalk", PottedBlocks.POTTED_BEANSTALK);
        registerBlock("potted_budding_beanstalk", PottedBlocks.POTTED_BUDDING_BEANSTALK);
        BlockEntityRegistry.registerBlockEntities();
    }
}