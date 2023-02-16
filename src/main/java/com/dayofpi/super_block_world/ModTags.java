package com.dayofpi.super_block_world;

import com.dayofpi.super_block_world.Main;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
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
        SURFACE_BIOMES = registerTag(Registry.BIOME_KEY, "surface_biomes");
        SURFACE_GOOMBA_SPAWN = registerTag(Registry.BIOME_KEY, "surface_goomba_spawn");
        SURFACE_KOOPA_SPAWN = registerTag(Registry.BIOME_KEY, "surface_koopa_spawn");
        SURFACE_SHY_GUY_SPAWN = registerTag(Registry.BIOME_KEY, "surface_shy_guy_spawn");
    }

    private static void registerBlockTags() {
        FIRE_CIRCLE_BREAK_TARGETS = registerTag(Registry.BLOCK_KEY, "fire_circle_break_targets");
        FIRE_CIRCLE_LIT_TARGETS = registerTag(Registry.BLOCK_KEY, "fire_circle_lit_targets");
        FLAGS = registerTag(Registry.BLOCK_KEY, "flags");
        GOOP = registerTag(Registry.BLOCK_KEY, "goop");
        PIPE_BODIES = registerTag(Registry.BLOCK_KEY, "pipe_bodies");
        ROYALITE = registerTag(Registry.BLOCK_KEY, "royalite");
        VANILLATE = registerTag(Registry.BLOCK_KEY, "vanillate");
        WARP_PIPES = registerTag(Registry.BLOCK_KEY, "warp_pipes");
    }

    private static void registerEntityTags() {
        STOMP_IGNORED = registerTag(Registry.ENTITY_TYPE_KEY, "stomp_ignored");
        STOMP_IMMUNE = registerTag(Registry.ENTITY_TYPE_KEY, "stomp_immune");
    }

    private static void registerStructureTags() {
        GHOST_HOUSE = registerTag(Registry.STRUCTURE_KEY, "ghost_house");
        ON_ARENA_FINDER_MAP = registerTag(Registry.STRUCTURE_KEY, "on_arena_finder_map");
        ON_ISLAND_EXPLORER_MAP = registerTag(Registry.STRUCTURE_KEY, "on_island_explorer_map");
    }

    public static void register() {
        POISON = registerTag(Registry.FLUID_KEY, "poison");
        registerBiomeTags();
        registerBlockTags();
        registerEntityTags();
        registerStructureTags();
        SHELLMETS = registerTag(Registry.ITEM_KEY, "shellmets");
        STAR_BITS = registerTag(Registry.ITEM_KEY, "star_bits");
        PAINT = registerTag(Registry.ITEM_KEY, "paint");
    }
}
