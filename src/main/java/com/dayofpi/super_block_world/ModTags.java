package com.dayofpi.super_block_world;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;

public class ModTags {
    public static TagKey<Biome> SURFACE_BIOMES;
    public static TagKey<Biome> SURFACE_GOOMBA_SPAWN;
    public static TagKey<Biome> SURFACE_KOOPA_SPAWN;
    public static TagKey<Biome> SURFACE_SHY_GUY_SPAWN;
    public static TagKey<Block> FIRE_CIRCLE_BREAK_TARGETS;
    public static TagKey<Block> FIRE_CIRCLE_LIT_TARGETS;
    public static TagKey<Block> FLAGS;
    public static TagKey<Block> GOOP;
    public static TagKey<Block> PIPE_BODIES;
    public static TagKey<Block> ROYALITE;
    public static TagKey<Block> VANILLATE;
    public static TagKey<Block> WARP_PIPES;
    public static TagKey<EntityType<?>> STOMP_IGNORED;
    public static TagKey<EntityType<?>> STOMP_IMMUNE;
    public static TagKey<Fluid> POISON;
    public static TagKey<Item> SHELLMETS;
    public static TagKey<Item> STAR_BITS;
    public static TagKey<Item> PAINT;
    public static TagKey<Structure> GHOST_HOUSE;
    public static TagKey<Structure> ON_ARENA_FINDER_MAP;
    public static TagKey<Structure> ON_ISLAND_EXPLORER_MAP;

    private static <T> TagKey<T> registerTag(RegistryKey<? extends Registry<T>> registry, String id) {
        return TagKey.of(registry, new Identifier(Main.MOD_ID, id));
    }

    private static void registerBiomeTags() {
        SURFACE_BIOMES = registerTag(RegistryKeys.BIOME, "surface_biomes");
        SURFACE_GOOMBA_SPAWN = registerTag(RegistryKeys.BIOME, "surface_goomba_spawn");
        SURFACE_KOOPA_SPAWN = registerTag(RegistryKeys.BIOME, "surface_koopa_spawn");
        SURFACE_SHY_GUY_SPAWN = registerTag(RegistryKeys.BIOME, "surface_shy_guy_spawn");
    }

    private static void registerBlockTags() {
        FIRE_CIRCLE_BREAK_TARGETS = registerTag(RegistryKeys.BLOCK, "fire_circle_break_targets");
        FIRE_CIRCLE_LIT_TARGETS = registerTag(RegistryKeys.BLOCK, "fire_circle_lit_targets");
        FLAGS = registerTag(RegistryKeys.BLOCK, "flags");
        GOOP = registerTag(RegistryKeys.BLOCK, "goop");
        PIPE_BODIES = registerTag(RegistryKeys.BLOCK, "pipe_bodies");
        ROYALITE = registerTag(RegistryKeys.BLOCK, "royalite");
        VANILLATE = registerTag(RegistryKeys.BLOCK, "vanillate");
        WARP_PIPES = registerTag(RegistryKeys.BLOCK, "warp_pipes");
    }

    private static void registerEntityTags() {
        STOMP_IGNORED = registerTag(RegistryKeys.ENTITY_TYPE, "stomp_ignored");
        STOMP_IMMUNE = registerTag(RegistryKeys.ENTITY_TYPE, "stomp_immune");
    }

    private static void registerStructureTags() {
        GHOST_HOUSE = registerTag(RegistryKeys.STRUCTURE, "ghost_house");
        ON_ARENA_FINDER_MAP = registerTag(RegistryKeys.STRUCTURE, "on_arena_finder_map");
        ON_ISLAND_EXPLORER_MAP = registerTag(RegistryKeys.STRUCTURE, "on_island_explorer_map");
    }

    public static void register() {
        POISON = registerTag(RegistryKeys.FLUID, "poison");
        registerBiomeTags();
        registerBlockTags();
        registerEntityTags();
        registerStructureTags();
        SHELLMETS = registerTag(RegistryKeys.ITEM, "shellmets");
        STAR_BITS = registerTag(RegistryKeys.ITEM, "star_bits");
        PAINT = registerTag(RegistryKeys.ITEM, "paint");
    }
}
