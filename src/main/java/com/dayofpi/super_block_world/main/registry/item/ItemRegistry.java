package com.dayofpi.super_block_world.main.registry.item;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.item.*;
import com.dayofpi.super_block_world.main.common.item.armor.JumpBootsItem;
import com.dayofpi.super_block_world.main.common.item.armor.ShellItem;
import com.dayofpi.super_block_world.main.common.item.projectile.*;
import com.dayofpi.super_block_world.main.util.armor_material.*;
import com.dayofpi.super_block_world.main.registry.EntityRegistry;
import com.dayofpi.super_block_world.main.registry.FluidRegistry;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.block.ColoredBlocks;
import com.dayofpi.super_block_world.main.registry.block.MushroomBlocks;
import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.main.registry.block.VariantBlocks;
import com.dayofpi.super_block_world.main.util.mixin_aid.ModBoatType;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;


public class ItemRegistry {
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
    public static final Item SUPER_MUSHROOM = new SuperMushroomItem(new FabricItemSettings().food(FoodComponents.SUPER_MUSHROOM).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item GOLDEN_MUSHROOM = new GoldenMushroomItem(new FabricItemSettings().food(FoodComponents.GOLDEN_MUSHROOM).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item ONE_UP = new OneUpItem(new FabricItemSettings().food(FoodComponents.ONE_UP).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item POISON_MUSHROOM = new PoisonMushroomItem(new FabricItemSettings().food(FoodComponents.POISON_MUSHROOM).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item FIRE_FLOWER = new FireFlowerItem(new FabricItemSettings().maxDamage(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item ICE_FLOWER = new IceFlowerItem(new FabricItemSettings().maxDamage(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item SUPER_STAR = new SuperStarItem(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).rarity(Rarity.RARE));
    public static final Item COIN = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item STAR_COIN = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item RAW_BRONZE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BRONZE_INGOT = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item CERISE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item TROOP_HIDE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item CLOUD_PUFF = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item SPIKE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BUZZY_SHELL_PIECE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item GREEN_SHELL = new ShellItem(EntityRegistry.BUZZY_SHELL, GREEN_SHELL_ARMOR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item RED_SHELL = new ShellItem(EntityRegistry.BUZZY_SHELL, RED_SHELL_ARMOR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BUZZY_SHELL = new ShellItem(EntityRegistry.BUZZY_SHELL, BUZZY_ARMOR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item JUMP_BOOTS = new JumpBootsItem(JUMP_ARMOR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BOTTLED_GHOST = new Item(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).group(CreativeTabs.ITEM_GROUP));
    public static final Item GREEN_MUSHROOM_ON_A_STICK = new OnAStickItem<>(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxDamage(25), EntityRegistry.BUZZY_BEETLE, 2);
    public static final Item AMANITA_BOAT = new BoatItem(ModBoatType.AMANITA, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxCount(1));
    public static final Item DARK_AMANITA_BOAT = new BoatItem(ModBoatType.DARK_AMANITA, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxCount(1));
    public static final Item POISON_BUCKET = new PoisonBucketItem(FluidRegistry.STILL_POISON, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1).group(CreativeTabs.ITEM_GROUP));
    public static final Item MOO_MOO_SPAWN_EGG = new SpawnEggItem(EntityRegistry.MOO_MOO, 15985102, 4605249, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item GOOMBA_SPAWN_EGG = new SpawnEggItem(EntityRegistry.GOOMBA, 12544546, 14922613, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item PARAGOOMBA_SPAWN_EGG = new SpawnEggItem(EntityRegistry.PARAGOOMBA, 12544546, 16767400, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item KOOPA_TROOPA_SPAWN_EGG = new SpawnEggItem(EntityRegistry.KOOPA_TROOPA, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item PARATROOPA_SPAWN_EGG = new SpawnEggItem(EntityRegistry.PARATROOPA, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item HAMMER_BRO_SPAWN_EGG = new SpawnEggItem(EntityRegistry.HAMMER_BRO, 16570144, 9341835, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item FIRE_BRO_SPAWN_EGG = new SpawnEggItem(EntityRegistry.FIRE_BRO, 16570144, 15552066, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BOB_OMB_SPAWN_EGG = new SpawnEggItem(EntityRegistry.BOB_OMB, 658473, 5688063, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BOO_SPAWN_EGG = new SpawnEggItem(EntityRegistry.BOO, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BUZZY_BEETLE_SPAWN_EGG = new SpawnEggItem(EntityRegistry.BUZZY_BEETLE, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item SPIKE_TOP_SPAWN_EGG = new SpawnEggItem(EntityRegistry.SPIKE_TOP, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item NIPPER_PLANT_SPAWN_EGG = new SpawnEggItem(EntityRegistry.NIPPER_PLANT, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item STINGBY_SPAWN_EGG = new SpawnEggItem(EntityRegistry.STINGBY, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item ROTTEN_MUSHROOM_SPAWN_EGG = new SpawnEggItem(EntityRegistry.ROTTEN_MUSHROOM, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item THWOMP_SPAWN_EGG = new SpawnEggItem(EntityRegistry.THWOMP, 10530706, 12572889, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item FAKE_BLOCK_SPAWN_EGG = new SpawnEggItem(EntityRegistry.FAKE_BLOCK, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));

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
        registerItem("star_coin", STAR_COIN);
        registerItem("raw_bronze", RAW_BRONZE);
        registerItem("bronze_ingot", BRONZE_INGOT);
        registerItem("cerise", CERISE);
        registerItem("troop_hide", TROOP_HIDE);
        registerItem("cloud_puff", CLOUD_PUFF);
        registerItem("spike", SPIKE);
        registerItem("buzzy_shell_piece", BUZZY_SHELL_PIECE);
        registerItem("green_mushroom_on_a_stick", GREEN_MUSHROOM_ON_A_STICK);
        registerItem("amanita_boat", AMANITA_BOAT);
        registerItem("dark_amanita_boat", DARK_AMANITA_BOAT);
        registerItem("bottle_o_ghost", BOTTLED_GHOST);
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
        registerBlockItem("warp_frame", BlockRegistry.WARP_FRAME);

        registerBlockItem("question_block", BlockRegistry.QUESTION_BLOCK);
        registerBlockItem("coin_block", BlockRegistry.COIN_BLOCK);
        registerBlockItem("fake_block", BlockRegistry.FAKE_BLOCK);
        registerBlockItem("empty_block", BlockRegistry.EMPTY_BLOCK);
        registerBlockItem("hidden_block", BlockRegistry.HIDDEN_BLOCK);
        registerBlockItem("glow_block", BlockRegistry.GLOW_BLOCK);

        registerBlockItem("brown_mushroom_cap", MushroomBlocks.BROWN_MUSHROOM_CAP);
        registerBlockItem("red_mushroom_cap", MushroomBlocks.RED_MUSHROOM_CAP);
        registerBlockItem("green_mushroom_cap", MushroomBlocks.GREEN_MUSHROOM_CAP);
        registerBlockItem("pink_mushroom_cap", MushroomBlocks.PINK_MUSHROOM_CAP);
        registerBlockItem("yellow_mushroom_cap", MushroomBlocks.YELLOW_MUSHROOM_CAP);
        registerBlockItem("orange_mushroom_cap", MushroomBlocks.ORANGE_MUSHROOM_CAP);
        registerBlockItem("purple_mushroom_cap", MushroomBlocks.PURPLE_MUSHROOM_CAP);

        registerBlockItem("green_mushroom", MushroomBlocks.GREEN_MUSHROOM);
        registerBlockItem("pink_mushroom", MushroomBlocks.PINK_MUSHROOM);
        registerBlockItem("yellow_mushroom", MushroomBlocks.YELLOW_MUSHROOM);
        registerBlockItem("orange_mushroom", MushroomBlocks.ORANGE_MUSHROOM);
        registerBlockItem("purple_mushroom", MushroomBlocks.PURPLE_MUSHROOM);

        registerBlockItem("amanita_sapling", PlantBlocks.AMANITA_SAPLING);
        registerBlockItem("dark_amanita_sapling", PlantBlocks.DARK_AMANITA_SAPLING);
        registerBlockItem("bush", PlantBlocks.BUSH);
        registerBlockItem("horsetail", PlantBlocks.HORSETAIL);
        registerBlockItem("vegetable", PlantBlocks.VEGETABLE);

        registerBlockItem("pawflower", PlantBlocks.PAWFLOWER);
        registerBlockItem("blue_songflower", PlantBlocks.BLUE_SONGFLOWER);
        registerBlockItem("pink_songflower", PlantBlocks.PINK_SONGFLOWER);
        registerBlockItem("yellow_songflower", PlantBlocks.YELLOW_SONGFLOWER);
        registerBlockItem("fire_tulip", PlantBlocks.FIRE_TULIP);

        registerBlockItem("yellow_flowerbed", PlantBlocks.YELLOW_FLOWERBED);
        registerBlockItem("white_flowerbed", PlantBlocks.WHITE_FLOWERBED);

        registerBlockItem("cave_mushrooms", PlantBlocks.CAVE_MUSHROOMS);
        registerBlockItem("muncher", PlantBlocks.MUNCHER);
        registerBlockItem("pit_plant", PlantBlocks.PIT_PLANT);
        registerItem("piranha_lily", new PiranhaLilyItem(PlantBlocks.PIRANHA_LILY, new FabricItemSettings().group(CreativeTabs.BLOCK_GROUP)));
        registerBlockItem("budding_beanstalk", PlantBlocks.BUDDING_BEANSTALK);
        registerBlockItem("beanstalk", PlantBlocks.BEANSTALK);
        registerBlockItem("strawberry_coral", BlockRegistry.STRAWBERRY_CORAL);

        registerBlockItem("mushroom_stem", MushroomBlocks.MUSHROOM_STEM);
        registerBlockItem("beanstalk_block", BlockRegistry.BEANSTALK_BLOCK);
        registerBlockItem("strawberry_coral_block", BlockRegistry.STRAWBERRY_CORAL_BLOCK);
        registerBlockItem("frozen_muncher", PlantBlocks.FROZEN_MUNCHER);
        registerBlockItem("jellybeam", BlockRegistry.JELLYBEAM);

        registerBlockItem("toadstool_grass", BlockRegistry.TOADSTOOL_GRASS);
        registerBlockItem("grassy_toadstone", BlockRegistry.GRASSY_TOADSTONE);
        registerBlockItem("toadstool_turf", BlockRegistry.TOADSTOOL_TURF);

        registerBlockItem("toadstool_path", BlockRegistry.TOADSTOOL_PATH);
        registerBlockItem("toadstool_soil", BlockRegistry.TOADSTOOL_SOIL);
        registerBlockItem("coarse_toadstool_soil", BlockRegistry.COARSE_TOADSTOOL_SOIL);
        registerBlockItem("toadstool_farmland", BlockRegistry.TOADSTOOL_FARMLAND);

        registerBlockItem("gritzy_sand", BlockRegistry.GRITZY_SAND);
        registerBlockItem("quicksand", BlockRegistry.QUICKSAND);
        registerBlockItem("gritzy_sandstone", BlockRegistry.GRITZY_SANDSTONE);
        registerBlockItem("chiseled_gritzy_sandstone", BlockRegistry.CHISELED_GRITZY_SANDSTONE);
        registerBlockItem("cut_gritzy_sandstone", BlockRegistry.CUT_GRITZY_SANDSTONE);
        registerBlockItem("smooth_gritzy_sandstone", BlockRegistry.SMOOTH_GRITZY_SANDSTONE);
        registerBlockItem("gritzy_sandstone_slab", VariantBlocks.GRITZY_SANDSTONE_SLAB);
        registerBlockItem("gritzy_sandstone_stairs", VariantBlocks.GRITZY_SANDSTONE_STAIRS);

        registerBlockItem("vanillate_crumble", BlockRegistry.VANILLATE_CRUMBLE);
        registerBlockItem("bronze_ore", BlockRegistry.BRONZE_ORE);
        registerBlockItem("amethyst_ore", BlockRegistry.AMETHYST_ORE);
        registerBlockItem("cerise_ore", BlockRegistry.CERISE_ORE);registerBlockItem("topped_vanillate", BlockRegistry.TOPPED_VANILLATE);
        registerBlockItem("coal_topped_vanillate", BlockRegistry.COAL_TOPPED_VANILLATE);
        registerBlockItem("iron_topped_vanillate", BlockRegistry.IRON_TOPPED_VANILLATE);
        registerBlockItem("gold_topped_vanillate", BlockRegistry.GOLD_TOPPED_VANILLATE);
        registerBlockItem("frosty_vanillate_crumble", BlockRegistry.FROSTY_VANILLATE_CRUMBLE);
        registerBlockItem("frosty_topped_vanillate", BlockRegistry.FROSTY_TOPPED_VANILLATE);
        registerBlockItem("frosty_topped_vanillate_ore", BlockRegistry.FROSTY_TOPPED_VANILLATE_ORE);

        registerBlockItem("happy_cloud", BlockRegistry.HAPPY_CLOUD);
        registerBlockItem("cloud_block", BlockRegistry.CLOUD_BLOCK);
        registerBlockItem("cloud_slab", VariantBlocks.CLOUD_SLAB);
        registerBlockItem("cloud_stairs", VariantBlocks.CLOUD_STAIRS);

        registerBlockItem("shoregrass", BlockRegistry.SHOREGRASS);
        registerBlockItem("shoresand", BlockRegistry.SHORESAND);

        registerBlockItem("amanita_leaves", BlockRegistry.AMANITA_LEAVES);
        registerBlockItem("fruiting_amanita_leaves", BlockRegistry.FRUITING_AMANITA_LEAVES);

        registerBlockItem("dark_amanita_leaves", BlockRegistry.DARK_AMANITA_LEAVES);
        registerBlockItem("fruiting_dark_amanita_leaves", BlockRegistry.FRUITING_DARK_AMANITA_LEAVES);

        registerBlockItem("amanita_carpet", PlantBlocks.AMANITA_CARPET);

        registerBlockItem("amanita_log", BlockRegistry.AMANITA_LOG);
        registerBlockItem("stripped_amanita_log", BlockRegistry.STRIPPED_AMANITA_LOG);
        registerBlockItem("amanita_wood", BlockRegistry.AMANITA_WOOD);
        registerBlockItem("stripped_amanita_wood", BlockRegistry.STRIPPED_AMANITA_WOOD);

        registerBlockItem("dark_amanita_log", BlockRegistry.DARK_AMANITA_LOG);
        registerBlockItem("stripped_dark_amanita_log", BlockRegistry.STRIPPED_DARK_AMANITA_LOG);
        registerBlockItem("dark_amanita_wood", BlockRegistry.DARK_AMANITA_WOOD);
        registerBlockItem("stripped_dark_amanita_wood", BlockRegistry.STRIPPED_DARK_AMANITA_WOOD);

        registerBlockItem("amanita_planks", BlockRegistry.AMANITA_PLANKS);
        registerBlockItem("amanita_slab", VariantBlocks.AMANITA_SLAB);
        registerBlockItem("amanita_stairs", VariantBlocks.AMANITA_STAIRS);

        registerBlockItem("dark_amanita_planks", BlockRegistry.DARK_AMANITA_PLANKS);
        registerBlockItem("dark_amanita_slab", VariantBlocks.DARK_AMANITA_SLAB);
        registerBlockItem("dark_amanita_stairs", VariantBlocks.DARK_AMANITA_STAIRS);

        registerItem("amanita_sign", new SignItem(new FabricItemSettings().group(CreativeTabs.BLOCK_GROUP).maxCount(16), BlockRegistry.AMANITA_SIGN, BlockRegistry.AMANITA_WALL_SIGN));
        registerBlockItem("amanita_door", BlockRegistry.AMANITA_DOOR);
        registerBlockItem("amanita_trapdoor", BlockRegistry.AMANITA_TRAPDOOR);
        registerBlockItem("amanita_fence", VariantBlocks.AMANITA_FENCE);
        registerBlockItem("amanita_fence_gate", VariantBlocks.AMANITA_FENCE_GATE);
        registerBlockItem("amanita_pressure_plate", BlockRegistry.AMANITA_PRESSURE_PLATE);
        registerBlockItem("amanita_button", BlockRegistry.AMANITA_BUTTON);

        registerItem("dark_amanita_sign", new SignItem(new FabricItemSettings().group(CreativeTabs.BLOCK_GROUP).maxCount(16), BlockRegistry.DARK_AMANITA_SIGN, BlockRegistry.DARK_AMANITA_WALL_SIGN));
        registerBlockItem("dark_amanita_door", BlockRegistry.DARK_AMANITA_DOOR);
        registerBlockItem("dark_amanita_trapdoor", BlockRegistry.DARK_AMANITA_TRAPDOOR);
        registerBlockItem("dark_amanita_fence", VariantBlocks.DARK_AMANITA_FENCE);
        registerBlockItem("dark_amanita_fence_gate", VariantBlocks.DARK_AMANITA_FENCE_GATE);
        registerBlockItem("dark_amanita_pressure_plate", BlockRegistry.DARK_AMANITA_PRESSURE_PLATE);
        registerBlockItem("dark_amanita_button", BlockRegistry.DARK_AMANITA_BUTTON);

        registerBlockItem("bronze_block", BlockRegistry.BRONZE_BLOCK);
        registerBlockItem("raw_bronze_block", BlockRegistry.RAW_BRONZE_BLOCK);
        registerBlockItem("bronze_slab", VariantBlocks.BRONZE_SLAB);
        registerBlockItem("bronze_stairs", VariantBlocks.BRONZE_STAIRS);

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

        registerBlockItem("vanillate", BlockRegistry.VANILLATE);
        registerBlockItem("vanillate_bricks", BlockRegistry.VANILLATE_BRICKS);
        registerBlockItem("vanillate_tiles", BlockRegistry.VANILLATE_TILES);
        registerBlockItem("vanillate_slab", VariantBlocks.VANILLATE_SLAB);
        registerBlockItem("vanillate_stairs", VariantBlocks.VANILLATE_STAIRS);
        registerBlockItem("vanillate_brick_slab", VariantBlocks.VANILLATE_BRICK_SLAB);
        registerBlockItem("vanillate_brick_stairs", VariantBlocks.VANILLATE_BRICK_STAIRS);

        registerBlockItem("frosty_vanillate", BlockRegistry.FROSTY_VANILLATE);
        registerBlockItem("frosty_vanillate_bricks", BlockRegistry.FROSTY_VANILLATE_BRICKS);
        registerBlockItem("frosty_vanillate_tiles", BlockRegistry.FROSTY_VANILLATE_TILES);
        registerBlockItem("frosty_vanillate_slab", VariantBlocks.FROSTY_VANILLATE_SLAB);
        registerBlockItem("frosty_vanillate_stairs", VariantBlocks.FROSTY_VANILLATE_STAIRS);
        registerBlockItem("frosty_vanillate_brick_slab", VariantBlocks.FROSTY_VANILLATE_BRICK_SLAB);
        registerBlockItem("frosty_vanillate_brick_stairs", VariantBlocks.FROSTY_VANILLATE_BRICK_STAIRS);

        registerBlockItem("toadstone", BlockRegistry.TOADSTONE);
        registerBlockItem("smooth_toadstone", BlockRegistry.SMOOTH_TOADSTONE);
        registerBlockItem("toadstone_bricks", BlockRegistry.TOADSTONE_BRICKS);
        registerBlockItem("chiseled_toadstone", BlockRegistry.CHISELED_TOADSTONE);

        registerBlockItem("toadstone_slab", VariantBlocks.TOADSTONE_SLAB);
        registerBlockItem("toadstone_stairs", VariantBlocks.TOADSTONE_STAIRS);
        registerBlockItem("toadstone_wall", VariantBlocks.TOADSTONE_WALL);

        registerBlockItem("toadstone_brick_slab", VariantBlocks.TOADSTONE_BRICK_SLAB);
        registerBlockItem("toadstone_brick_stairs", VariantBlocks.TOADSTONE_BRICK_STAIRS);
        registerBlockItem("toadstone_brick_wall", VariantBlocks.TOADSTONE_BRICK_WALL);

        registerBlockItem("gloomstone", BlockRegistry.GLOOMSTONE);
        registerBlockItem("smooth_gloomstone", BlockRegistry.SMOOTH_GLOOMSTONE);
        registerBlockItem("gloomstone_bricks", BlockRegistry.GLOOMSTONE_BRICKS);
        registerBlockItem("chiseled_gloomstone", BlockRegistry.CHISELED_GLOOMSTONE);

        registerBlockItem("gloomstone_slab", VariantBlocks.GLOOMSTONE_SLAB);
        registerBlockItem("gloomstone_stairs", VariantBlocks.GLOOMSTONE_STAIRS);
        registerBlockItem("gloomstone_wall", VariantBlocks.GLOOMSTONE_WALL);

        registerBlockItem("gloomstone_brick_slab", VariantBlocks.GLOOMSTONE_BRICK_SLAB);
        registerBlockItem("gloomstone_brick_stairs", VariantBlocks.GLOOMSTONE_BRICK_STAIRS);
        registerBlockItem("gloomstone_brick_wall", VariantBlocks.GLOOMSTONE_BRICK_WALL);

        registerBlockItem("seastone", BlockRegistry.SEASTONE);
        registerBlockItem("seastone_bricks", BlockRegistry.SEASTONE_BRICKS);
        registerBlockItem("seastone_brick_slab", VariantBlocks.SEASTONE_BRICK_SLAB);
        registerBlockItem("seastone_brick_stairs", VariantBlocks.SEASTONE_BRICK_STAIRS);
        registerBlockItem("seastone_brick_wall", VariantBlocks.SEASTONE_BRICK_WALL);

        registerBlockItem("golden_bricks", BlockRegistry.GOLDEN_BRICKS);
        registerBlockItem("golden_brick_slab", VariantBlocks.GOLDEN_BRICK_SLAB);
        registerBlockItem("golden_brick_stairs", VariantBlocks.GOLDEN_BRICK_STAIRS);
        registerBlockItem("golden_brick_wall", VariantBlocks.GOLDEN_BRICK_WALL);

        registerBlockItem("crystal_bricks", BlockRegistry.CRYSTAL_BRICKS);
        registerBlockItem("crystal_brick_slab", VariantBlocks.CRYSTAL_BRICK_SLAB);
        registerBlockItem("crystal_brick_stairs", VariantBlocks.CRYSTAL_BRICK_STAIRS);
        registerBlockItem("crystal_brick_wall", VariantBlocks.CRYSTAL_BRICK_WALL);

        registerBlockItem("hardstone", BlockRegistry.HARDSTONE);
        registerBlockItem("polished_hardstone", BlockRegistry.POLISHED_HARDSTONE);
        registerBlockItem("chiseled_hardstone", BlockRegistry.CHISELED_HARDSTONE);
        registerBlockItem("hardstone_pillar", BlockRegistry.HARDSTONE_PILLAR);

        registerBlockItem("hardstone_bricks", BlockRegistry.HARDSTONE_BRICKS);
        registerBlockItem("chiseled_hardstone_bricks", BlockRegistry.CHISELED_HARDSTONE_BRICKS);
        registerBlockItem("cracked_hardstone_bricks", BlockRegistry.CRACKED_HARDSTONE_BRICKS);

        registerBlockItem("polished_hardstone_slab", VariantBlocks.POLISHED_HARDSTONE_SLAB);
        registerBlockItem("polished_hardstone_stairs", VariantBlocks.POLISHED_HARDSTONE_STAIRS);
        registerBlockItem("polished_hardstone_wall", VariantBlocks.POLISHED_HARDSTONE_WALL);

        registerBlockItem("hardstone_brick_slab", VariantBlocks.HARDSTONE_BRICK_SLAB);
        registerBlockItem("hardstone_brick_stairs", VariantBlocks.HARDSTONE_BRICK_STAIRS);
        registerBlockItem("hardstone_brick_wall", VariantBlocks.HARDSTONE_BRICK_WALL);

        registerBlockItem("royalite", BlockRegistry.ROYALITE);
        registerBlockItem("smooth_royalite", BlockRegistry.SMOOTH_ROYALITE);
        registerBlockItem("royalite_bricks", BlockRegistry.ROYALITE_BRICKS);
        registerBlockItem("cracked_royalite_bricks", BlockRegistry.CRACKED_ROYALITE_BRICKS);
        registerBlockItem("chiseled_royalite_bricks", BlockRegistry.CHISELED_ROYALITE_BRICKS);

        registerBlockItem("royalite_brick_slab", VariantBlocks.ROYALITE_BRICK_SLAB);
        registerBlockItem("royalite_brick_stairs", VariantBlocks.ROYALITE_BRICK_STAIRS);
        registerBlockItem("royalite_brick_wall", VariantBlocks.ROYALITE_BRICK_WALL);

        registerBlockItem("cerise_block", BlockRegistry.CERISE_BLOCK);
        registerBlockItem("cerise_bricks", BlockRegistry.CERISE_BRICKS);
        registerBlockItem("cerise_tiles", BlockRegistry.CERISE_TILES);

        registerBlockItem("cerise_brick_slab", VariantBlocks.CERISE_BRICK_SLAB);
        registerBlockItem("cerise_brick_stairs", VariantBlocks.CERISE_BRICK_STAIRS);
        registerBlockItem("cerise_tile_slab", VariantBlocks.CERISE_TILE_SLAB);
        registerBlockItem("cerise_tile_stairs", VariantBlocks.CERISE_TILE_STAIRS);

        registerBlockItem("jump_block", BlockRegistry.JUMP_BLOCK);
        registerBlockItem("redstone_trampoline", BlockRegistry.REDSTONE_TRAMPOLINE);
        registerBlockItem("trampoline", BlockRegistry.TRAMPOLINE);
        registerBlockItem("donut_block", BlockRegistry.DONUT_BLOCK);
        registerBlockItem("boo_lantern", BlockRegistry.BOO_LANTERN);
        registerBlockItem("stone_torch", BlockRegistry.STONE_TORCH);
        registerBlockItem("icicle", BlockRegistry.ICICLE);
        registerBlockItem("girder", BlockRegistry.GIRDER);
        registerBlockItem("spike_trap", BlockRegistry.SPIKE_TRAP);
        registerBlockItem("bronze_pipe", BlockRegistry.BRONZE_PIPE);
        registerBlockItem("bronze_pipe_body", BlockRegistry.BRONZE_PIPE_BODY);
        registerBlockItem("warp_pipe", BlockRegistry.WARP_PIPE);
        registerBlockItem("warp_pipe_body", BlockRegistry.WARP_PIPE_BODY);
    }
}
