package com.dayofpi.super_block_world.world.biome;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.util.key.ModBiomeKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class BiomeInit {
    private static final Biome MUSHROOM_GRASSLANDS = BiomeBuilder.createMushroomGrasslands();
    private static final Biome MOO_MOO_MEADOW = BiomeBuilder.createMooMooMeadow();
    private static final Biome MUSHROOM_GORGE = BiomeBuilder.createMushroomGorge();
    private static final Biome TALL_TALL_MOUNTAIN = BiomeBuilder.createTallTallMountain();
    private static final Biome FOSSIL_FALLS = BiomeBuilder.createFossilFalls();
    private static final Biome DRY_DRY_DESERT = BiomeBuilder.createDryDryDesert();
    private static final Biome CHEEP_CHEEP_REEF = BiomeBuilder.createCheepCheepReef();
    private static final Biome AMANITA_FOREST = BiomeBuilder.createAmanitaForest();
    private static final Biome AUTUMN_FOREST = BiomeBuilder.createAutumnForest();
    private static final Biome AUTUMN_MOUNTAIN = BiomeBuilder.createAutumnMountain();
    private static final Biome FOREST_OF_ILLUSION = BiomeBuilder.createForestOfIllusion();
    private static final Biome SHERBET_LAND = BiomeBuilder.createSherbetLand();

    private static void registerBiome(String id, Biome type) {
        Registry.register(BuiltinRegistries.BIOME, RegistryKey.of(Registry.BIOME_KEY, new Identifier(Main.MOD_ID, id)), type);
    }

    public static void registerBiomes() {
        ModBiomeKeys.registerKeys();
        registerBiome("mushroom_grasslands", MUSHROOM_GRASSLANDS);
        registerBiome("moo_moo_meadow", MOO_MOO_MEADOW);
        registerBiome("mushroom_gorge", MUSHROOM_GORGE);
        registerBiome("tall_tall_mountain", TALL_TALL_MOUNTAIN);
        registerBiome("fossil_falls", FOSSIL_FALLS);
        registerBiome("dry_dry_desert", DRY_DRY_DESERT);
        registerBiome("cheep_cheep_reef", CHEEP_CHEEP_REEF);
        registerBiome("amanita_forest", AMANITA_FOREST);
        registerBiome("autumn_forest", AUTUMN_FOREST);
        registerBiome("autumn_mountain", AUTUMN_MOUNTAIN);
        registerBiome("forest_of_illusion", FOREST_OF_ILLUSION);
        registerBiome("sherbet_land", SHERBET_LAND);
    }
}
