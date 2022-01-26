package com.dayofpi.super_block_world.registry.main;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.items.*;
import com.dayofpi.super_block_world.common.items.armor.CloudBootsItem;
import com.dayofpi.super_block_world.common.items.armor.JumpBootsItem;
import com.dayofpi.super_block_world.common.items.armor.PlumberCapItem;
import com.dayofpi.super_block_world.common.items.armor.ShellItem;
import com.dayofpi.super_block_world.common.items.projectile.*;
import com.dayofpi.super_block_world.common.util.mixin_aid.ModBoatType;
import com.dayofpi.super_block_world.registry.block.ColoredBlocks;
import com.dayofpi.super_block_world.registry.block.MushroomBlocks;
import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.registry.block.VariantBlocks;
import com.dayofpi.super_block_world.registry.more.CreativeTabs;
import com.dayofpi.super_block_world.registry.more.FluidInit;
import com.dayofpi.super_block_world.registry.more.FoodComponents;
import com.dayofpi.super_block_world.registry.more.Materials;
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
    public static final Item POWER_STAR = new PowerStarItem(new FabricItemSettings().rarity(Rarity.RARE).group(CreativeTabs.ITEM_GROUP));
    public static final Item POWER_DUST = new Item(new FabricItemSettings());
    public static final Item YOSHI_FRUIT = new Item(new FabricItemSettings().food(FoodComponents.YOSHI_FRUIT).group(CreativeTabs.ITEM_GROUP));
    public static final Item YOSHI_COOKIE = new Item(new FabricItemSettings().food(FoodComponents.YOSHI_COOKIE).group(CreativeTabs.ITEM_GROUP));
    public static final Item HORSETAIL_TART = new Item(new FabricItemSettings().food(FoodComponents.HORSETAIL_TART).group(CreativeTabs.ITEM_GROUP));
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
    public static final Item MAGIC_WINGS = new Item(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).rarity(Rarity.UNCOMMON));
    public static final Item COIN = new BlockItem(BlockInit.COIN,new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item STAR_COIN = new BlockItem(BlockInit.STAR_COIN,new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
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
    public static final Item PLUMBER_CAP = new PlumberCapItem(Materials.JUMP_ARMOR, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item PRINCESS_CROWN = new ArmorItem(Materials.PRINCESS, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).rarity(Rarity.RARE));
    public static final Item GREEN_SHELL = new ShellItem(Materials.GREEN_SHELL, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item RED_SHELL = new ShellItem(Materials.RED_SHELL, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BUZZY_SHELL = new ShellItem(Materials.BUZZY_ARMOR, EquipmentSlot.HEAD, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item DRY_BONES_SHELL = new DryBonesShellItem(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).fireproof());
    public static final Item JUMP_BOOTS = new JumpBootsItem(Materials.JUMP_ARMOR, EquipmentSlot.FEET, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item CLOUD_BOOTS = new CloudBootsItem(Materials.CLOUD_ARMOR, EquipmentSlot.FEET, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP));
    public static final Item BOTTLED_GHOST = new Item(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).group(CreativeTabs.ITEM_GROUP));
    public static final Item GREEN_MUSHROOM_ON_A_STICK = new OnAStickItem<>(new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxDamage(25), EntityInit.BUZZY_BEETLE, 2);
    public static final Item AMANITA_BOAT = new BoatItem(ModBoatType.AMANITA, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxCount(1));
    public static final Item DARK_AMANITA_BOAT = new BoatItem(ModBoatType.DARK_AMANITA, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxCount(1));
    public static final Item BELL_BOAT = new BoatItem(ModBoatType.BELL, new FabricItemSettings().group(CreativeTabs.ITEM_GROUP).maxCount(1));
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
        registerItem("yoshi_fruit", YOSHI_FRUIT);
        registerItem("yoshi_cookie", YOSHI_COOKIE);
        registerItem("horsetail_tart", HORSETAIL_TART);
        registerItem("turnip", TURNIP);
        registerItem("hammer", HAMMER);
        registerItem("super_pickax", SUPER_PICKAX);
        registerItem("bomb", BOMB);
        registerItem("fuzzy_magnet", FUZZY_MAGNET);
        registerItem("plumber_cap", PLUMBER_CAP);
        registerItem("princess_crown", PRINCESS_CROWN);
        registerItem("green_shell", GREEN_SHELL);
        registerItem("red_shell", RED_SHELL);
        registerItem("buzzy_shell", BUZZY_SHELL);
        registerItem("dry_bones_shell", DRY_BONES_SHELL);
        registerItem("jump_boots", JUMP_BOOTS);
        registerItem("cloud_boots", CLOUD_BOOTS);
        registerItem("super_mushroom", SUPER_MUSHROOM);
        registerItem("golden_mushroom", GOLDEN_MUSHROOM);
        registerItem("one_up", ONE_UP);
        registerItem("poison_mushroom", POISON_MUSHROOM);
        registerItem("fire_flower", FIRE_FLOWER);
        registerItem("ice_flower", ICE_FLOWER);
        registerItem("super_star", SUPER_STAR);
        registerItem("magic_wings", MAGIC_WINGS);
        registerItem("coin", COIN);
        registerItem("star_coin", STAR_COIN);
        registerItem("power_star", POWER_STAR);
        registerItem("power_dust", POWER_DUST);
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
        registerItem("bell_boat", BELL_BOAT);
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
        registerItem("spawn_eggs/dry_bones", createSpawnEggItem(EntityInit.DRY_BONES));
        registerItem("spawn_eggs/buzzy_beetle", createSpawnEggItem(EntityInit.BUZZY_BEETLE));
        registerItem("spawn_eggs/spike_top", createSpawnEggItem(EntityInit.SPIKE_TOP));
        registerItem("spawn_eggs/nipper_plant", createSpawnEggItem(EntityInit.NIPPER_PLANT));
        registerItem("spawn_eggs/stingby", createSpawnEggItem(EntityInit.STINGBY));
        registerItem("spawn_eggs/rotten_mushroom", createSpawnEggItem(EntityInit.ROTTEN_MUSHROOM));
        registerItem("spawn_eggs/fuzzy", createSpawnEggItem(EntityInit.FUZZY));
        registerItem("spawn_eggs/thwomp", createSpawnEggItem(EntityInit.THWOMP));
        registerItem("spawn_eggs/fake_block", createSpawnEggItem(EntityInit.FAKE_BLOCK));
    }

    private static void registerBlockItem(String id, Block block) {
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(CreativeTabs.BLOCK_GROUP)));
    }

    private static void registerDecoration(String id, Block block) {
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(CreativeTabs.DECORATION_GROUP)));
    }

    public static void registerDecorations() {
        registerDecoration("green_mushroom", MushroomBlocks.GREEN_MUSHROOM);
        registerDecoration("pink_mushroom", MushroomBlocks.PINK_MUSHROOM);
        registerDecoration("yellow_mushroom", MushroomBlocks.YELLOW_MUSHROOM);
        registerDecoration("orange_mushroom", MushroomBlocks.ORANGE_MUSHROOM);
        registerDecoration("purple_mushroom", MushroomBlocks.PURPLE_MUSHROOM);

        registerDecoration("brown_toad_stool", MushroomBlocks.BROWN_TOAD_STOOL);
        registerDecoration("red_toad_stool", MushroomBlocks.RED_TOAD_STOOL);
        registerDecoration("green_toad_stool", MushroomBlocks.GREEN_TOAD_STOOL);
        registerDecoration("pink_toad_stool", MushroomBlocks.PINK_TOAD_STOOL);
        registerDecoration("yellow_toad_stool", MushroomBlocks.YELLOW_TOAD_STOOL);
        registerDecoration("orange_toad_stool", MushroomBlocks.ORANGE_TOAD_STOOL);
        registerDecoration("purple_toad_stool", MushroomBlocks.PURPLE_TOAD_STOOL);

        registerDecoration("amanita_sapling", PlantBlocks.AMANITA_SAPLING);
        registerDecoration("dark_amanita_sapling", PlantBlocks.DARK_AMANITA_SAPLING);
        registerDecoration("bell_sapling", PlantBlocks.BELL_SAPLING);
        registerDecoration("amanita_carpet", PlantBlocks.AMANITA_CARPET);
        registerDecoration("bush", PlantBlocks.BUSH);
        registerDecoration("horsetail", PlantBlocks.HORSETAIL);
        registerDecoration("vegetable", PlantBlocks.VEGETABLE);

        registerDecoration("pawflower", PlantBlocks.PAWFLOWER);
        registerDecoration("blue_songflower", PlantBlocks.BLUE_SONGFLOWER);
        registerDecoration("pink_songflower", PlantBlocks.PINK_SONGFLOWER);
        registerDecoration("yellow_songflower", PlantBlocks.YELLOW_SONGFLOWER);
        registerDecoration("purple_songflower", PlantBlocks.PURPLE_SONGFLOWER);
        registerItem("rocket_flower", new WaterFlowerItem(PlantBlocks.ROCKET_FLOWER, new FabricItemSettings().group(CreativeTabs.DECORATION_GROUP)));
        registerDecoration("fire_tulip", PlantBlocks.FIRE_TULIP);

        registerDecoration("yellow_flowerbed", PlantBlocks.YELLOW_FLOWERBED);
        registerDecoration("white_flowerbed", PlantBlocks.WHITE_FLOWERBED);
        registerDecoration("hybrid_flowerbed", PlantBlocks.HYBRID_FLOWERBED);

        registerDecoration("cave_mushrooms", PlantBlocks.CAVE_MUSHROOMS);
        registerDecoration("muncher", PlantBlocks.MUNCHER);
        registerDecoration("frozen_muncher", PlantBlocks.FROZEN_MUNCHER);
        registerDecoration("icicle", BlockInit.ICICLE);
        registerDecoration("freezie", BlockInit.FREEZIE);
        registerDecoration("dry_bones_pile", BlockInit.DRY_BONES_PILE);
        registerDecoration("star_crystal", BlockInit.STAR_CRYSTAL);
        registerDecoration("pit_plant", PlantBlocks.PIT_PLANT);
        registerItem("piranha_lily", new WaterFlowerItem(PlantBlocks.PIRANHA_LILY, new FabricItemSettings().group(CreativeTabs.DECORATION_GROUP)));
        registerDecoration("budding_beanstalk", PlantBlocks.BUDDING_BEANSTALK);
        registerDecoration("beanstalk", PlantBlocks.BEANSTALK);
        registerDecoration("strawberry_coral", PlantBlocks.STRAWBERRY_CORAL);
        registerDecoration("fuzzbush", PlantBlocks.FUZZBUSH);

        registerItem("amanita_sign", new SignItem(new FabricItemSettings().group(CreativeTabs.DECORATION_GROUP).maxCount(16), BlockInit.AMANITA_SIGN, BlockInit.AMANITA_WALL_SIGN));
        registerItem("dark_amanita_sign", new SignItem(new FabricItemSettings().group(CreativeTabs.DECORATION_GROUP).maxCount(16), BlockInit.DARK_AMANITA_SIGN, BlockInit.DARK_AMANITA_WALL_SIGN));
        registerItem("bell_sign", new SignItem(new FabricItemSettings().group(CreativeTabs.DECORATION_GROUP).maxCount(16), BlockInit.BELL_SIGN, BlockInit.BELL_WALL_SIGN));
        registerDecoration("amanita_door", BlockInit.AMANITA_DOOR);
        registerDecoration("dark_amanita_door", BlockInit.DARK_AMANITA_DOOR);
        registerDecoration("bell_door", BlockInit.BELL_DOOR);
        registerDecoration("amanita_trapdoor", BlockInit.AMANITA_TRAPDOOR);
        registerDecoration("dark_amanita_trapdoor", BlockInit.DARK_AMANITA_TRAPDOOR);
        registerDecoration("bell_trapdoor", BlockInit.BELL_TRAPDOOR);
        registerDecoration("amanita_fence", VariantBlocks.AMANITA_FENCE);
        registerDecoration("dark_amanita_fence", VariantBlocks.DARK_AMANITA_FENCE);
        registerDecoration("bell_fence", VariantBlocks.BELL_FENCE);
        registerDecoration("amanita_fence_gate", VariantBlocks.AMANITA_FENCE_GATE);
        registerDecoration("dark_amanita_fence_gate", VariantBlocks.DARK_AMANITA_FENCE_GATE);
        registerDecoration("bell_fence_gate", VariantBlocks.BELL_FENCE_GATE);
        registerDecoration("amanita_pressure_plate", BlockInit.AMANITA_PRESSURE_PLATE);
        registerDecoration("dark_amanita_pressure_plate", BlockInit.DARK_AMANITA_PRESSURE_PLATE);
        registerDecoration("bell_pressure_plate", BlockInit.BELL_PRESSURE_PLATE);
        registerDecoration("amanita_button", BlockInit.AMANITA_BUTTON);
        registerDecoration("dark_amanita_button", BlockInit.DARK_AMANITA_BUTTON);
        registerDecoration("bell_button", BlockInit.BELL_BUTTON);

        registerDecoration("boo_lantern", BlockInit.BOO_LANTERN);
        registerDecoration("stone_torch", BlockInit.STONE_TORCH);
        registerDecoration("question_block", BlockInit.QUESTION_BLOCK);
        registerDecoration("question_box", BlockInit.QUESTION_BOX);
        registerDecoration("coin_block", BlockInit.COIN_BLOCK);
        registerDecoration("fake_block", BlockInit.FAKE_BLOCK);
        registerDecoration("empty_block", BlockInit.EMPTY_BLOCK);
        registerDecoration("hidden_block", BlockInit.HIDDEN_BLOCK);
        registerDecoration("glow_block", BlockInit.GLOW_BLOCK);
        registerDecoration("jellybeam", BlockInit.JELLYBEAM);
        registerDecoration("spike_trap", BlockInit.SPIKE_TRAP);
        registerDecoration("pull_block", BlockInit.PULL_BLOCK);
        registerDecoration("jump_block", BlockInit.JUMP_BLOCK);
        registerDecoration("redstone_trampoline", BlockInit.REDSTONE_TRAMPOLINE);
        registerDecoration("trampoline", BlockInit.TRAMPOLINE);
        registerDecoration("donut_block", BlockInit.DONUT_BLOCK);
        registerDecoration("girder", BlockInit.GIRDER);


        registerDecoration("white_warp_pipe", ColoredBlocks.WHITE_WARP_PIPE);
        registerDecoration("white_pipe_body", ColoredBlocks.WHITE_PIPE_BODY);
        registerDecoration("orange_warp_pipe", ColoredBlocks.ORANGE_WARP_PIPE);
        registerDecoration("orange_pipe_body", ColoredBlocks.ORANGE_PIPE_BODY);
        registerDecoration("magenta_warp_pipe", ColoredBlocks.MAGENTA_WARP_PIPE);
        registerDecoration("magenta_pipe_body", ColoredBlocks.MAGENTA_PIPE_BODY);
        registerDecoration("light_blue_warp_pipe", ColoredBlocks.LIGHT_BLUE_WARP_PIPE);
        registerDecoration("light_blue_pipe_body", ColoredBlocks.LIGHT_BLUE_PIPE_BODY);
        registerDecoration("yellow_warp_pipe", ColoredBlocks.YELLOW_WARP_PIPE);
        registerDecoration("yellow_pipe_body", ColoredBlocks.YELLOW_PIPE_BODY);
        registerDecoration("lime_warp_pipe", ColoredBlocks.LIME_WARP_PIPE);
        registerDecoration("lime_pipe_body", ColoredBlocks.LIME_PIPE_BODY);
        registerDecoration("pink_warp_pipe", ColoredBlocks.PINK_WARP_PIPE);
        registerDecoration("pink_pipe_body", ColoredBlocks.PINK_PIPE_BODY);
        registerDecoration("gray_warp_pipe", ColoredBlocks.GRAY_WARP_PIPE);
        registerDecoration("gray_pipe_body", ColoredBlocks.GRAY_PIPE_BODY);
        registerDecoration("light_gray_warp_pipe", ColoredBlocks.LIGHT_GRAY_WARP_PIPE);
        registerDecoration("light_gray_pipe_body", ColoredBlocks.LIGHT_GRAY_PIPE_BODY);
        registerDecoration("cyan_warp_pipe", ColoredBlocks.CYAN_WARP_PIPE);
        registerDecoration("cyan_pipe_body", ColoredBlocks.CYAN_PIPE_BODY);
        registerDecoration("purple_warp_pipe", ColoredBlocks.PURPLE_WARP_PIPE);
        registerDecoration("purple_pipe_body", ColoredBlocks.PURPLE_PIPE_BODY);
        registerDecoration("blue_warp_pipe", ColoredBlocks.BLUE_WARP_PIPE);
        registerDecoration("blue_pipe_body", ColoredBlocks.BLUE_PIPE_BODY);
        registerDecoration("brown_warp_pipe", ColoredBlocks.BROWN_WARP_PIPE);
        registerDecoration("brown_pipe_body", ColoredBlocks.BROWN_PIPE_BODY);
        registerDecoration("warp_pipe", ColoredBlocks.GREEN_WARP_PIPE);
        registerDecoration("warp_pipe_body", ColoredBlocks.GREEN_PIPE_BODY);
        registerDecoration("red_warp_pipe", ColoredBlocks.RED_WARP_PIPE);
        registerDecoration("red_pipe_body", ColoredBlocks.RED_PIPE_BODY);
        registerDecoration("black_warp_pipe", ColoredBlocks.BLACK_WARP_PIPE);
        registerDecoration("black_pipe_body", ColoredBlocks.BLACK_PIPE_BODY);
    }

    public static void registerBuildingBlocks() {
        registerBlockItem("warp_frame", BlockInit.WARP_FRAME);
        registerBlockItem("brown_mushroom_cap", MushroomBlocks.BROWN_MUSHROOM_CAP);
        registerBlockItem("red_mushroom_cap", MushroomBlocks.RED_MUSHROOM_CAP);
        registerBlockItem("green_mushroom_cap", MushroomBlocks.GREEN_MUSHROOM_CAP);
        registerBlockItem("pink_mushroom_cap", MushroomBlocks.PINK_MUSHROOM_CAP);
        registerBlockItem("yellow_mushroom_cap", MushroomBlocks.YELLOW_MUSHROOM_CAP);
        registerBlockItem("orange_mushroom_cap", MushroomBlocks.ORANGE_MUSHROOM_CAP);
        registerBlockItem("purple_mushroom_cap", MushroomBlocks.PURPLE_MUSHROOM_CAP);

        registerBlockItem("mushroom_stem", MushroomBlocks.MUSHROOM_STEM);
        registerBlockItem("shroomstone", BlockInit.SHROOMSTONE);
        registerBlockItem("cobbled_shroomstone", BlockInit.COBBLED_SHROOMSTONE);
        registerBlockItem("exposed_shroomstone", BlockInit.EXPOSED_SHROOMSTONE);
        registerBlockItem("charrock", BlockInit.CHARROCK);
        registerBlockItem("beanstalk_block", BlockInit.BEANSTALK_BLOCK);
        registerBlockItem("strawberry_coral_block", BlockInit.STRAWBERRY_CORAL_BLOCK);

        registerBlockItem("toadstool_turf", BlockInit.TOADSTOOL_TURF);
        registerBlockItem("toadstool_grass", BlockInit.TOADSTOOL_GRASS);
        registerBlockItem("grassy_toadstone", BlockInit.GRASSY_TOADSTONE);
        registerBlockItem("grassy_hardstone", BlockInit.GRASSY_HARDSTONE);

        registerBlockItem("shoregrass", BlockInit.SHOREGRASS);
        registerBlockItem("shoresand", BlockInit.SHORESAND);
        registerBlockItem("snowy_sherbet_soil", BlockInit.SNOWY_SHERBET_SOIL);
        registerBlockItem("sherbet_soil", BlockInit.SHERBET_SOIL);

        registerBlockItem("toadstool_path", BlockInit.TOADSTOOL_PATH);
        registerBlockItem("toadstool_soil", BlockInit.TOADSTOOL_SOIL);
        registerBlockItem("coarse_toadstool_soil", BlockInit.COARSE_TOADSTOOL_SOIL);
        registerBlockItem("toadstool_farmland", BlockInit.TOADSTOOL_FARMLAND);

        registerBlockItem("happy_cloud", BlockInit.HAPPY_CLOUD);
        registerBlockItem("cloud_block", BlockInit.CLOUD_BLOCK);
        registerBlockItem("cloud_slab", VariantBlocks.CLOUD_SLAB);
        registerBlockItem("cloud_stairs", VariantBlocks.CLOUD_STAIRS);

        registerBlockItem("amanita_leaves", BlockInit.AMANITA_LEAVES);
        registerBlockItem("fruiting_amanita_leaves", BlockInit.FRUITING_AMANITA_LEAVES);

        registerBlockItem("dark_amanita_leaves", BlockInit.DARK_AMANITA_LEAVES);
        registerBlockItem("fruiting_dark_amanita_leaves", BlockInit.FRUITING_DARK_AMANITA_LEAVES);

        registerBlockItem("bell_cap", BlockInit.BELL_CAP);
        registerBlockItem("darkened_bell_cap", BlockInit.DARKENED_BELL_CAP);

        registerBlockItem("amanita_log", BlockInit.AMANITA_LOG);
        registerBlockItem("stripped_amanita_log", BlockInit.STRIPPED_AMANITA_LOG);
        registerBlockItem("amanita_wood", BlockInit.AMANITA_WOOD);
        registerBlockItem("stripped_amanita_wood", BlockInit.STRIPPED_AMANITA_WOOD);

        registerBlockItem("dark_amanita_log", BlockInit.DARK_AMANITA_LOG);
        registerBlockItem("stripped_dark_amanita_log", BlockInit.STRIPPED_DARK_AMANITA_LOG);
        registerBlockItem("dark_amanita_wood", BlockInit.DARK_AMANITA_WOOD);
        registerBlockItem("stripped_dark_amanita_wood", BlockInit.STRIPPED_DARK_AMANITA_WOOD);

        registerBlockItem("bell_log", BlockInit.BELL_LOG);
        registerBlockItem("bell_wood", BlockInit.BELL_WOOD);
        registerBlockItem("stripped_bell_log", BlockInit.STRIPPED_BELL_LOG);
        registerBlockItem("stripped_bell_wood", BlockInit.STRIPPED_BELL_WOOD);

        registerBlockItem("amanita_planks", BlockInit.AMANITA_PLANKS);
        registerBlockItem("amanita_slab", VariantBlocks.AMANITA_SLAB);
        registerBlockItem("amanita_stairs", VariantBlocks.AMANITA_STAIRS);

        registerBlockItem("dark_amanita_planks", BlockInit.DARK_AMANITA_PLANKS);
        registerBlockItem("dark_amanita_slab", VariantBlocks.DARK_AMANITA_SLAB);
        registerBlockItem("dark_amanita_stairs", VariantBlocks.DARK_AMANITA_STAIRS);

        registerBlockItem("bell_planks", BlockInit.BELL_PLANKS);
        registerBlockItem("bell_slab", VariantBlocks.BELL_SLAB);
        registerBlockItem("bell_stairs", VariantBlocks.BELL_STAIRS);

        registerBlockItem("vanillate", BlockInit.VANILLATE);
        registerBlockItem("vanillate_bricks", BlockInit.VANILLATE_BRICKS);
        registerBlockItem("vanillate_tiles", BlockInit.VANILLATE_TILES);
        registerBlockItem("vanillate_slab", VariantBlocks.VANILLATE_SLAB);
        registerBlockItem("vanillate_stairs", VariantBlocks.VANILLATE_STAIRS);
        registerBlockItem("vanillate_brick_slab", VariantBlocks.VANILLATE_BRICK_SLAB);
        registerBlockItem("vanillate_brick_stairs", VariantBlocks.VANILLATE_BRICK_STAIRS);
        registerBlockItem("vanillate_crumble", BlockInit.VANILLATE_CRUMBLE);
        registerBlockItem("topped_vanillate", BlockInit.TOPPED_VANILLATE);
        registerBlockItem("gold_topped_vanillate", BlockInit.GOLD_TOPPED_VANILLATE);
        registerBlockItem("amethyst_topped_vanillate", BlockInit.AMETHYST_TOPPED_VANILLATE);
        registerBlockItem("bronze_ore", BlockInit.BRONZE_ORE);
        registerBlockItem("vanillate_coal_ore", BlockInit.VANILLATE_COAL_ORE);
        registerBlockItem("vanillate_iron_ore", BlockInit.VANILLATE_IRON_ORE);

        registerBlockItem("frosty_vanillate", BlockInit.FROSTY_VANILLATE);
        registerBlockItem("frosty_vanillate_bricks", BlockInit.FROSTY_VANILLATE_BRICKS);
        registerBlockItem("frosty_vanillate_tiles", BlockInit.FROSTY_VANILLATE_TILES);
        registerBlockItem("frosty_vanillate_slab", VariantBlocks.FROSTY_VANILLATE_SLAB);
        registerBlockItem("frosty_vanillate_stairs", VariantBlocks.FROSTY_VANILLATE_STAIRS);
        registerBlockItem("frosty_vanillate_brick_slab", VariantBlocks.FROSTY_VANILLATE_BRICK_SLAB);
        registerBlockItem("frosty_vanillate_brick_stairs", VariantBlocks.FROSTY_VANILLATE_BRICK_STAIRS);
        registerBlockItem("frosty_vanillate_crumble", BlockInit.FROSTY_VANILLATE_CRUMBLE);
        registerBlockItem("frosted_vanillate", BlockInit.FROSTED_VANILLATE);
        registerBlockItem("frozen_ore", BlockInit.FROZEN_ORE);

        registerBlockItem("bronze_block", BlockInit.BRONZE_BLOCK);
        registerBlockItem("raw_bronze_block", BlockInit.RAW_BRONZE_BLOCK);
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

        registerBlockItem("toadstone", BlockInit.TOADSTONE);
        registerBlockItem("smooth_toadstone", BlockInit.SMOOTH_TOADSTONE);
        registerBlockItem("toadstone_bricks", BlockInit.TOADSTONE_BRICKS);
        registerBlockItem("chiseled_toadstone_bricks", BlockInit.CHISELED_TOADSTONE_BRICKS);

        registerBlockItem("toadstone_slab", VariantBlocks.TOADSTONE_SLAB);
        registerBlockItem("toadstone_stairs", VariantBlocks.TOADSTONE_STAIRS);
        registerBlockItem("toadstone_wall", VariantBlocks.TOADSTONE_WALL);

        registerBlockItem("toadstone_brick_slab", VariantBlocks.TOADSTONE_BRICK_SLAB);
        registerBlockItem("toadstone_brick_stairs", VariantBlocks.TOADSTONE_BRICK_STAIRS);
        registerBlockItem("toadstone_brick_wall", VariantBlocks.TOADSTONE_BRICK_WALL);

        registerBlockItem("gloomstone", BlockInit.GLOOMSTONE);
        registerBlockItem("smooth_gloomstone", BlockInit.SMOOTH_GLOOMSTONE);
        registerBlockItem("gloomstone_bricks", BlockInit.GLOOMSTONE_BRICKS);
        registerBlockItem("chiseled_gloomstone_bricks", BlockInit.CHISELED_GLOOMSTONE_BRICKS);

        registerBlockItem("gloomstone_slab", VariantBlocks.GLOOMSTONE_SLAB);
        registerBlockItem("gloomstone_stairs", VariantBlocks.GLOOMSTONE_STAIRS);
        registerBlockItem("gloomstone_wall", VariantBlocks.GLOOMSTONE_WALL);

        registerBlockItem("gloomstone_brick_slab", VariantBlocks.GLOOMSTONE_BRICK_SLAB);
        registerBlockItem("gloomstone_brick_stairs", VariantBlocks.GLOOMSTONE_BRICK_STAIRS);
        registerBlockItem("gloomstone_brick_wall", VariantBlocks.GLOOMSTONE_BRICK_WALL);

        registerBlockItem("gritzy_sand", BlockInit.GRITZY_SAND);
        registerBlockItem("quicksand", BlockInit.QUICKSAND);
        registerBlockItem("gritzy_sandstone", BlockInit.GRITZY_SANDSTONE);
        registerBlockItem("chiseled_gritzy_sandstone", BlockInit.CHISELED_GRITZY_SANDSTONE);
        registerBlockItem("cut_gritzy_sandstone", BlockInit.CUT_GRITZY_SANDSTONE);
        registerBlockItem("smooth_gritzy_sandstone", BlockInit.SMOOTH_GRITZY_SANDSTONE);
        registerBlockItem("gritzy_sandstone_slab", VariantBlocks.GRITZY_SANDSTONE_SLAB);
        registerBlockItem("gritzy_sandstone_stairs", VariantBlocks.GRITZY_SANDSTONE_STAIRS);
        registerBlockItem("gritzy_sandstone_wall", VariantBlocks.GRITZY_SANDSTONE_WALL);

        registerBlockItem("seastone", BlockInit.SEASTONE);
        registerBlockItem("seastone_bricks", BlockInit.SEASTONE_BRICKS);
        registerBlockItem("seastone_brick_slab", VariantBlocks.SEASTONE_BRICK_SLAB);
        registerBlockItem("seastone_brick_stairs", VariantBlocks.SEASTONE_BRICK_STAIRS);
        registerBlockItem("seastone_brick_wall", VariantBlocks.SEASTONE_BRICK_WALL);

        registerBlockItem("sherbet_bricks", BlockInit.SHERBET_BRICKS);
        registerBlockItem("sherbet_brick_slab", VariantBlocks.SHERBET_BRICK_SLAB);
        registerBlockItem("sherbet_brick_stairs", VariantBlocks.SHERBET_BRICK_STAIRS);
        registerBlockItem("sherbet_brick_wall", VariantBlocks.SHERBET_BRICK_WALL);

        registerBlockItem("golden_bricks", BlockInit.GOLDEN_BRICKS);
        registerBlockItem("golden_tiles", BlockInit.GOLDEN_TILES);
        registerBlockItem("golden_brick_slab", VariantBlocks.GOLDEN_BRICK_SLAB);
        registerBlockItem("golden_brick_stairs", VariantBlocks.GOLDEN_BRICK_STAIRS);
        registerBlockItem("golden_brick_wall", VariantBlocks.GOLDEN_BRICK_WALL);

        registerBlockItem("crystal_bricks", BlockInit.CRYSTAL_BRICKS);
        registerBlockItem("crystal_brick_slab", VariantBlocks.CRYSTAL_BRICK_SLAB);
        registerBlockItem("crystal_brick_stairs", VariantBlocks.CRYSTAL_BRICK_STAIRS);
        registerBlockItem("crystal_brick_wall", VariantBlocks.CRYSTAL_BRICK_WALL);

        registerBlockItem("hardstone", BlockInit.HARDSTONE);

        registerBlockItem("polished_hardstone", BlockInit.POLISHED_HARDSTONE);
        registerBlockItem("polished_hardstone_slab", VariantBlocks.POLISHED_HARDSTONE_SLAB);
        registerBlockItem("polished_hardstone_stairs", VariantBlocks.POLISHED_HARDSTONE_STAIRS);
        registerBlockItem("polished_hardstone_wall", VariantBlocks.POLISHED_HARDSTONE_WALL);

        registerBlockItem("chiseled_hardstone", BlockInit.CHISELED_HARDSTONE);
        registerBlockItem("hardstone_pillar", BlockInit.HARDSTONE_PILLAR);
        registerBlockItem("hardstone_bricks", BlockInit.HARDSTONE_BRICKS);
        registerBlockItem("chiseled_hardstone_bricks", BlockInit.CHISELED_HARDSTONE_BRICKS);
        registerBlockItem("cracked_hardstone_bricks", BlockInit.CRACKED_HARDSTONE_BRICKS);

        registerBlockItem("hardstone_brick_slab", VariantBlocks.HARDSTONE_BRICK_SLAB);
        registerBlockItem("hardstone_brick_stairs", VariantBlocks.HARDSTONE_BRICK_STAIRS);
        registerBlockItem("hardstone_brick_wall", VariantBlocks.HARDSTONE_BRICK_WALL);

        registerBlockItem("royalite", BlockInit.ROYALITE);
        registerBlockItem("cerise_ore", BlockInit.CERISE_ORE);
        registerBlockItem("smooth_royalite", BlockInit.SMOOTH_ROYALITE);
        registerBlockItem("royalite_bricks", BlockInit.ROYALITE_BRICKS);
        registerBlockItem("cracked_royalite_bricks", BlockInit.CRACKED_ROYALITE_BRICKS);
        registerBlockItem("chiseled_royalite_bricks", BlockInit.CHISELED_ROYALITE_BRICKS);

        registerBlockItem("royalite_brick_slab", VariantBlocks.ROYALITE_BRICK_SLAB);
        registerBlockItem("royalite_brick_stairs", VariantBlocks.ROYALITE_BRICK_STAIRS);
        registerBlockItem("royalite_brick_wall", VariantBlocks.ROYALITE_BRICK_WALL);

        registerBlockItem("cerise_block", BlockInit.CERISE_BLOCK);
        registerBlockItem("cerise_bricks", BlockInit.CERISE_BRICKS);
        registerBlockItem("cerise_tiles", BlockInit.CERISE_TILES);

        registerBlockItem("cerise_brick_slab", VariantBlocks.CERISE_BRICK_SLAB);
        registerBlockItem("cerise_brick_stairs", VariantBlocks.CERISE_BRICK_STAIRS);
        registerBlockItem("cerise_tile_slab", VariantBlocks.CERISE_TILE_SLAB);
        registerBlockItem("cerise_tile_stairs", VariantBlocks.CERISE_TILE_STAIRS);
    }
}
