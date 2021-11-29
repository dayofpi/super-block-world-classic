package com.dayofpi.sbw_main.item.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.block.registry.categories.ColoredBlocks;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.registry.categories.MushroomBlocks;
import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
import com.dayofpi.sbw_main.block.registry.categories.VariantBlocks;
import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.item.types.*;
import com.dayofpi.sbw_main.misc.EnumBoats;
import com.dayofpi.sbw_main.world.registry.ModFluids;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;


public class ModItems {
    public static final ArmorMaterial GREEN_SHELL_ARMOR_MATERIAL = new GreenShellArmorMaterial();
    public static final ArmorMaterial RED_SHELL_ARMOR_MATERIAL = new RedShellArmorMaterial();
    public static final ArmorMaterial BUZZY_ARMOR_MATERIAL = new BuzzyArmorMaterial();
    public static final ArmorMaterial JUMP_ARMOR_MATERIAL = new JumpArmorMaterial();

    public static final Item POWER_STAR = new PowerStarItem(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Item POWER_DUST = new Item(new FabricItemSettings());
    public static final Item YOSHI_FRUIT = new Item(new FabricItemSettings().food(FoodComponents.YOSHI_FRUIT).group(CreativeTabs.ITEM_GROUP));
    public static final Item YOSHI_COOKIE = new Item(new FabricItemSettings().food(FoodComponents.YOSHI_COOKIE).group(CreativeTabs.ITEM_GROUP));
    public static final Item TURNIP = new TurnipItem(new FabricItemSettings().maxCount(16).group(CreativeTabs.ITEM_GROUP));
    public static final Item HAMMER = new HammerItem(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxDamage(50));
    public static final Item BOMB = new BombItem(new FabricItemSettings().maxCount(16).group(CreativeTabs.ITEM_GROUP));
    public static final Item SUPER_MUSHROOM = new Item(new FabricItemSettings().food(FoodComponents.SUPER_MUSHROOM).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item GOLDEN_MUSHROOM = new Item(new FabricItemSettings().food(FoodComponents.GOLDEN_MUSHROOM).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item ONE_UP = new OneUpItem(new FabricItemSettings().food(FoodComponents.ONE_UP).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item POISON_MUSHROOM = new Item(new FabricItemSettings().food(FoodComponents.POISON_MUSHROOM).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item FIRE_FLOWER = new FireFlowerItem(new FabricItemSettings().maxDamage(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item ICE_FLOWER = new IceFlowerItem(new FabricItemSettings().maxDamage(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item SUPER_STAR = new SuperStarItem(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).rarity(Rarity.RARE));
    public static final Item COIN = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item RAW_BRONZE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BRONZE_INGOT = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item TROOP_HIDE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item CLOUD_PUFF = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item SPIKE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BUZZY_SHELL_PIECE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item GREEN_SHELL = new ArmorItem(GREEN_SHELL_ARMOR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item RED_SHELL = new ArmorItem(RED_SHELL_ARMOR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BUZZY_SHELL = new ArmorItem(BUZZY_ARMOR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item JUMP_BOOTS = new ArmorItem(JUMP_ARMOR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BOTTLE_O_GHOST = new Item(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).group(CreativeTabs.ITEM_GROUP));
    public static final Item GREEN_MUSHROOM_ON_A_STICK = new OnAStickItem<>(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxDamage(25), ModEntities.BUZZY_BEETLE, 2);
    public static final Item AMANITA_BOAT = new BoatItem(EnumBoats.AMANITA, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxCount(1));
    public static final Item POISON_BUCKET = new PoisonBucketItem(ModFluids.STILL_POISON, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1).group(CreativeTabs.ITEM_GROUP));
    public static final Item MOO_MOO_SPAWN_EGG = new SpawnEggItem(ModEntities.MOO_MOO, 15985102, 4605249, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item GOOMBA_SPAWN_EGG = new SpawnEggItem(ModEntities.GOOMBA, 12544546, 14922613, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item PARAGOOMBA_SPAWN_EGG = new SpawnEggItem(ModEntities.PARAGOOMBA, 12544546, 16767400, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item KOOPA_TROOPA_SPAWN_EGG = new SpawnEggItem(ModEntities.KOOPA_TROOPA, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item PARATROOPA_SPAWN_EGG = new SpawnEggItem(ModEntities.PARATROOPA, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item HAMMER_BRO_SPAWN_EGG = new SpawnEggItem(ModEntities.HAMMER_BRO, 16570144, 9341835, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item FIRE_BRO_SPAWN_EGG = new SpawnEggItem(ModEntities.FIRE_BRO, 16570144, 15552066, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BOB_OMB_SPAWN_EGG = new SpawnEggItem(ModEntities.BOB_OMB, 658473, 5688063, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BOO_SPAWN_EGG = new SpawnEggItem(ModEntities.BOO, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BUZZY_BEETLE_SPAWN_EGG = new SpawnEggItem(ModEntities.BUZZY_BEETLE, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item SPIKE_TOP_SPAWN_EGG = new SpawnEggItem(ModEntities.SPIKE_TOP, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item NIPPER_PLANT_SPAWN_EGG = new SpawnEggItem(ModEntities.NIPPER_PLANT, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item STINGBY_SPAWN_EGG = new SpawnEggItem(ModEntities.STINGBY, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item ROTTEN_MUSHROOM_SPAWN_EGG = new SpawnEggItem(ModEntities.ROTTEN_MUSHROOM, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item THWOMP_SPAWN_EGG = new SpawnEggItem(ModEntities.THWOMP, 10530706, 12572889, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item FAKE_BLOCK_SPAWN_EGG = new SpawnEggItem(ModEntities.FAKE_BLOCK, 10042394, 10042394, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));

    private static void registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, id), item);
    }

    public static void registerItems() {
        registerBlockItems();
        registerItem("power_star", POWER_STAR);
        registerItem("power_dust", POWER_DUST);
        registerItem("yoshi_fruit", YOSHI_FRUIT);
        registerItem("yoshi_cookie", YOSHI_COOKIE);
        registerItem("turnip", TURNIP);
        registerItem("hammer", HAMMER);
        registerItem("bomb", BOMB);
        registerItem("green_shell", GREEN_SHELL);
        registerItem("red_shell", RED_SHELL);
        registerItem("buzzy_shell", BUZZY_SHELL);
        registerItem("jump_boots", JUMP_BOOTS);
        registerItem("super_mushroom", SUPER_MUSHROOM);
        registerItem("golden_mushroom", GOLDEN_MUSHROOM);
        registerItem("one_up", ONE_UP);
        registerItem("poison_mushroom", POISON_MUSHROOM);
        registerItem("fire_flower", FIRE_FLOWER);
        registerItem("ice_flower", ICE_FLOWER);
        registerItem("super_star", SUPER_STAR);
        registerItem("coin", COIN);
        registerItem("raw_bronze", RAW_BRONZE);
        registerItem("bronze_ingot", BRONZE_INGOT);
        registerItem("troop_hide", TROOP_HIDE);
        registerItem("cloud_puff", CLOUD_PUFF);
        registerItem("spike", SPIKE);
        registerItem("buzzy_shell_piece", BUZZY_SHELL_PIECE);
        registerItem("green_mushroom_on_a_stick", GREEN_MUSHROOM_ON_A_STICK);
        registerItem("amanita_boat", AMANITA_BOAT);
        registerItem("bottle_o_ghost", BOTTLE_O_GHOST);
        registerItem("poison_bucket", POISON_BUCKET);
        registerItem("spawn_eggs/moo_moo", MOO_MOO_SPAWN_EGG);
        registerItem("spawn_eggs/goomba", GOOMBA_SPAWN_EGG);
        registerItem("spawn_eggs/paragoomba", PARAGOOMBA_SPAWN_EGG);
        registerItem("spawn_eggs/koopa_troopa", KOOPA_TROOPA_SPAWN_EGG);
        registerItem("spawn_eggs/paratroopa", PARATROOPA_SPAWN_EGG);
        registerItem("spawn_eggs/hammer_bro", HAMMER_BRO_SPAWN_EGG);
        registerItem("spawn_eggs/fire_bro", FIRE_BRO_SPAWN_EGG);
        registerItem("spawn_eggs/bob_omb", BOB_OMB_SPAWN_EGG);
        registerItem("spawn_eggs/boo", BOO_SPAWN_EGG);
        registerItem("spawn_eggs/buzzy_beetle", BUZZY_BEETLE_SPAWN_EGG);
        registerItem("spawn_eggs/spike_top", SPIKE_TOP_SPAWN_EGG);
        registerItem("spawn_eggs/nipper_plant", NIPPER_PLANT_SPAWN_EGG);
        registerItem("spawn_eggs/stingby", STINGBY_SPAWN_EGG);
        registerItem("spawn_eggs/rotten_mushroom", ROTTEN_MUSHROOM_SPAWN_EGG);
        registerItem("spawn_eggs/thwomp", THWOMP_SPAWN_EGG);
        registerItem("spawn_eggs/fake_block", FAKE_BLOCK_SPAWN_EGG);
    }

    private static void registerBlockItem(String id, Block block) {
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(CreativeTabs.BLOCK_GROUP)));
    }

    public static void registerBlockItems() {
        registerBlockItem("warp_frame", ModBlocks.WARP_FRAME);

        registerBlockItem("question_block", ModBlocks.QUESTION_BLOCK);
        registerBlockItem("coin_block", ModBlocks.COIN_BLOCK);
        registerBlockItem("fake_block", ModBlocks.FAKE_BLOCK);
        registerBlockItem("empty_block", ModBlocks.EMPTY_BLOCK);

        registerBlockItem("brown_mushroom_cap", MushroomBlocks.BROWN_MUSHROOM_CAP);
        registerBlockItem("red_mushroom_cap", MushroomBlocks.RED_MUSHROOM_CAP);
        registerBlockItem("green_mushroom_cap", MushroomBlocks.GREEN_MUSHROOM_CAP);
        registerBlockItem("pink_mushroom_cap", MushroomBlocks.PINK_MUSHROOM_CAP);
        registerBlockItem("yellow_mushroom_cap", MushroomBlocks.YELLOW_MUSHROOM_CAP);
        registerBlockItem("orange_mushroom_cap", MushroomBlocks.ORANGE_MUSHROOM_CAP);
        registerBlockItem("purple_mushroom_cap", MushroomBlocks.PURPLE_MUSHROOM_CAP);
        registerBlockItem("redstone_trampoline", ModBlocks.REDSTONE_TRAMPOLINE);

        registerBlockItem("green_mushroom", MushroomBlocks.GREEN_MUSHROOM);
        registerBlockItem("pink_mushroom", MushroomBlocks.PINK_MUSHROOM);
        registerBlockItem("yellow_mushroom", MushroomBlocks.YELLOW_MUSHROOM);
        registerBlockItem("orange_mushroom", MushroomBlocks.ORANGE_MUSHROOM);
        registerBlockItem("purple_mushroom", MushroomBlocks.PURPLE_MUSHROOM);

        registerBlockItem("amanita_sapling", PlantBlocks.AMANITA_SAPLING);
        registerBlockItem("bush", PlantBlocks.BUSH);
        registerBlockItem("horsetail", PlantBlocks.HORSETAIL);
        registerBlockItem("vegetable", PlantBlocks.VEGETABLE);

        registerBlockItem("pawflower", PlantBlocks.PAWFLOWER);
        registerBlockItem("blue_songflower", PlantBlocks.BLUE_SONGFLOWER);
        registerBlockItem("pink_songflower", PlantBlocks.PINK_SONGFLOWER);
        registerBlockItem("yellow_songflower", PlantBlocks.YELLOW_SONGFLOWER);
        registerBlockItem("fire_tulip", PlantBlocks.FIRE_TULIP);

        registerBlockItem("cave_mushrooms", PlantBlocks.CAVE_MUSHROOMS);
        registerBlockItem("muncher", PlantBlocks.MUNCHER);
        registerBlockItem("piranha_lily", PlantBlocks.PIRANHA_LILY);
        registerBlockItem("budding_beanstalk", PlantBlocks.BUDDING_BEANSTALK);
        registerBlockItem("beanstalk", PlantBlocks.BEANSTALK);
        registerBlockItem("strawberry_coral", ModBlocks.STRAWBERRY_CORAL);

        registerBlockItem("mushroom_stem", MushroomBlocks.MUSHROOM_STEM);
        registerBlockItem("beanstalk_block", ModBlocks.BEANSTALK_BLOCK);
        registerBlockItem("strawberry_coral_block", ModBlocks.STRAWBERRY_CORAL_BLOCK);
        registerBlockItem("frozen_muncher", PlantBlocks.FROZEN_MUNCHER);
        registerBlockItem("jellybeam", ModBlocks.JELLYBEAM);

        registerBlockItem("toadstool_grass", ModBlocks.TOADSTOOL_GRASS);
        registerBlockItem("grassy_toadstone", ModBlocks.GRASSY_TOADSTONE);
        registerBlockItem("toadstool_turf", ModBlocks.TOADSTOOL_TURF);

        registerBlockItem("toadstool_path", ModBlocks.TOADSTOOL_PATH);
        registerBlockItem("toadstool_soil", ModBlocks.TOADSTOOL_SOIL);
        registerBlockItem("coarse_toadstool_soil", ModBlocks.COARSE_TOADSTOOL_SOIL);
        registerBlockItem("toadstool_farmland", ModBlocks.TOADSTOOL_FARMLAND);

        registerBlockItem("gritzy_sand", ModBlocks.GRITZY_SAND);
        registerBlockItem("quicksand", ModBlocks.QUICKSAND);
        registerBlockItem("gritzy_sandstone", ModBlocks.GRITZY_SANDSTONE);
        registerBlockItem("chiseled_gritzy_sandstone", ModBlocks.CHISELED_GRITZY_SANDSTONE);
        registerBlockItem("cut_gritzy_sandstone", ModBlocks.CUT_GRITZY_SANDSTONE);
        registerBlockItem("smooth_gritzy_sandstone", ModBlocks.SMOOTH_GRITZY_SANDSTONE);
        registerBlockItem("gritzy_sandstone_slab", VariantBlocks.GRITZY_SANDSTONE_SLAB);
        registerBlockItem("gritzy_sandstone_stairs", VariantBlocks.GRITZY_SANDSTONE_STAIRS);

        registerBlockItem("topped_vanillate", ModBlocks.TOPPED_VANILLATE);
        registerBlockItem("coal_topped_vanillate", ModBlocks.COAL_TOPPED_VANILLATE);
        registerBlockItem("iron_topped_vanillate", ModBlocks.IRON_TOPPED_VANILLATE);
        registerBlockItem("gold_topped_vanillate", ModBlocks.GOLD_TOPPED_VANILLATE);
        registerBlockItem("frosty_topped_vanillate", ModBlocks.FROSTY_TOPPED_VANILLATE);
        registerBlockItem("frosty_topped_vanillate_ore", ModBlocks.FROSTY_TOPPED_VANILLATE_ORE);
        registerBlockItem("bronze_ore", ModBlocks.BRONZE_ORE);
        registerBlockItem("amethyst_ore", ModBlocks.AMETHYST_ORE);

        registerBlockItem("happy_cloud", ModBlocks.HAPPY_CLOUD);
        registerBlockItem("cloud_block", ModBlocks.CLOUD_BLOCK);
        registerBlockItem("jump_block", ModBlocks.JUMP_BLOCK);
        registerBlockItem("cloud_slab", VariantBlocks.CLOUD_SLAB);

        registerBlockItem("amanita_log", ModBlocks.AMANITA_LOG);
        registerBlockItem("stripped_amanita_log", ModBlocks.STRIPPED_AMANITA_LOG);
        registerBlockItem("amanita_wood", ModBlocks.AMANITA_WOOD);
        registerBlockItem("stripped_amanita_wood", ModBlocks.STRIPPED_AMANITA_WOOD);

        registerBlockItem("amanita_leaves", ModBlocks.AMANITA_LEAVES);
        registerBlockItem("fruiting_amanita_leaves", ModBlocks.FRUITING_AMANITA_LEAVES);

        registerBlockItem("amanita_carpet", PlantBlocks.AMANITA_CARPET);
        registerBlockItem("yellow_flowerbed", PlantBlocks.YELLOW_FLOWERBED);
        registerBlockItem("white_flowerbed", PlantBlocks.WHITE_FLOWERBED);

        registerBlockItem("amanita_planks", ModBlocks.AMANITA_PLANKS);
        registerBlockItem("amanita_slab", VariantBlocks.AMANITA_SLAB);
        registerBlockItem("amanita_stairs", VariantBlocks.AMANITA_STAIRS);

        registerBlockItem("white_bronze", ColoredBlocks.WHITE_BRONZE);
        registerBlockItem("orange_bronze", ColoredBlocks.ORANGE_BRONZE);
        registerBlockItem("magenta_bronze", ColoredBlocks.MAGENTA_BRONZE);
        registerBlockItem("light_blue_bronze", ColoredBlocks.LIGHT_BLUE_BRONZE);
        registerBlockItem("yellow_bronze", ColoredBlocks.YELLOW_BRONZE);
        registerBlockItem("lime_bronze", ColoredBlocks.LIME_BRONZE);
        registerBlockItem("pink_bronze", ColoredBlocks.PINK_BRONZE);
        registerBlockItem("gray_bronze", ColoredBlocks.GRAY_BRONZE);
        registerBlockItem("light_gray_bronze", ColoredBlocks.LIGHT_GRAY_BRONZE);
        registerBlockItem("cyan_bronze", ColoredBlocks.CYAN_BRONZE);
        registerBlockItem("purple_bronze", ColoredBlocks.PURPLE_BRONZE);
        registerBlockItem("blue_bronze", ColoredBlocks.BLUE_BRONZE);
        registerBlockItem("brown_bronze", ColoredBlocks.BROWN_BRONZE);
        registerBlockItem("green_bronze", ColoredBlocks.GREEN_BRONZE);
        registerBlockItem("red_bronze", ColoredBlocks.RED_BRONZE);
        registerBlockItem("black_bronze", ColoredBlocks.BLACK_BRONZE);

        registerItem("amanita_sign", new SignItem(new FabricItemSettings().group(CreativeTabs.BLOCK_GROUP).maxCount(16), ModBlocks.AMANITA_SIGN, ModBlocks.AMANITA_WALL_SIGN));
        registerBlockItem("amanita_door", ModBlocks.AMANITA_DOOR);
        registerBlockItem("amanita_trapdoor", ModBlocks.AMANITA_TRAPDOOR);
        registerBlockItem("amanita_fence", VariantBlocks.AMANITA_FENCE);
        registerBlockItem("amanita_fence_gate", VariantBlocks.AMANITA_FENCE_GATE);
        registerBlockItem("amanita_pressure_plate", ModBlocks.AMANITA_PRESSURE_PLATE);
        registerBlockItem("amanita_button", ModBlocks.AMANITA_BUTTON);

        registerBlockItem("warp_pipe", ModBlocks.WARP_PIPE);
        registerBlockItem("warp_pipe_body", ModBlocks.WARP_PIPE_BODY);
        registerBlockItem("trampoline", ModBlocks.TRAMPOLINE);
        registerBlockItem("donut_block", ModBlocks.DONUT_BLOCK);
        registerBlockItem("boo_lantern", ModBlocks.BOO_LANTERN);
        registerBlockItem("stone_torch", ModBlocks.STONE_TORCH);
        registerBlockItem("icicle", ModBlocks.ICICLE);
        registerBlockItem("girder", ModBlocks.GIRDER);
        registerBlockItem("spike_trap", ModBlocks.SPIKE_TRAP);

        registerBlockItem("bronze_block", ModBlocks.BRONZE_BLOCK);
        registerBlockItem("raw_bronze_block", ModBlocks.RAW_BRONZE_BLOCK);
        registerBlockItem("bronze_slab", VariantBlocks.BRONZE_SLAB);
        registerBlockItem("bronze_stairs", VariantBlocks.BRONZE_STAIRS);

        registerBlockItem("toadstone", ModBlocks.TOADSTONE);
        registerBlockItem("chiseled_toadstone", ModBlocks.CHISELED_TOADSTONE);
        registerBlockItem("smooth_toadstone", ModBlocks.SMOOTH_TOADSTONE);

        registerBlockItem("toadstone_slab", VariantBlocks.TOADSTONE_SLAB);
        registerBlockItem("toadstone_stairs", VariantBlocks.TOADSTONE_STAIRS);
        registerBlockItem("toadstone_wall", VariantBlocks.TOADSTONE_WALL);

        registerBlockItem("toadstone_bricks", ModBlocks.TOADSTONE_BRICKS);
        registerBlockItem("toadstone_brick_slab", VariantBlocks.TOADSTONE_BRICK_SLAB);
        registerBlockItem("toadstone_brick_stairs", VariantBlocks.TOADSTONE_BRICK_STAIRS);
        registerBlockItem("toadstone_brick_wall", VariantBlocks.TOADSTONE_BRICK_WALL);

        registerBlockItem("gloomstone", ModBlocks.GLOOMSTONE);
        registerBlockItem("chiseled_gloomstone", ModBlocks.CHISELED_GLOOMSTONE);
        registerBlockItem("smooth_gloomstone", ModBlocks.SMOOTH_GLOOMSTONE);

        registerBlockItem("gloomstone_slab", VariantBlocks.GLOOMSTONE_SLAB);
        registerBlockItem("gloomstone_stairs", VariantBlocks.GLOOMSTONE_STAIRS);
        registerBlockItem("gloomstone_wall", VariantBlocks.GLOOMSTONE_WALL);

        registerBlockItem("gloomstone_bricks", ModBlocks.GLOOMSTONE_BRICKS);
        registerBlockItem("gloomstone_brick_slab", VariantBlocks.GLOOMSTONE_BRICK_SLAB);
        registerBlockItem("gloomstone_brick_stairs", VariantBlocks.GLOOMSTONE_BRICK_STAIRS);
        registerBlockItem("gloomstone_brick_wall", VariantBlocks.GLOOMSTONE_BRICK_WALL);

        registerBlockItem("hardstone", ModBlocks.HARDSTONE);
        registerBlockItem("polished_hardstone", ModBlocks.POLISHED_HARDSTONE);
        registerBlockItem("chiseled_hardstone", ModBlocks.CHISELED_HARDSTONE);
        registerBlockItem("hardstone_pillar", ModBlocks.HARDSTONE_PILLAR);
        registerBlockItem("polished_hardstone_slab", VariantBlocks.POLISHED_HARDSTONE_SLAB);
        registerBlockItem("polished_hardstone_stairs", VariantBlocks.POLISHED_HARDSTONE_STAIRS);
        registerBlockItem("polished_hardstone_wall", VariantBlocks.POLISHED_HARDSTONE_WALL);

        registerBlockItem("hardstone_bricks", ModBlocks.HARDSTONE_BRICKS);
        registerBlockItem("cracked_hardstone_bricks", ModBlocks.CRACKED_HARDSTONE_BRICKS);
        registerBlockItem("hardstone_brick_slab", VariantBlocks.HARDSTONE_BRICK_SLAB);
        registerBlockItem("hardstone_brick_stairs", VariantBlocks.HARDSTONE_BRICK_STAIRS);
        registerBlockItem("hardstone_brick_wall", VariantBlocks.HARDSTONE_BRICK_WALL);

        registerBlockItem("seastone", ModBlocks.SEASTONE);
        registerBlockItem("seastone_bricks", ModBlocks.SEASTONE_BRICKS);
        registerBlockItem("seastone_brick_slab", VariantBlocks.SEASTONE_BRICK_SLAB);
        registerBlockItem("seastone_brick_stairs", VariantBlocks.SEASTONE_BRICK_STAIRS);
        registerBlockItem("seastone_brick_wall", VariantBlocks.SEASTONE_BRICK_WALL);

        registerBlockItem("golden_bricks", ModBlocks.GOLDEN_BRICKS);
        registerBlockItem("golden_brick_slab", VariantBlocks.GOLDEN_BRICK_SLAB);
        registerBlockItem("golden_brick_stairs", VariantBlocks.GOLDEN_BRICK_STAIRS);
        registerBlockItem("golden_brick_wall", VariantBlocks.GOLDEN_BRICK_WALL);

        registerBlockItem("crystal_bricks", ModBlocks.CRYSTAL_BRICKS);
        registerBlockItem("crystal_brick_slab", VariantBlocks.CRYSTAL_BRICK_SLAB);
        registerBlockItem("crystal_brick_stairs", VariantBlocks.CRYSTAL_BRICK_STAIRS);
        registerBlockItem("crystal_brick_wall", VariantBlocks.CRYSTAL_BRICK_WALL);

        registerBlockItem("vanillate", ModBlocks.VANILLATE);
        registerBlockItem("vanillate_crumble", ModBlocks.VANILLATE_CRUMBLE);
        registerBlockItem("vanillate_bricks", ModBlocks.VANILLATE_BRICKS);
        registerBlockItem("vanillate_tiles", ModBlocks.VANILLATE_TILES);
        registerBlockItem("vanillate_slab", VariantBlocks.VANILLATE_SLAB);
        registerBlockItem("vanillate_stairs", VariantBlocks.VANILLATE_STAIRS);
        registerBlockItem("vanillate_brick_slab", VariantBlocks.VANILLATE_BRICK_SLAB);
        registerBlockItem("vanillate_brick_stairs", VariantBlocks.VANILLATE_BRICK_STAIRS);

        registerBlockItem("frosty_vanillate", ModBlocks.FROSTY_VANILLATE);
        registerBlockItem("frosty_vanillate_crumble", ModBlocks.FROSTY_VANILLATE_CRUMBLE);
        registerBlockItem("frosty_vanillate_bricks", ModBlocks.FROSTY_VANILLATE_BRICKS);
        registerBlockItem("frosty_vanillate_tiles", ModBlocks.FROSTY_VANILLATE_TILES);
        registerBlockItem("frosty_vanillate_slab", VariantBlocks.FROSTY_VANILLATE_SLAB);
        registerBlockItem("frosty_vanillate_stairs", VariantBlocks.FROSTY_VANILLATE_STAIRS);
        registerBlockItem("frosty_vanillate_brick_slab", VariantBlocks.FROSTY_VANILLATE_BRICK_SLAB);
        registerBlockItem("frosty_vanillate_brick_stairs", VariantBlocks.FROSTY_VANILLATE_BRICK_STAIRS);

        registerBlockItem("royalite", ModBlocks.ROYALITE);
        registerBlockItem("smooth_royalite", ModBlocks.SMOOTH_ROYALITE);
        registerBlockItem("royalite_bricks", ModBlocks.ROYALITE_BRICKS);
        registerBlockItem("royalite_brick_slab", VariantBlocks.ROYALITE_BRICK_SLAB);
        registerBlockItem("royalite_brick_stairs", VariantBlocks.ROYALITE_BRICK_STAIRS);
        registerBlockItem("royalite_brick_wall", VariantBlocks.ROYALITE_BRICK_WALL);
    }
}
