package com.dayofpi.super_block_world.registry;

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
    public static TagKey<Biome> MUSHROOM_KINGDOM;
    public static TagKey<Biome> SURFACE_GOOMBA_SPAWN;
    public static TagKey<Biome> SURFACE_SHY_GUY_SPAWN;
    public static TagKey<Block> FLAGS;
    public static TagKey<Block> PIPE_BODIES;
    public static TagKey<Block> ROYALITE;
    public static TagKey<Block> VANILLATE;
    public static TagKey<Block> WARP_PIPES;
    public static TagKey<EntityType<?>> POISON_IMMUNE;
    public static TagKey<EntityType<?>> STOMP_IGNORED;
    public static TagKey<EntityType<?>> STOMP_IMMUNE;
    public static TagKey<Fluid> POISON;
    public static TagKey<Item> FLAG_ITEMS;
    public static TagKey<Item> SHELLMETS;
    public static TagKey<Item> STAR_BITS;
    public static TagKey<Structure> BOSS_ARENAS;
    public static TagKey<Structure> GHOST_HOUSE;
    public static TagKey<Structure> ON_YOSHIS_ISLAND_MAP;

    private static <T> TagKey<T> registerTag(RegistryKey<? extends Registry<T>> registry, String id) {
        return TagKey.of(registry, new Identifier(Main.MOD_ID, id));
    }

    public static void register() {
        BOSS_ARENAS = registerTag(Registry.STRUCTURE_KEY, "boss_arenas");
        FLAGS = registerTag(Registry.BLOCK_KEY, "flags");
        FLAG_ITEMS = registerTag(Registry.ITEM_KEY, "flags");
        GHOST_HOUSE = registerTag(Registry.STRUCTURE_KEY, "ghost_house");
        MUSHROOM_KINGDOM = registerTag(Registry.BIOME_KEY, "mushroom_kingdom");
        ON_YOSHIS_ISLAND_MAP = registerTag(Registry.STRUCTURE_KEY, "on_yoshis_island_map");
        PIPE_BODIES = registerTag(Registry.BLOCK_KEY, "pipe_bodies");
        POISON = registerTag(Registry.FLUID_KEY, "poison");
        POISON_IMMUNE = registerTag(Registry.ENTITY_TYPE_KEY, "poison_immune");
        ROYALITE = registerTag(Registry.BLOCK_KEY, "royalite");
        SHELLMETS = registerTag(Registry.ITEM_KEY, "shellmets");
        STAR_BITS = registerTag(Registry.ITEM_KEY, "star_bits");
        STOMP_IGNORED = registerTag(Registry.ENTITY_TYPE_KEY, "stomp_ignored");
        STOMP_IMMUNE = registerTag(Registry.ENTITY_TYPE_KEY, "stomp_immune");
        SURFACE_GOOMBA_SPAWN = registerTag(Registry.BIOME_KEY, "surface_goomba_spawn");
        SURFACE_SHY_GUY_SPAWN = registerTag(Registry.BIOME_KEY, "surface_shy_guy_spawn");
        VANILLATE = registerTag(Registry.BLOCK_KEY, "vanillate");
        WARP_PIPES = registerTag(Registry.BLOCK_KEY, "warp_pipes");
    }
}
