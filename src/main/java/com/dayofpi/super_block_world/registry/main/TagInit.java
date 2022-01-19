package com.dayofpi.super_block_world.registry.main;

import com.dayofpi.super_block_world.Main;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class TagInit {
    public static Tag<Biome> ALL_BIOMES;
    public static Tag<Item> SHELLMETS;
    public static Tag<Block> ALWAYS_CARVABLE;
    public static Tag<Block> BRICKS;
    public static Tag<Block> WARP_PIPES;
    public static Tag<Block> TOPPINGS;
    public static Tag<Fluid> POISON;
    public static Tag<EntityType<?>> IGNORES_BOOTS;
    public static Tag<EntityType<?>> POISON_IMMUNE;
    public static Tag<EntityType<?>> STOMP_IMMUNE;

    public static void register() {
        ALL_BIOMES = TagFactory.BIOME.create(new Identifier(Main.MOD_ID, "all_biomes"));
        SHELLMETS = TagFactory.ITEM.create(new Identifier(Main.MOD_ID, "shellmets"));

        ALWAYS_CARVABLE = TagFactory.BLOCK.create(new Identifier(Main.MOD_ID, "always_carvable"));
        BRICKS = TagFactory.BLOCK.create(new Identifier(Main.MOD_ID, "bricks"));
        WARP_PIPES = TagFactory.BLOCK.create(new Identifier(Main.MOD_ID, "warp_pipes"));
        TOPPINGS = TagFactory.BLOCK.create(new Identifier(Main.MOD_ID, "toppings"));
        POISON = TagFactory.FLUID.create(new Identifier(Main.MOD_ID, "poison"));

        IGNORES_BOOTS = TagFactory.ENTITY_TYPE.create(new Identifier(Main.MOD_ID, "ignores_boots"));
        POISON_IMMUNE = TagFactory.ENTITY_TYPE.create(new Identifier(Main.MOD_ID, "poison_immune"));
        STOMP_IMMUNE = TagFactory.ENTITY_TYPE.create(new Identifier(Main.MOD_ID, "stomp_immune"));
    }
}
