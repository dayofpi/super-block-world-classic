package com.dayofpi.super_block_world.main.registry.main;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.item.*;
import com.dayofpi.super_block_world.main.common.item.armor.JumpBootsItem;
import com.dayofpi.super_block_world.main.common.item.armor.ShellItem;
import com.dayofpi.super_block_world.main.common.item.projectile.*;
import com.dayofpi.super_block_world.main.registry.other.FluidInit;
import com.dayofpi.super_block_world.main.registry.block.*;
import com.dayofpi.super_block_world.main.util.item.CreativeTabs;
import com.dayofpi.super_block_world.main.registry.other.FoodComponents;
import com.dayofpi.super_block_world.main.util.item.Materials;
import com.dayofpi.super_block_world.main.util.mixin_aid.ModBoatType;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;


public class ItemInit {
    public static final Item POWER_STAR = new PowerStarItem(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Item POWER_DUST = new Item(new FabricItemSettings());
    public static final Item YOSHI_FRUIT = new Item(new FabricItemSettings().food(FoodComponents.YOSHI_FRUIT).group(CreativeTabs.ITEM_GROUP));
    public static final Item YOSHI_COOKIE = new Item(new FabricItemSettings().food(FoodComponents.YOSHI_COOKIE).group(CreativeTabs.ITEM_GROUP));
    public static final Item TURNIP = new TurnipItem(new FabricItemSettings().maxCount(16).group(CreativeTabs.ITEM_GROUP));
    public static final Item HAMMER = new HammerItem(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxDamage(50));
    public static final Item SUPER_PICKAX = new SuperPickaxItem(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BOMB = new BombItem(new FabricItemSettings().maxCount(16).group(CreativeTabs.ITEM_GROUP));
    public static final Item FUZZY_MAGNET = new FuzzyMagnetItem(new FabricItemSettings().maxCount(1).group(CreativeTabs.ITEM_GROUP));
    public static final Item SUPER_MUSHROOM = new SuperMushroomItem(new FabricItemSettings().food(FoodComponents.SUPER_MUSHROOM).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item GOLDEN_MUSHROOM = new GoldenMushroomItem(new FabricItemSettings().food(FoodComponents.GOLDEN_MUSHROOM).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item ONE_UP = new OneUpItem(new FabricItemSettings().food(FoodComponents.ONE_UP).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item POISON_MUSHROOM = new PoisonMushroomItem(new FabricItemSettings().food(FoodComponents.POISON_MUSHROOM).maxCount(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item FIRE_FLOWER = new FireFlowerItem(new FabricItemSettings().maxDamage(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item ICE_FLOWER = new IceFlowerItem(new FabricItemSettings().maxDamage(16).group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item SUPER_STAR = new SuperStarItem(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).rarity(Rarity.RARE));
    public static final Item COIN = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item STAR_COIN = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item GREEN_STAR_BIT = new StarBitItem(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item PURPLE_STAR_BIT = new StarBitItem(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BLUE_STAR_BIT = new StarBitItem(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item RAW_BRONZE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BRONZE_INGOT = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item CERISE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item TROOP_HIDE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item CLOUD_PUFF = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item FUZZBALL = new BlockItem(PlantBlocks.FUZZBALL, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item SPIKE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BUZZY_SHELL_PIECE = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item GREEN_SHELL = new ShellItem(EntityInit.BUZZY_SHELL, Materials.GREEN_SHELL, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item RED_SHELL = new ShellItem(EntityInit.BUZZY_SHELL, Materials.RED_SHELL, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BUZZY_SHELL = new ShellItem(EntityInit.BUZZY_SHELL, Materials.BUZZY_ARMOR, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item JUMP_BOOTS = new JumpBootsItem(Materials.JUMP_ARMOR, EquipmentSlot.FEET, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BOTTLED_GHOST = new Item(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).group(CreativeTabs.ITEM_GROUP));
    public static final Item GREEN_MUSHROOM_ON_A_STICK = new OnAStickItem<>(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxDamage(25), EntityInit.BUZZY_BEETLE, 2);
    public static final Item AMANITA_BOAT = new BoatItem(ModBoatType.AMANITA, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxCount(1));
    public static final Item DARK_AMANITA_BOAT = new BoatItem(ModBoatType.DARK_AMANITA, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxCount(1));
    public static final Item POISON_BUCKET = new PoisonBucketItem(FluidInit.STILL_POISON, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1).group(CreativeTabs.ITEM_GROUP));
    public static final Item MUSIC_DISC_MY_SONG = new MusicDiscItem(0, SoundInit.MUSIC_DISC_MY_SONG, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).rarity(Rarity.RARE)){};

    private static void registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, id), item);
    }

    private static SpawnEggItem createSpawnEggItem(EntityType<? extends MobEntity> entityType) {
        return new SpawnEggItem(entityType, 16777215, 16777215, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    }

    public static void register() {
        registerBuildingBlocks();
        registerDecorations();
        registerItem("power_star", POWER_STAR);
        registerItem("power_dust", POWER_DUST);
        registerItem("yoshi_fruit", YOSHI_FRUIT);
        registerItem("yoshi_cookie", YOSHI_COOKIE);
        registerItem("turnip", TURNIP);
        registerItem("hammer", HAMMER);
        registerItem("super_pickax", SUPER_PICKAX);
        registerItem("bomb", BOMB);
        registerItem("fuzzy_magnet", FUZZY_MAGNET);
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
        registerItem("green_star_bit", GREEN_STAR_BIT);
        registerItem("purple_star_bit", PURPLE_STAR_BIT);
        registerItem("blue_star_bit", BLUE_STAR_BIT);
        registerItem("raw_bronze", RAW_BRONZE);
        registerItem("bronze_ingot", BRONZE_INGOT);
        registerItem("cerise", CERISE);
        registerItem("troop_hide", TROOP_HIDE);
        registerItem("cloud_puff", CLOUD_PUFF);
        registerItem("fuzzball", FUZZBALL);
        registerItem("spike", SPIKE);
        registerItem("buzzy_shell_piece", BUZZY_SHELL_PIECE);
        registerItem("green_mushroom_on_a_stick", GREEN_MUSHROOM_ON_A_STICK);
        registerItem("amanita_boat", AMANITA_BOAT);
        registerItem("dark_amanita_boat", DARK_AMANITA_BOAT);
        registerItem("bottled_ghost", BOTTLED_GHOST);
        registerItem("poison_bucket", POISON_BUCKET);
        registerItem("music_disc_my_song", MUSIC_DISC_MY_SONG);
        registerItem("spawn_eggs/moo_moo", createSpawnEggItem(EntityInit.MOO_MOO));
        registerItem("spawn_eggs/toad", createSpawnEggItem(EntityInit.TOAD));
        registerItem("spawn_eggs/mummy_toad", createSpawnEggItem(EntityInit.MUMMY_TOAD));
        registerItem("spawn_eggs/goomba", createSpawnEggItem(EntityInit.GOOMBA));
        registerItem("spawn_eggs/paragoomba", createSpawnEggItem(EntityInit.PARAGOOMBA));
        registerItem("spawn_eggs/glad_goomba", createSpawnEggItem(EntityInit.GLAD_GOOMBA));
        registerItem("spawn_eggs/koopa_troopa", createSpawnEggItem(EntityInit.KOOPA_TROOPA));
        registerItem("spawn_eggs/paratroopa", createSpawnEggItem(EntityInit.PARATROOPA));
        registerItem("spawn_eggs/hammer_bro", createSpawnEggItem(EntityInit.HAMMER_BRO));
        registerItem("spawn_eggs/fire_bro", createSpawnEggItem(EntityInit.FIRE_BRO));
        registerItem("spawn_eggs/ice_bro", createSpawnEggItem(EntityInit.ICE_BRO));
        registerItem("spawn_eggs/bob_omb", createSpawnEggItem(EntityInit.BOB_OMB));
        registerItem("spawn_eggs/boo", createSpawnEggItem(EntityInit.BOO));
        registerItem("spawn_eggs/buzzy_beetle", createSpawnEggItem(EntityInit.BUZZY_BEETLE));
        registerItem("spawn_eggs/spike_top", createSpawnEggItem(EntityInit.SPIKE_TOP));
        registerItem("spawn_eggs/nipper_plant", createSpawnEggItem(EntityInit.NIPPER_PLANT));
        registerItem("spawn_eggs/stingby", createSpawnEggItem(EntityInit.STINGBY));
        registerItem("spawn_eggs/rotten_mushroom", createSpawnEggItem(EntityInit.ROTTEN_MUSHROOM));
        registerItem("spawn_eggs/fuzzy", createSpawnEggItem(EntityInit.FUZZY));
        registerItem("spawn_eggs/thwomp", createSpawnEggItem(EntityInit.THWOMP));
        registerItem("spawn_eggs/fake_block", createSpawnEggItem(EntityInit.FAKE_BLOCK));
    }

    private static void addToBuildingBlocks(String id, Block block) {
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(CreativeTabs.BLOCK_GROUP)));
    }

    private static void addToDecorations(String id, Block block) {
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(CreativeTabs.DECORATION_GROUP)));
    }

    public static void registerDecorations() {
        addToDecorations("green_mushroom", MushroomBlocks.GREEN_MUSHROOM);
        addToDecorations("pink_mushroom", MushroomBlocks.PINK_MUSHROOM);
        addToDecorations("yellow_mushroom", MushroomBlocks.YELLOW_MUSHROOM);
        addToDecorations("orange_mushroom", MushroomBlocks.ORANGE_MUSHROOM);
        addToDecorations("purple_mushroom", MushroomBlocks.PURPLE_MUSHROOM);

        addToDecorations("amanita_sapling", PlantBlocks.AMANITA_SAPLING);
        addToDecorations("dark_amanita_sapling", PlantBlocks.DARK_AMANITA_SAPLING);
        addToDecorations("amanita_carpet", PlantBlocks.AMANITA_CARPET);
        addToDecorations("bush", PlantBlocks.BUSH);
        addToDecorations("horsetail", PlantBlocks.HORSETAIL);
        addToDecorations("vegetable", PlantBlocks.VEGETABLE);

        addToDecorations("pawflower", PlantBlocks.PAWFLOWER);
        addToDecorations("blue_songflower", PlantBlocks.BLUE_SONGFLOWER);
        addToDecorations("pink_songflower", PlantBlocks.PINK_SONGFLOWER);
        addToDecorations("yellow_songflower", PlantBlocks.YELLOW_SONGFLOWER);
        addToDecorations("purple_songflower", PlantBlocks.PURPLE_SONGFLOWER);
        addToDecorations("fire_tulip", PlantBlocks.FIRE_TULIP);

        addToDecorations("yellow_flowerbed", PlantBlocks.YELLOW_FLOWERBED);
        addToDecorations("white_flowerbed", PlantBlocks.WHITE_FLOWERBED);
        addToDecorations("hybrid_flowerbed", PlantBlocks.HYBRID_FLOWERBED);

        addToDecorations("cave_mushrooms", PlantBlocks.CAVE_MUSHROOMS);
        addToDecorations("muncher", PlantBlocks.MUNCHER);
        addToDecorations("frozen_muncher", PlantBlocks.FROZEN_MUNCHER);
        addToDecorations("pit_plant", PlantBlocks.PIT_PLANT);
        registerItem("piranha_lily", new PiranhaLilyItem(PlantBlocks.PIRANHA_LILY, new FabricItemSettings().group(CreativeTabs.DECORATION_GROUP)));
        addToDecorations("budding_beanstalk", PlantBlocks.BUDDING_BEANSTALK);
        addToDecorations("beanstalk", PlantBlocks.BEANSTALK);
        addToDecorations("strawberry_coral", BlockInit.STRAWBERRY_CORAL);
        addToDecorations("fuzzbush", PlantBlocks.FUZZBUSH);

        registerItem("amanita_sign", new SignItem(new FabricItemSettings().group(CreativeTabs.DECORATION_GROUP).maxCount(16), BlockInit.AMANITA_SIGN, BlockInit.AMANITA_WALL_SIGN));
        registerItem("dark_amanita_sign", new SignItem(new FabricItemSettings().group(CreativeTabs.DECORATION_GROUP).maxCount(16), BlockInit.DARK_AMANITA_SIGN, BlockInit.DARK_AMANITA_WALL_SIGN));
        addToDecorations("amanita_door", BlockInit.AMANITA_DOOR);
        addToDecorations("dark_amanita_door", BlockInit.DARK_AMANITA_DOOR);
        addToDecorations("amanita_trapdoor", BlockInit.AMANITA_TRAPDOOR);
        addToDecorations("dark_amanita_trapdoor", BlockInit.DARK_AMANITA_TRAPDOOR);
        addToDecorations("amanita_fence", VariantBlocks.AMANITA_FENCE);
        addToDecorations("dark_amanita_fence", VariantBlocks.DARK_AMANITA_FENCE);
        addToDecorations("amanita_fence_gate", VariantBlocks.AMANITA_FENCE_GATE);
        addToDecorations("dark_amanita_fence_gate", VariantBlocks.DARK_AMANITA_FENCE_GATE);
        addToDecorations("amanita_pressure_plate", BlockInit.AMANITA_PRESSURE_PLATE);
        addToDecorations("dark_amanita_pressure_plate", BlockInit.DARK_AMANITA_PRESSURE_PLATE);
        addToDecorations("amanita_button", BlockInit.AMANITA_BUTTON);
        addToDecorations("dark_amanita_button", BlockInit.DARK_AMANITA_BUTTON);

        addToDecorations("question_block", BlockInit.QUESTION_BLOCK);
        addToDecorations("question_box", BlockInit.QUESTION_BOX);
        addToDecorations("coin_block", BlockInit.COIN_BLOCK);
        addToDecorations("fake_block", BlockInit.FAKE_BLOCK);
        addToDecorations("empty_block", BlockInit.EMPTY_BLOCK);
        addToDecorations("hidden_block", BlockInit.HIDDEN_BLOCK);
        addToDecorations("glow_block", BlockInit.GLOW_BLOCK);
        addToDecorations("jellybeam", BlockInit.JELLYBEAM);
        addToDecorations("pull_block", BlockInit.PULL_BLOCK);
        addToDecorations("jump_block", BlockInit.JUMP_BLOCK);
        addToDecorations("redstone_trampoline", BlockInit.REDSTONE_TRAMPOLINE);
        addToDecorations("trampoline", BlockInit.TRAMPOLINE);
        addToDecorations("donut_block", BlockInit.DONUT_BLOCK);
        addToDecorations("girder", BlockInit.GIRDER);
        addToDecorations("spike_trap", BlockInit.SPIKE_TRAP);
        addToDecorations("star_crystal", BlockInit.STAR_CRYSTAL);
        addToDecorations("icicle", BlockInit.ICICLE);
        addToDecorations("boo_lantern", BlockInit.BOO_LANTERN);
        addToDecorations("stone_torch", BlockInit.STONE_TORCH);
    }

    public static void registerBuildingBlocks() {
        addToBuildingBlocks("warp_frame", BlockInit.WARP_FRAME);
        addToBuildingBlocks("brown_mushroom_cap", MushroomBlocks.BROWN_MUSHROOM_CAP);
        addToBuildingBlocks("red_mushroom_cap", MushroomBlocks.RED_MUSHROOM_CAP);
        addToBuildingBlocks("green_mushroom_cap", MushroomBlocks.GREEN_MUSHROOM_CAP);
        addToBuildingBlocks("pink_mushroom_cap", MushroomBlocks.PINK_MUSHROOM_CAP);
        addToBuildingBlocks("yellow_mushroom_cap", MushroomBlocks.YELLOW_MUSHROOM_CAP);
        addToBuildingBlocks("orange_mushroom_cap", MushroomBlocks.ORANGE_MUSHROOM_CAP);
        addToBuildingBlocks("purple_mushroom_cap", MushroomBlocks.PURPLE_MUSHROOM_CAP);

        addToBuildingBlocks("mushroom_stem", MushroomBlocks.MUSHROOM_STEM);
        addToBuildingBlocks("beanstalk_block", BlockInit.BEANSTALK_BLOCK);
        addToBuildingBlocks("strawberry_coral_block", BlockInit.STRAWBERRY_CORAL_BLOCK);

        addToBuildingBlocks("toadstool_turf", BlockInit.TOADSTOOL_TURF);
        addToBuildingBlocks("toadstool_grass", BlockInit.TOADSTOOL_GRASS);
        addToBuildingBlocks("grassy_toadstone", BlockInit.GRASSY_TOADSTONE);
        addToBuildingBlocks("grassy_hardstone", BlockInit.GRASSY_HARDSTONE);

        addToBuildingBlocks("shoregrass", BlockInit.SHOREGRASS);
        addToBuildingBlocks("shoresand", BlockInit.SHORESAND);
        addToBuildingBlocks("snowy_sherbet_soil", BlockInit.SNOWY_SHERBET_SOIL);
        addToBuildingBlocks("sherbet_soil", BlockInit.SHERBET_SOIL);

        addToBuildingBlocks("toadstool_path", BlockInit.TOADSTOOL_PATH);
        addToBuildingBlocks("toadstool_soil", BlockInit.TOADSTOOL_SOIL);
        addToBuildingBlocks("coarse_toadstool_soil", BlockInit.COARSE_TOADSTOOL_SOIL);
        addToBuildingBlocks("toadstool_farmland", BlockInit.TOADSTOOL_FARMLAND);

        addToBuildingBlocks("happy_cloud", BlockInit.HAPPY_CLOUD);
        addToBuildingBlocks("cloud_block", BlockInit.CLOUD_BLOCK);
        addToBuildingBlocks("cloud_slab", VariantBlocks.CLOUD_SLAB);
        addToBuildingBlocks("cloud_stairs", VariantBlocks.CLOUD_STAIRS);

        addToBuildingBlocks("amanita_leaves", BlockInit.AMANITA_LEAVES);
        addToBuildingBlocks("fruiting_amanita_leaves", BlockInit.FRUITING_AMANITA_LEAVES);

        addToBuildingBlocks("dark_amanita_leaves", BlockInit.DARK_AMANITA_LEAVES);
        addToBuildingBlocks("fruiting_dark_amanita_leaves", BlockInit.FRUITING_DARK_AMANITA_LEAVES);

        addToBuildingBlocks("amanita_log", BlockInit.AMANITA_LOG);
        addToBuildingBlocks("stripped_amanita_log", BlockInit.STRIPPED_AMANITA_LOG);
        addToBuildingBlocks("amanita_wood", BlockInit.AMANITA_WOOD);
        addToBuildingBlocks("stripped_amanita_wood", BlockInit.STRIPPED_AMANITA_WOOD);

        addToBuildingBlocks("dark_amanita_log", BlockInit.DARK_AMANITA_LOG);
        addToBuildingBlocks("stripped_dark_amanita_log", BlockInit.STRIPPED_DARK_AMANITA_LOG);
        addToBuildingBlocks("dark_amanita_wood", BlockInit.DARK_AMANITA_WOOD);
        addToBuildingBlocks("stripped_dark_amanita_wood", BlockInit.STRIPPED_DARK_AMANITA_WOOD);

        addToBuildingBlocks("amanita_planks", BlockInit.AMANITA_PLANKS);
        addToBuildingBlocks("amanita_slab", VariantBlocks.AMANITA_SLAB);
        addToBuildingBlocks("amanita_stairs", VariantBlocks.AMANITA_STAIRS);

        addToBuildingBlocks("dark_amanita_planks", BlockInit.DARK_AMANITA_PLANKS);
        addToBuildingBlocks("dark_amanita_slab", VariantBlocks.DARK_AMANITA_SLAB);
        addToBuildingBlocks("dark_amanita_stairs", VariantBlocks.DARK_AMANITA_STAIRS);

        addToBuildingBlocks("vanillate", BlockInit.VANILLATE);
        addToBuildingBlocks("vanillate_bricks", BlockInit.VANILLATE_BRICKS);
        addToBuildingBlocks("vanillate_tiles", BlockInit.VANILLATE_TILES);
        addToBuildingBlocks("vanillate_slab", VariantBlocks.VANILLATE_SLAB);
        addToBuildingBlocks("vanillate_stairs", VariantBlocks.VANILLATE_STAIRS);
        addToBuildingBlocks("vanillate_brick_slab", VariantBlocks.VANILLATE_BRICK_SLAB);
        addToBuildingBlocks("vanillate_brick_stairs", VariantBlocks.VANILLATE_BRICK_STAIRS);
        addToBuildingBlocks("vanillate_crumble", BlockInit.VANILLATE_CRUMBLE);
        addToBuildingBlocks("topped_vanillate", BlockInit.TOPPED_VANILLATE);
        addToBuildingBlocks("gold_topped_vanillate", BlockInit.GOLD_TOPPED_VANILLATE);
        addToBuildingBlocks("amethyst_topped_vanillate", BlockInit.AMETHYST_TOPPED_VANILLATE);
        addToBuildingBlocks("bronze_ore", BlockInit.BRONZE_ORE);
        addToBuildingBlocks("vanillate_coal_ore", BlockInit.VANILLATE_COAL_ORE);
        addToBuildingBlocks("vanillate_iron_ore", BlockInit.VANILLATE_IRON_ORE);

        addToBuildingBlocks("frosty_vanillate", BlockInit.FROSTY_VANILLATE);
        addToBuildingBlocks("frosty_vanillate_bricks", BlockInit.FROSTY_VANILLATE_BRICKS);
        addToBuildingBlocks("frosty_vanillate_tiles", BlockInit.FROSTY_VANILLATE_TILES);
        addToBuildingBlocks("frosty_vanillate_slab", VariantBlocks.FROSTY_VANILLATE_SLAB);
        addToBuildingBlocks("frosty_vanillate_stairs", VariantBlocks.FROSTY_VANILLATE_STAIRS);
        addToBuildingBlocks("frosty_vanillate_brick_slab", VariantBlocks.FROSTY_VANILLATE_BRICK_SLAB);
        addToBuildingBlocks("frosty_vanillate_brick_stairs", VariantBlocks.FROSTY_VANILLATE_BRICK_STAIRS);
        addToBuildingBlocks("frosty_vanillate_crumble", BlockInit.FROSTY_VANILLATE_CRUMBLE);
        addToBuildingBlocks("frosted_vanillate", BlockInit.FROSTED_VANILLATE);
        addToBuildingBlocks("frozen_ore", BlockInit.FROZEN_ORE);

        addToBuildingBlocks("bronze_block", BlockInit.BRONZE_BLOCK);
        addToBuildingBlocks("raw_bronze_block", BlockInit.RAW_BRONZE_BLOCK);
        addToBuildingBlocks("bronze_slab", VariantBlocks.BRONZE_SLAB);
        addToBuildingBlocks("bronze_stairs", VariantBlocks.BRONZE_STAIRS);

        addToBuildingBlocks("white_bronze", ColoredBlocks.WHITE_BRONZE);
        addToBuildingBlocks("orange_bronze", ColoredBlocks.ORANGE_BRONZE);
        addToBuildingBlocks("magenta_bronze", ColoredBlocks.MAGENTA_BRONZE);
        addToBuildingBlocks("light_blue_bronze", ColoredBlocks.LIGHT_BLUE_BRONZE);
        addToBuildingBlocks("yellow_bronze", ColoredBlocks.YELLOW_BRONZE);
        addToBuildingBlocks("lime_bronze", ColoredBlocks.LIME_BRONZE);
        addToBuildingBlocks("pink_bronze", ColoredBlocks.PINK_BRONZE);
        addToBuildingBlocks("gray_bronze", ColoredBlocks.GRAY_BRONZE);
        addToBuildingBlocks("light_gray_bronze", ColoredBlocks.LIGHT_GRAY_BRONZE);
        addToBuildingBlocks("cyan_bronze", ColoredBlocks.CYAN_BRONZE);
        addToBuildingBlocks("purple_bronze", ColoredBlocks.PURPLE_BRONZE);
        addToBuildingBlocks("blue_bronze", ColoredBlocks.BLUE_BRONZE);
        addToBuildingBlocks("brown_bronze", ColoredBlocks.BROWN_BRONZE);
        addToBuildingBlocks("green_bronze", ColoredBlocks.GREEN_BRONZE);
        addToBuildingBlocks("red_bronze", ColoredBlocks.RED_BRONZE);
        addToBuildingBlocks("black_bronze", ColoredBlocks.BLACK_BRONZE);

        addToBuildingBlocks("toadstone", BlockInit.TOADSTONE);
        addToBuildingBlocks("smooth_toadstone", BlockInit.SMOOTH_TOADSTONE);
        addToBuildingBlocks("toadstone_bricks", BlockInit.TOADSTONE_BRICKS);
        addToBuildingBlocks("chiseled_toadstone_bricks", BlockInit.CHISELED_TOADSTONE_BRICKS);

        addToBuildingBlocks("toadstone_slab", VariantBlocks.TOADSTONE_SLAB);
        addToBuildingBlocks("toadstone_stairs", VariantBlocks.TOADSTONE_STAIRS);
        addToBuildingBlocks("toadstone_wall", VariantBlocks.TOADSTONE_WALL);

        addToBuildingBlocks("toadstone_brick_slab", VariantBlocks.TOADSTONE_BRICK_SLAB);
        addToBuildingBlocks("toadstone_brick_stairs", VariantBlocks.TOADSTONE_BRICK_STAIRS);
        addToBuildingBlocks("toadstone_brick_wall", VariantBlocks.TOADSTONE_BRICK_WALL);

        addToBuildingBlocks("gloomstone", BlockInit.GLOOMSTONE);
        addToBuildingBlocks("smooth_gloomstone", BlockInit.SMOOTH_GLOOMSTONE);
        addToBuildingBlocks("gloomstone_bricks", BlockInit.GLOOMSTONE_BRICKS);
        addToBuildingBlocks("chiseled_gloomstone_bricks", BlockInit.CHISELED_GLOOMSTONE_BRICKS);

        addToBuildingBlocks("gloomstone_slab", VariantBlocks.GLOOMSTONE_SLAB);
        addToBuildingBlocks("gloomstone_stairs", VariantBlocks.GLOOMSTONE_STAIRS);
        addToBuildingBlocks("gloomstone_wall", VariantBlocks.GLOOMSTONE_WALL);

        addToBuildingBlocks("gloomstone_brick_slab", VariantBlocks.GLOOMSTONE_BRICK_SLAB);
        addToBuildingBlocks("gloomstone_brick_stairs", VariantBlocks.GLOOMSTONE_BRICK_STAIRS);
        addToBuildingBlocks("gloomstone_brick_wall", VariantBlocks.GLOOMSTONE_BRICK_WALL);

        addToBuildingBlocks("gritzy_sand", BlockInit.GRITZY_SAND);
        addToBuildingBlocks("quicksand", BlockInit.QUICKSAND);
        addToBuildingBlocks("gritzy_sandstone", BlockInit.GRITZY_SANDSTONE);
        addToBuildingBlocks("chiseled_gritzy_sandstone", BlockInit.CHISELED_GRITZY_SANDSTONE);
        addToBuildingBlocks("cut_gritzy_sandstone", BlockInit.CUT_GRITZY_SANDSTONE);
        addToBuildingBlocks("smooth_gritzy_sandstone", BlockInit.SMOOTH_GRITZY_SANDSTONE);
        addToBuildingBlocks("gritzy_sandstone_slab", VariantBlocks.GRITZY_SANDSTONE_SLAB);
        addToBuildingBlocks("gritzy_sandstone_stairs", VariantBlocks.GRITZY_SANDSTONE_STAIRS);
        addToBuildingBlocks("gritzy_sandstone_wall", VariantBlocks.GRITZY_SANDSTONE_WALL);

        addToBuildingBlocks("seastone", BlockInit.SEASTONE);
        addToBuildingBlocks("seastone_bricks", BlockInit.SEASTONE_BRICKS);
        addToBuildingBlocks("seastone_brick_slab", VariantBlocks.SEASTONE_BRICK_SLAB);
        addToBuildingBlocks("seastone_brick_stairs", VariantBlocks.SEASTONE_BRICK_STAIRS);
        addToBuildingBlocks("seastone_brick_wall", VariantBlocks.SEASTONE_BRICK_WALL);

        addToBuildingBlocks("sherbet_bricks", BlockInit.SHERBET_BRICKS);
        addToBuildingBlocks("sherbet_brick_slab", VariantBlocks.SHERBET_BRICK_SLAB);
        addToBuildingBlocks("sherbet_brick_stairs", VariantBlocks.SHERBET_BRICK_STAIRS);
        addToBuildingBlocks("sherbet_brick_wall", VariantBlocks.SHERBET_BRICK_WALL);

        addToBuildingBlocks("golden_bricks", BlockInit.GOLDEN_BRICKS);
        addToBuildingBlocks("golden_brick_slab", VariantBlocks.GOLDEN_BRICK_SLAB);
        addToBuildingBlocks("golden_brick_stairs", VariantBlocks.GOLDEN_BRICK_STAIRS);
        addToBuildingBlocks("golden_brick_wall", VariantBlocks.GOLDEN_BRICK_WALL);

        addToBuildingBlocks("crystal_bricks", BlockInit.CRYSTAL_BRICKS);
        addToBuildingBlocks("crystal_brick_slab", VariantBlocks.CRYSTAL_BRICK_SLAB);
        addToBuildingBlocks("crystal_brick_stairs", VariantBlocks.CRYSTAL_BRICK_STAIRS);
        addToBuildingBlocks("crystal_brick_wall", VariantBlocks.CRYSTAL_BRICK_WALL);

        addToBuildingBlocks("hardstone", BlockInit.HARDSTONE);

        addToBuildingBlocks("polished_hardstone", BlockInit.POLISHED_HARDSTONE);
        addToBuildingBlocks("polished_hardstone_slab", VariantBlocks.POLISHED_HARDSTONE_SLAB);
        addToBuildingBlocks("polished_hardstone_stairs", VariantBlocks.POLISHED_HARDSTONE_STAIRS);
        addToBuildingBlocks("polished_hardstone_wall", VariantBlocks.POLISHED_HARDSTONE_WALL);

        addToBuildingBlocks("chiseled_hardstone", BlockInit.CHISELED_HARDSTONE);
        addToBuildingBlocks("hardstone_pillar", BlockInit.HARDSTONE_PILLAR);
        addToBuildingBlocks("hardstone_bricks", BlockInit.HARDSTONE_BRICKS);
        addToBuildingBlocks("chiseled_hardstone_bricks", BlockInit.CHISELED_HARDSTONE_BRICKS);
        addToBuildingBlocks("cracked_hardstone_bricks", BlockInit.CRACKED_HARDSTONE_BRICKS);

        addToBuildingBlocks("hardstone_brick_slab", VariantBlocks.HARDSTONE_BRICK_SLAB);
        addToBuildingBlocks("hardstone_brick_stairs", VariantBlocks.HARDSTONE_BRICK_STAIRS);
        addToBuildingBlocks("hardstone_brick_wall", VariantBlocks.HARDSTONE_BRICK_WALL);

        addToBuildingBlocks("royalite", BlockInit.ROYALITE);
        addToBuildingBlocks("cerise_ore", BlockInit.CERISE_ORE);
        addToBuildingBlocks("smooth_royalite", BlockInit.SMOOTH_ROYALITE);
        addToBuildingBlocks("royalite_bricks", BlockInit.ROYALITE_BRICKS);
        addToBuildingBlocks("cracked_royalite_bricks", BlockInit.CRACKED_ROYALITE_BRICKS);
        addToBuildingBlocks("chiseled_royalite_bricks", BlockInit.CHISELED_ROYALITE_BRICKS);

        addToBuildingBlocks("royalite_brick_slab", VariantBlocks.ROYALITE_BRICK_SLAB);
        addToBuildingBlocks("royalite_brick_stairs", VariantBlocks.ROYALITE_BRICK_STAIRS);
        addToBuildingBlocks("royalite_brick_wall", VariantBlocks.ROYALITE_BRICK_WALL);

        addToBuildingBlocks("cerise_block", BlockInit.CERISE_BLOCK);
        addToBuildingBlocks("cerise_bricks", BlockInit.CERISE_BRICKS);
        addToBuildingBlocks("cerise_tiles", BlockInit.CERISE_TILES);

        addToBuildingBlocks("cerise_brick_slab", VariantBlocks.CERISE_BRICK_SLAB);
        addToBuildingBlocks("cerise_brick_stairs", VariantBlocks.CERISE_BRICK_STAIRS);
        addToBuildingBlocks("cerise_tile_slab", VariantBlocks.CERISE_TILE_SLAB);
        addToBuildingBlocks("cerise_tile_stairs", VariantBlocks.CERISE_TILE_STAIRS);

        addToBuildingBlocks("white_warp_pipe", ColoredBlocks.WHITE_WARP_PIPE);
        addToBuildingBlocks("white_pipe_body", ColoredBlocks.WHITE_PIPE_BODY);
        addToBuildingBlocks("orange_warp_pipe", ColoredBlocks.ORANGE_WARP_PIPE);
        addToBuildingBlocks("orange_pipe_body", ColoredBlocks.ORANGE_PIPE_BODY);
        addToBuildingBlocks("magenta_warp_pipe", ColoredBlocks.MAGENTA_WARP_PIPE);
        addToBuildingBlocks("magenta_pipe_body", ColoredBlocks.MAGENTA_PIPE_BODY);
        addToBuildingBlocks("light_blue_warp_pipe", ColoredBlocks.LIGHT_BLUE_WARP_PIPE);
        addToBuildingBlocks("light_blue_pipe_body", ColoredBlocks.LIGHT_BLUE_PIPE_BODY);
        addToBuildingBlocks("yellow_warp_pipe", ColoredBlocks.YELLOW_WARP_PIPE);
        addToBuildingBlocks("yellow_pipe_body", ColoredBlocks.YELLOW_PIPE_BODY);
        addToBuildingBlocks("lime_warp_pipe", ColoredBlocks.LIME_WARP_PIPE);
        addToBuildingBlocks("lime_pipe_body", ColoredBlocks.LIME_PIPE_BODY);
        addToBuildingBlocks("pink_warp_pipe", ColoredBlocks.PINK_WARP_PIPE);
        addToBuildingBlocks("pink_pipe_body", ColoredBlocks.PINK_PIPE_BODY);
        addToBuildingBlocks("gray_warp_pipe", ColoredBlocks.GRAY_WARP_PIPE);
        addToBuildingBlocks("gray_pipe_body", ColoredBlocks.GRAY_PIPE_BODY);
        addToBuildingBlocks("light_gray_warp_pipe", ColoredBlocks.LIGHT_GRAY_WARP_PIPE);
        addToBuildingBlocks("light_gray_pipe_body", ColoredBlocks.LIGHT_GRAY_PIPE_BODY);
        addToBuildingBlocks("cyan_warp_pipe", ColoredBlocks.CYAN_WARP_PIPE);
        addToBuildingBlocks("cyan_pipe_body", ColoredBlocks.CYAN_PIPE_BODY);
        addToBuildingBlocks("purple_warp_pipe", ColoredBlocks.PURPLE_WARP_PIPE);
        addToBuildingBlocks("purple_pipe_body", ColoredBlocks.PURPLE_PIPE_BODY);
        addToBuildingBlocks("blue_warp_pipe", ColoredBlocks.BLUE_WARP_PIPE);
        addToBuildingBlocks("blue_pipe_body", ColoredBlocks.BLUE_PIPE_BODY);
        addToBuildingBlocks("brown_warp_pipe", ColoredBlocks.BROWN_WARP_PIPE);
        addToBuildingBlocks("brown_pipe_body", ColoredBlocks.BROWN_PIPE_BODY);
        addToBuildingBlocks("warp_pipe", ColoredBlocks.GREEN_WARP_PIPE);
        addToBuildingBlocks("warp_pipe_body", ColoredBlocks.GREEN_PIPE_BODY);
        addToBuildingBlocks("red_warp_pipe", ColoredBlocks.RED_WARP_PIPE);
        addToBuildingBlocks("red_pipe_body", ColoredBlocks.RED_PIPE_BODY);
        addToBuildingBlocks("black_warp_pipe", ColoredBlocks.BLACK_WARP_PIPE);
        addToBuildingBlocks("black_pipe_body", ColoredBlocks.BLACK_PIPE_BODY);
    }
}
