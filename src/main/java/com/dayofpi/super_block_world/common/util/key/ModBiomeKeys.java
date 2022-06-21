package com.dayofpi.super_block_world.common.util.key;

import com.dayofpi.super_block_world.Main;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class ModBiomeKeys {
    public static RegistryKey<Biome> MUSHROOM_GRASSLANDS;
    public static RegistryKey<Biome> MOO_MOO_MEADOW;
    public static RegistryKey<Biome> AMANITA_FOREST;
    public static RegistryKey<Biome> FOREST_OF_ILLUSION;
    public static RegistryKey<Biome> DRY_DRY_DESERT;
    public static RegistryKey<Biome> CHEEP_CHEEP_REEF;
    public static RegistryKey<Biome> AUTUMN_FOREST;
    public static RegistryKey<Biome> AUTUMN_MOUNTAIN;
    public static RegistryKey<Biome> MUSHROOM_GORGE;
    public static RegistryKey<Biome> TALL_TALL_MOUNTAIN;
    public static RegistryKey<Biome> FOSSIL_FALLS;
    public static RegistryKey<Biome> SHERBET_LAND;

    private static RegistryKey<Biome> registerKey(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(Main.MOD_ID, name));
    }

    public static void registerKeys() {
        MUSHROOM_GRASSLANDS = registerKey("mushroom_grasslands");
        MOO_MOO_MEADOW = registerKey("moo_moo_meadow");
        AMANITA_FOREST = registerKey("amanita_forest");
        FOREST_OF_ILLUSION = registerKey("forest_of_illusion");
        DRY_DRY_DESERT = registerKey("dry_dry_desert");
        CHEEP_CHEEP_REEF = registerKey("cheep_cheep_reef");
        AUTUMN_FOREST = registerKey("autumn_forest");
        AUTUMN_MOUNTAIN = registerKey("autumn_mountain");
        MUSHROOM_GORGE = registerKey("mushroom_gorge");
        TALL_TALL_MOUNTAIN = registerKey("tall_tall_mountain");
        FOSSIL_FALLS = registerKey("fossil_falls");
        SHERBET_LAND = registerKey("sherbet_land");
    }
}
