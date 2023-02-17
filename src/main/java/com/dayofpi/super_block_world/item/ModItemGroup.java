package com.dayofpi.super_block_world.item;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.entity.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static ItemGroup GENERAL;

    public static void register() {
        GENERAL = FabricItemGroup.builder(new Identifier(Main.MOD_ID, "general")).displayName(Text.literal("Super Block World")).icon(() -> new ItemStack(ModBlocks.QUESTION_BLOCK)).build();
        ItemGroupEvents.modifyEntriesEvent(GENERAL).register(ModItemGroup::populate);
    }

    public static void populate(FabricItemGroupEntries entries) {
        entries.add(ModBlocks.COIN);
        entries.add(ModBlocks.STAR_COIN);
        entries.add(ModBlocks.POWER_STAR);
        entries.add(ModBlocks.ZTAR);
        entries.add(ModBlocks.WARP_FRAME);
        entries.add(ModBlocks.CORRUPTED_WARP_FRAME);
        entries.add(ModBlocks.BOWSER_LOCK);
        entries.add(ModBlocks.QUESTION_BLOCK);
        entries.add(ModBlocks.EMPTY_BLOCK);
        entries.add(ModBlocks.EXCLAMATION_BLOCK);
        entries.add(ModBlocks.FAKE_BLOCK);
        entries.add(ModBlocks.DASH_BLOCK);
        entries.add(ModBlocks.PULL_BLOCK);
        entries.add(ModBlocks.PROPELLER_BLOCK);
        entries.add(ModBlocks.SPIKE_TRAP);
        entries.add(ModBlocks.BILL_BLASTER);
        entries.add(ModBlocks.ON_OFF_SWITCH);
        entries.add(ModBlocks.DOTTED_LINE_BLOCK);
        entries.add(ModBlocks.RED_DOTTED_LINE_BLOCK);
        entries.add(ModBlocks.POW_BLOCK);
        entries.add(ModBlocks.P_SWITCH);
        entries.add(ModBlocks.SUPER_PICKAX);

        entries.add(ModBlocks.STONE_TORCH);
        entries.add(ModBlocks.CHINCHO_TORCH);
        entries.add(ModBlocks.BOO_LANTERN);
        entries.add(ModBlocks.GLOW_BLOCK);
        entries.add(ModBlocks.JELLYBEAM);

        entries.add(ModBlocks.GIRDER);
        entries.add(ModBlocks.DONUT_BLOCK);
        entries.add(ModBlocks.JUMP_BLOCK);
        entries.add(ModBlocks.TRAMPOLINE);
        entries.add(ModBlocks.REDSTONE_TRAMPOLINE);
        entries.add(ModBlocks.QUESTION_BOX);

        entries.add(ModBlocks.AMANITA_LOG);
        entries.add(ModBlocks.AMANITA_WOOD);
        entries.add(ModBlocks.STRIPPED_AMANITA_LOG);
        entries.add(ModBlocks.STRIPPED_AMANITA_WOOD);
        entries.add(ModBlocks.AMANITA_PLANKS);
        entries.add(ModBlocks.AMANITA_STAIRS);
        entries.add(ModBlocks.AMANITA_SLAB);
        entries.add(ModBlocks.AMANITA_FENCE);
        entries.add(ModBlocks.AMANITA_FENCE_GATE);
        entries.add(ModBlocks.AMANITA_DOOR);
        entries.add(ModBlocks.AMANITA_TRAPDOOR);
        entries.add(ModBlocks.AMANITA_PRESSURE_PLATE);
        entries.add(ModBlocks.AMANITA_BUTTON);
        entries.add(ModBlocks.AMANITA_SIGN);

        entries.add(ModBlocks.DARK_AMANITA_LOG);
        entries.add(ModBlocks.DARK_AMANITA_WOOD);
        entries.add(ModBlocks.STRIPPED_DARK_AMANITA_LOG);
        entries.add(ModBlocks.STRIPPED_DARK_AMANITA_WOOD);
        entries.add(ModBlocks.DARK_AMANITA_PLANKS);
        entries.add(ModBlocks.DARK_AMANITA_STAIRS);
        entries.add(ModBlocks.DARK_AMANITA_SLAB);
        entries.add(ModBlocks.DARK_AMANITA_FENCE);
        entries.add(ModBlocks.DARK_AMANITA_FENCE_GATE);
        entries.add(ModBlocks.DARK_AMANITA_DOOR);
        entries.add(ModBlocks.DARK_AMANITA_TRAPDOOR);
        entries.add(ModBlocks.DARK_AMANITA_PRESSURE_PLATE);
        entries.add(ModBlocks.DARK_AMANITA_BUTTON);
        entries.add(ModBlocks.DARK_AMANITA_SIGN);

        entries.add(ModBlocks.BELL_LOG);
        entries.add(ModBlocks.BELL_WOOD);
        entries.add(ModBlocks.STRIPPED_BELL_LOG);
        entries.add(ModBlocks.STRIPPED_BELL_WOOD);
        entries.add(ModBlocks.BELL_PLANKS);
        entries.add(ModBlocks.BELL_STAIRS);
        entries.add(ModBlocks.BELL_SLAB);
        entries.add(ModBlocks.BELL_FENCE);
        entries.add(ModBlocks.BELL_FENCE_GATE);
        entries.add(ModBlocks.BELL_DOOR);
        entries.add(ModBlocks.BELL_TRAPDOOR);
        entries.add(ModBlocks.BELL_PRESSURE_PLATE);
        entries.add(ModBlocks.BELL_BUTTON);
        entries.add(ModBlocks.BELL_SIGN);

        entries.add(ModBlocks.AMANITA_LEAVES);
        entries.add(ModBlocks.FRUITING_AMANITA_LEAVES);
        entries.add(ModBlocks.DARK_AMANITA_LEAVES);
        entries.add(ModBlocks.FRUITING_DARK_AMANITA_LEAVES);
        entries.add(ModBlocks.BELL_CAP);
        entries.add(ModBlocks.DARKENED_BELL_CAP);

        entries.add(ModBlocks.GREEN_MUSHROOM_BLOCK);
        entries.add(ModBlocks.YELLOW_MUSHROOM_BLOCK);
        entries.add(ModBlocks.PURPLE_MUSHROOM_BLOCK);
        entries.add(ModBlocks.ORANGE_MUSHROOM_BLOCK);
        entries.add(ModBlocks.PINK_MUSHROOM_BLOCK);

        entries.add(ModBlocks.RED_TOAD_STOOL);
        entries.add(ModBlocks.BROWN_TOAD_STOOL);
        entries.add(ModBlocks.GREEN_TOAD_STOOL);
        entries.add(ModBlocks.YELLOW_TOAD_STOOL);
        entries.add(ModBlocks.PURPLE_TOAD_STOOL);
        entries.add(ModBlocks.ORANGE_TOAD_STOOL);
        entries.add(ModBlocks.PINK_TOAD_STOOL);

        entries.add(ModBlocks.TOADSTOOL_GRASS);
        entries.add(ModBlocks.TOADSTOOL_TURF);
        entries.add(ModBlocks.TOADSTOOL_SOIL);
        entries.add(ModBlocks.COARSE_TOADSTOOL_SOIL);
        entries.add(ModBlocks.TOADSTOOL_PATH);

        entries.add(ModBlocks.CHOCOLATE_GROUND);
        entries.add(ModBlocks.SMOOTH_CHOCOLATE_GROUND);
        entries.add(ModBlocks.DARK_CHOCOLATE_GROUND);

        entries.add(ModBlocks.SNOWY_SHERBET_SOIL);
        entries.add(ModBlocks.SHERBET_SOIL);
        entries.add(ModBlocks.SHERBET_BRICKS);
        entries.add(ModBlocks.SHERBET_BRICK_STAIRS);
        entries.add(ModBlocks.SHERBET_BRICK_SLAB);

        entries.add(ModBlocks.CHERRY_GRASS);
        entries.add(ModBlocks.CHERRY_SOIL);

        entries.add(ModBlocks.SHOREGRASS);
        entries.add(ModBlocks.SHORESAND);

        entries.add(ModBlocks.GRITZY_SAND);
        entries.add(ModBlocks.QUICKSAND);
        entries.add(ModBlocks.GRITZY_SANDSTONE);
        entries.add(ModBlocks.GRITZY_SANDSTONE_STAIRS);
        entries.add(ModBlocks.GRITZY_SANDSTONE_SLAB);
        entries.add(ModBlocks.GRITZY_SANDSTONE_WALL);
        entries.add(ModBlocks.GRITZY_SANDSTONE_WALL);
        entries.add(ModBlocks.CHISELED_GRITZY_SANDSTONE);
        entries.add(ModBlocks.SMOOTH_GRITZY_SANDSTONE);
        entries.add(ModBlocks.CUT_GRITZY_SANDSTONE);

        entries.add(ModBlocks.SHROOMSTONE);
        entries.add(ModBlocks.COBBLED_SHROOMSTONE);
        entries.add(ModBlocks.EXPOSED_SHROOMSTONE);

        entries.add(ModBlocks.CHARROCK);
        entries.add(ModBlocks.BLAZING_CHARROCK);

        entries.add(ModBlocks.TOADSTONE);
        entries.add(ModBlocks.GRASSY_TOADSTONE);
        entries.add(ModBlocks.SMOOTH_TOADSTONE);
        entries.add(ModBlocks.TOADSTONE_BRICKS);
        entries.add(ModBlocks.TOADSTONE_BRICK_STAIRS);
        entries.add(ModBlocks.TOADSTONE_BRICK_SLAB);
        entries.add(ModBlocks.CHISELED_TOADSTONE_BRICKS);

        entries.add(ModBlocks.GOLDEN_BRICKS);
        entries.add(ModBlocks.GOLDEN_BRICK_STAIRS);
        entries.add(ModBlocks.GOLDEN_BRICK_SLAB);
        entries.add(ModBlocks.GOLDEN_BRICK_WALL);
        entries.add(ModBlocks.GOLDEN_TILES);

        entries.add(ModBlocks.CRYSTAL_BRICKS);
        entries.add(ModBlocks.CRYSTAL_BRICK_STAIRS);
        entries.add(ModBlocks.CRYSTAL_BRICK_SLAB);
        entries.add(ModBlocks.CRYSTAL_BRICK_WALL);

        entries.add(ModBlocks.GLOOMSTONE);
        entries.add(ModBlocks.SMOOTH_GLOOMSTONE);
        entries.add(ModBlocks.GLOOMSTONE_BRICKS);
        entries.add(ModBlocks.GLOOMSTONE_BRICK_STAIRS);
        entries.add(ModBlocks.GLOOMSTONE_BRICK_SLAB);
        entries.add(ModBlocks.CHISELED_GLOOMSTONE_BRICKS);

        entries.add(ModBlocks.SEASTONE);
        entries.add(ModBlocks.SEASTONE_BRICKS);
        entries.add(ModBlocks.SEASTONE_BRICK_STAIRS);
        entries.add(ModBlocks.SEASTONE_BRICK_SLAB);
        entries.add(ModBlocks.CHISELED_SEASTONE_BRICKS);

        entries.add(ModBlocks.HARDSTONE);
        entries.add(ModBlocks.GRASSY_HARDSTONE);
        entries.add(ModBlocks.CHISELED_HARDSTONE);
        entries.add(ModBlocks.POLISHED_HARDSTONE);
        entries.add(ModBlocks.POLISHED_HARDSTONE_STAIRS);
        entries.add(ModBlocks.POLISHED_HARDSTONE_SLAB);
        entries.add(ModBlocks.HARDSTONE_BRICKS);
        entries.add(ModBlocks.MOSSY_HARDSTONE_BRICKS);
        entries.add(ModBlocks.HARDSTONE_BRICK_STAIRS);
        entries.add(ModBlocks.HARDSTONE_BRICK_SLAB);
        entries.add(ModBlocks.HARDSTONE_BRICK_WALL);
        entries.add(ModBlocks.HARDSTONE_PILLAR);

        entries.add(ModBlocks.ROYALITE);
        entries.add(ModBlocks.SMOOTH_ROYALITE);
        entries.add(ModBlocks.ROYALITE_BRICKS);
        entries.add(ModBlocks.CRACKED_ROYALITE_BRICKS);
        entries.add(ModBlocks.ROYALITE_BRICK_STAIRS);
        entries.add(ModBlocks.ROYALITE_BRICK_SLAB);
        entries.add(ModBlocks.ROYALITE_BRICK_WALL);
        entries.add(ModBlocks.CHISELED_ROYALITE_BRICKS);

        entries.add(ModBlocks.CERISE_ORE);
        entries.add(ModBlocks.CERISE_BLOCK);
        entries.add(ModBlocks.CERISE_BRICKS);
        entries.add(ModBlocks.CERISE_BRICK_STAIRS);
        entries.add(ModBlocks.CERISE_BRICK_SLAB);
        entries.add(ModBlocks.CERISE_BRICK_WALL);
        entries.add(ModBlocks.CERISE_TILES);
        entries.add(ModBlocks.CERISE_TILE_STAIRS);
        entries.add(ModBlocks.CERISE_TILE_SLAB);
        entries.add(ModBlocks.CERISE_TILE_WALL);

        entries.add(ModBlocks.VANILLATE);
        entries.add(ModBlocks.VANILLATE_CRUMBLE);
        entries.add(ModBlocks.TOPPED_VANILLATE);
        entries.add(ModBlocks.COAL_TOPPED_VANILLATE);
        entries.add(ModBlocks.IRON_TOPPED_VANILLATE);
        entries.add(ModBlocks.GOLD_TOPPED_VANILLATE);
        entries.add(ModBlocks.AMETHYST_TOPPED_VANILLATE);
        entries.add(ModBlocks.VANILLATE_BRICKS);
        entries.add(ModBlocks.CRACKED_VANILLATE_BRICKS);
        entries.add(ModBlocks.VANILLATE_BRICK_STAIRS);
        entries.add(ModBlocks.VANILLATE_BRICK_SLAB);
        entries.add(ModBlocks.VANILLATE_BRICK_WALL);
        entries.add(ModBlocks.VANILLATE_TILES);

        entries.add(ModBlocks.FROSTY_VANILLATE);
        entries.add(ModBlocks.FROSTY_VANILLATE_CRUMBLE);
        entries.add(ModBlocks.FROSTED_VANILLATE);
        entries.add(ModBlocks.FROSTY_VANILLATE_BRICKS);
        entries.add(ModBlocks.FROSTY_VANILLATE_BRICK_STAIRS);
        entries.add(ModBlocks.FROSTY_VANILLATE_BRICK_SLAB);
        entries.add(ModBlocks.FROSTY_VANILLATE_BRICK_WALL);
        entries.add(ModBlocks.FROSTY_VANILLATE_TILES);

        entries.add(ModBlocks.BRONZE_ORE);
        entries.add(ModBlocks.RAW_BRONZE_BLOCK);
        entries.add(ModBlocks.BRONZE_BLOCK);
        entries.add(ModBlocks.BRONZE_STAIRS);
        entries.add(ModBlocks.BRONZE_SLAB);
        entries.add(ModBlocks.WHITE_BRONZE);
        entries.add(ModBlocks.LIGHT_GRAY_BRONZE);
        entries.add(ModBlocks.GRAY_BRONZE);
        entries.add(ModBlocks.BLACK_BRONZE);
        entries.add(ModBlocks.BROWN_BRONZE);
        entries.add(ModBlocks.RED_BRONZE);
        entries.add(ModBlocks.ORANGE_BRONZE);
        entries.add(ModBlocks.YELLOW_BRONZE);
        entries.add(ModBlocks.LIME_BRONZE);
        entries.add(ModBlocks.GREEN_BRONZE);
        entries.add(ModBlocks.CYAN_BRONZE);
        entries.add(ModBlocks.LIGHT_BLUE_BRONZE);
        entries.add(ModBlocks.BLUE_BRONZE);
        entries.add(ModBlocks.PURPLE_BRONZE);
        entries.add(ModBlocks.MAGENTA_BRONZE);
        entries.add(ModBlocks.PINK_BRONZE);

        entries.add(ModBlocks.WHITE_WARP_PIPE);
        entries.add(ModBlocks.LIGHT_GRAY_WARP_PIPE);
        entries.add(ModBlocks.GRAY_WARP_PIPE);
        entries.add(ModBlocks.BLACK_WARP_PIPE);
        entries.add(ModBlocks.BROWN_WARP_PIPE);
        entries.add(ModBlocks.RED_WARP_PIPE);
        entries.add(ModBlocks.ORANGE_WARP_PIPE);
        entries.add(ModBlocks.YELLOW_WARP_PIPE);
        entries.add(ModBlocks.LIME_WARP_PIPE);
        entries.add(ModBlocks.GREEN_WARP_PIPE);
        entries.add(ModBlocks.CYAN_WARP_PIPE);
        entries.add(ModBlocks.LIGHT_BLUE_WARP_PIPE);
        entries.add(ModBlocks.BLUE_WARP_PIPE);
        entries.add(ModBlocks.PURPLE_WARP_PIPE);
        entries.add(ModBlocks.MAGENTA_WARP_PIPE);
        entries.add(ModBlocks.PINK_WARP_PIPE);

        entries.add(ModBlocks.WHITE_PIPE_BODY);
        entries.add(ModBlocks.LIGHT_GRAY_PIPE_BODY);
        entries.add(ModBlocks.GRAY_PIPE_BODY);
        entries.add(ModBlocks.BLACK_PIPE_BODY);
        entries.add(ModBlocks.BROWN_PIPE_BODY);
        entries.add(ModBlocks.RED_PIPE_BODY);
        entries.add(ModBlocks.ORANGE_PIPE_BODY);
        entries.add(ModBlocks.YELLOW_PIPE_BODY);
        entries.add(ModBlocks.LIME_PIPE_BODY);
        entries.add(ModBlocks.GREEN_PIPE_BODY);
        entries.add(ModBlocks.CYAN_PIPE_BODY);
        entries.add(ModBlocks.LIGHT_BLUE_PIPE_BODY);
        entries.add(ModBlocks.BLUE_PIPE_BODY);
        entries.add(ModBlocks.PURPLE_PIPE_BODY);
        entries.add(ModBlocks.MAGENTA_PIPE_BODY);
        entries.add(ModBlocks.PINK_PIPE_BODY);

        entries.add(ModBlocks.DREAMWOOL);
        entries.add(ModBlocks.DREAMWOOL_CARPET);
        entries.add(ModBlocks.LIGHT_GRAY_DREAMPLUSH);
        entries.add(ModBlocks.BROWN_DREAMPLUSH);

        entries.add(ModBlocks.FLAGPOLE);
        entries.add(ModBlocks.WHITE_FLAG);
        entries.add(ModBlocks.LIGHT_GRAY_FLAG);
        entries.add(ModBlocks.GRAY_FLAG);
        entries.add(ModBlocks.BLACK_FLAG);
        entries.add(ModBlocks.BROWN_FLAG);
        entries.add(ModBlocks.RED_FLAG);
        entries.add(ModBlocks.ORANGE_FLAG);
        entries.add(ModBlocks.YELLOW_FLAG);
        entries.add(ModBlocks.LIME_FLAG);
        entries.add(ModBlocks.GREEN_FLAG);
        entries.add(ModBlocks.CYAN_FLAG);
        entries.add(ModBlocks.LIGHT_BLUE_FLAG);
        entries.add(ModBlocks.BLUE_FLAG);
        entries.add(ModBlocks.PURPLE_FLAG);
        entries.add(ModBlocks.MAGENTA_FLAG);
        entries.add(ModBlocks.PINK_FLAG);
        entries.add(ModBlocks.RAINBOW_FLAG);

        entries.add(ModBlocks.CLOUD_BLOCK);
        entries.add(ModBlocks.CLOUD_STAIRS);
        entries.add(ModBlocks.CLOUD_SLAB);
        entries.add(ModBlocks.HAPPY_CLOUD);

        entries.add(ModBlocks.RAINBOW_TILES);
        entries.add(ModBlocks.RAINBOW_TILE_STAIRS);
        entries.add(ModBlocks.RAINBOW_TILE_SLAB);
        entries.add(ModBlocks.RAINBOW_TILE_WALL);
        entries.add(ModBlocks.STAR_PANEL);
        entries.add(ModBlocks.STAR_CLUSTER);

        entries.add(ModBlocks.GOOP_BLOCK);
        entries.add(ModBlocks.GOOP);
        entries.add(ModBlocks.BLACK_PAINT_BLOCK);
        entries.add(ModBlocks.BLACK_PAINT);

        entries.add(ModBlocks.YOSHI_EGG);
        entries.add(ModBlocks.LIL_OINK_EGG);

        entries.add(ModBlocks.BEANSTALK_BLOCK);
        entries.add(ModBlocks.BEANSTALK);
        entries.add(ModBlocks.STRAWBERRY_CORAL_BLOCK);
        entries.add(ModBlocks.STRAWBERRY_CORAL);

        entries.add(ModBlocks.PIT_PLANT);
        entries.add(ModBlocks.PIRANHA_LILY);
        entries.add(ModBlocks.MUNCHER);
        entries.add(ModBlocks.FROZEN_MUNCHER);
        entries.add(ModBlocks.FREEZIE);
        entries.add(ModBlocks.DRY_BONES_PILE);

        entries.add(ModBlocks.FUZZBALL);
        entries.add(ModBlocks.FUZZBUSH);

        entries.add(ModBlocks.CAVE_MUSHROOMS);
        entries.add(ModBlocks.BLUE_CAVE_MUSHROOMS);
        entries.add(ModBlocks.GREEN_MUSHROOM);
        entries.add(ModBlocks.YELLOW_MUSHROOM);
        entries.add(ModBlocks.PURPLE_MUSHROOM);
        entries.add(ModBlocks.ORANGE_MUSHROOM);
        entries.add(ModBlocks.PINK_MUSHROOM);

        entries.add(ModBlocks.SHORT_GRASS);
        entries.add(ModBlocks.RED_GRASS);
        entries.add(ModBlocks.BUSH);
        entries.add(ModBlocks.HORSETAIL);
        entries.add(ModBlocks.AMANITA_CARPET);
        entries.add(ModBlocks.SMILEY_STEM);
        entries.add(ModBlocks.SMILEY_SUNFLOWER);
        entries.add(ModBlocks.SMILEY_SUNFLOWER_SEED);

        entries.add(ModBlocks.YELLOW_SONGFLOWER);
        entries.add(ModBlocks.BLUE_SONGFLOWER);
        entries.add(ModBlocks.PINK_SONGFLOWER);
        entries.add(ModBlocks.PAWFLOWER);
        entries.add(ModBlocks.ROCKET_FLOWER);
        entries.add(ModBlocks.FIRE_TULIP);

        entries.add(ModBlocks.YELLOW_FLOWERBED);
        entries.add(ModBlocks.WHITE_FLOWERBED);

        entries.add(ModBlocks.AMANITA_SAPLING);
        entries.add(ModBlocks.DARK_AMANITA_SAPLING);
        entries.add(ModBlocks.BELL_SAPLING);

        entries.add(ModItems.RAW_BRONZE);
        entries.add(ModItems.BRONZE_INGOT);
        entries.add(ModItems.CERISE);
        entries.add(ModItems.STELLAR_SHARD);
        entries.add(ModItems.YELLOW_STAR_BIT);
        entries.add(ModItems.GREEN_STAR_BIT);
        entries.add(ModItems.BLUE_STAR_BIT);
        entries.add(ModItems.PURPLE_STAR_BIT);
        entries.add(ModItems.SPINDRIFT_BLOSSOM);
        entries.add(ModItems.KART_TIRE);
        entries.add(ModItems.SPIKE);
        entries.add(ModItems.TROOP_HIDE);
        entries.add(ModItems.CLOUD_PUFF);
        entries.add(ModItems.SUBCON_THREAD);
        entries.add(ModItems.GOOP_BALL);
        entries.add(ModItems.BLACK_PAINT);
        entries.add(ModItems.BUZZY_SHELL_PIECE);
        entries.add(ModItems.POISON_BUCKET);
        entries.add(ModItems.BOTTLED_GHOST);
        entries.add(ModItems.KEY);
        entries.add(ModItems.POWER_SHARD);
        entries.add(ModItems.YOSHI_FRUIT);
        entries.add(ModItems.YOSHI_COOKIE);
        entries.add(ModItems.HORSETAIL_TART);
        entries.add(ModItems.RAW_DINO);
        entries.add(ModItems.COOKED_DINO);
        entries.add(ModItems.SYRUP_BOTTLE);
        entries.add(ModItems.SHROOM_SHAKE);
        entries.add(ModItems.SUPER_MUSHROOM);
        entries.add(ModItems.GOLDEN_MUSHROOM);
        entries.add(ModItems.ONE_UP);
        entries.add(ModItems.POISON_MUSHROOM);
        entries.add(ModItems.LIFE_MUSHROOM);
        entries.add(ModItems.FIRE_FLOWER);
        entries.add(ModItems.ICE_FLOWER);
        entries.add(ModItems.BOOMERANG_FLOWER);
        entries.add(ModItems.GOLD_FLOWER);
        entries.add(ModItems.MAGIC_WAND);
        entries.add(ModItems.MAGIC_PAINTBRUSH);
        entries.add(ModItems.BEE_MUSHROOM);
        entries.add(ModItems.SUPER_LEAF);
        entries.add(ModItems.SUPER_BELL);
        entries.add(ModItems.SUPER_STAR);
        entries.add(ModItems.HAMMER);
        entries.add(ModItems.TURNIP);
        entries.add(ModItems.BOMB);
        entries.add(ModItems.SUPER_HEART);
        entries.add(ModItems.GO_KART);
        entries.add(ModItems.DRY_BONES_SHELL);
        entries.add(ModItems.TRAMPOLINE_MINECART);
        entries.add(ModItems.AMANITA_BOAT);
        entries.add(ModItems.AMANITA_CHEST_BOAT);
        entries.add(ModItems.DARK_AMANITA_BOAT);
        entries.add(ModItems.DARK_AMANITA_CHEST_BOAT);
        entries.add(ModItems.BELL_BOAT);
        entries.add(ModItems.BELL_CHEST_BOAT);
        entries.add(ModItems.GREEN_MUSHROOM_ON_A_STICK);
        entries.add(ModItems.WARP_LINK);
        entries.add(ModItems.FUZZY_MAGNET);
        entries.add(ModItems.LAUNCH_STAR);
        entries.add(ModItems.MECHAKOOPA);
        entries.add(ModItems.GOO_ME);
        entries.add(ModItems.MUSIC_DISC_FIRE_FACTORY);
        entries.add(ModItems.MUSIC_DISC_TOXICITY);
        entries.add(ModItems.MUSIC_DISC_WALTZ_OF_THE_LOST);
        entries.add(ModItems.MUSIC_DISC_MY_SONG);
        entries.add(ModItems.MUSIC_DISC_TRAPPED);
        entries.add(ModItems.ARROW_STAMP);
        entries.add(ModItems.BLOOPER_STAMP);
        entries.add(ModItems.BOO_STAMP);
        entries.add(ModItems.BUZZY_STAMP);
        entries.add(ModItems.CAT_STAMP);
        entries.add(ModItems.CLOUD_STAMP);
        entries.add(ModItems.FISH_STAMP);
        entries.add(ModItems.FLOWER_STAMP);
        entries.add(ModItems.FUZZY_STAMP);
        entries.add(ModItems.GOOMBA_STAMP);
        entries.add(ModItems.GRAFFITI_STAMP);
        entries.add(ModItems.LUIGI_STAMP);
        entries.add(ModItems.MARIO_STAMP);
        entries.add(ModItems.MOM_STAMP);
        entries.add(ModItems.MUSHROOM_STAMP);
        entries.add(ModItems.PRINCESS_STAMP);
        entries.add(ModItems.SMILEY_STAMP);
        entries.add(ModItems.ZOMBIE_STAMP);
        entries.add(ModItems.PLUMBER_CAP);
        entries.add(ModItems.PRINCESS_CROWN);
        entries.add(ModItems.GREEN_SHELL);
        entries.add(ModItems.RED_SHELL);
        entries.add(ModItems.BLUE_SHELL);
        entries.add(ModItems.GOLD_SHELL);
        entries.add(ModItems.BUZZY_SHELL);
        entries.add(ModItems.SHY_GUY_MASK);
        entries.add(ModItems.JUMP_BOOTS);
        entries.add(ModItems.CLOUD_BOOTS);

        entries.add(SpawnEggItem.forEntity(ModEntities.PETEY_PIRANHA));
        entries.add(SpawnEggItem.forEntity(ModEntities.KING_BOB_OMB));
        entries.add(SpawnEggItem.forEntity(ModEntities.KING_BOO));
        entries.add(SpawnEggItem.forEntity(ModEntities.TOAD));
        entries.add(SpawnEggItem.forEntity(ModEntities.MAILTOAD));
        entries.add(SpawnEggItem.forEntity(ModEntities.YOSHI));
        entries.add(SpawnEggItem.forEntity(ModEntities.MOO_MOO));
        entries.add(SpawnEggItem.forEntity(ModEntities.SLEEPY_SHEEP));
        entries.add(SpawnEggItem.forEntity(ModEntities.LIL_OINK));
        entries.add(SpawnEggItem.forEntity(ModEntities.GOOMBA));
        entries.add(SpawnEggItem.forEntity(ModEntities.PARAGOOMBA));
        entries.add(SpawnEggItem.forEntity(ModEntities.DARK_GOOMBA));
        entries.add(SpawnEggItem.forEntity(ModEntities.DARK_PARAGOOMBA));
        entries.add(SpawnEggItem.forEntity(ModEntities.GLAD_GOOMBA));
        entries.add(SpawnEggItem.forEntity(ModEntities.GLAD_PARAGOOMBA));
        entries.add(SpawnEggItem.forEntity(ModEntities.KOOPA_TROOPA));
        entries.add(SpawnEggItem.forEntity(ModEntities.PARATROOPA));
        entries.add(SpawnEggItem.forEntity(ModEntities.DRY_BONES));
        entries.add(SpawnEggItem.forEntity(ModEntities.PARABONES));
        entries.add(SpawnEggItem.forEntity(ModEntities.HAMMER_BRO));
        entries.add(SpawnEggItem.forEntity(ModEntities.FIRE_BRO));
        entries.add(SpawnEggItem.forEntity(ModEntities.ICE_BRO));
        entries.add(SpawnEggItem.forEntity(ModEntities.MAGIKOOPA));
        entries.add(SpawnEggItem.forEntity(ModEntities.BUZZY_BEETLE));
        entries.add(SpawnEggItem.forEntity(ModEntities.SPIKE_TOP));
        entries.add(SpawnEggItem.forEntity(ModEntities.SPINY));
        entries.add(SpawnEggItem.forEntity(ModEntities.PIRANHA_PLANT));
        entries.add(SpawnEggItem.forEntity(ModEntities.PUTRID_PIRANHA));
        entries.add(SpawnEggItem.forEntity(ModEntities.CHEEP_CHEEP));
        entries.add(SpawnEggItem.forEntity(ModEntities.BLOOPER));
        entries.add(SpawnEggItem.forEntity(ModEntities.UNAGI));
        entries.add(SpawnEggItem.forEntity(ModEntities.LAVA_BUBBLE));
        entries.add(SpawnEggItem.forEntity(ModEntities.SHY_GUY));
        entries.add(SpawnEggItem.forEntity(ModEntities.BOB_OMB));
        entries.add(SpawnEggItem.forEntity(ModEntities.BOB_OMB_BUDDY));
        entries.add(SpawnEggItem.forEntity(ModEntities.POKEY));
        entries.add(SpawnEggItem.forEntity(ModEntities.SNOW_POKEY));
        entries.add(SpawnEggItem.forEntity(ModEntities.BOO));
        entries.add(SpawnEggItem.forEntity(ModEntities.THWOMP));
        entries.add(SpawnEggItem.forEntity(ModEntities.FUZZY));
        entries.add(SpawnEggItem.forEntity(ModEntities.DINO_RHINO));
        entries.add(SpawnEggItem.forEntity(ModEntities.SPINDRIFT));
        entries.add(SpawnEggItem.forEntity(ModEntities.FAKE_BLOCK));
        entries.add(SpawnEggItem.forEntity(ModEntities.STINGBY));
        entries.add(SpawnEggItem.forEntity(ModEntities.BLOCKSTEPPER));
        entries.add(SpawnEggItem.forEntity(ModEntities.MUD_TROOPER));
        entries.add(SpawnEggItem.forEntity(ModEntities.MUMMY_ME));
        entries.add(SpawnEggItem.forEntity(ModEntities.ROTTEN_MUSHROOM));
        entries.add(SpawnEggItem.forEntity(ModEntities.BULLET_BILL));
    }
}
