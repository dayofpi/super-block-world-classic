package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class ModBiome {
    private static final Biome MUSHROOM_GRASSLANDS = ModBiomeBuilder.createMushroomGrasslands();
    private static final Biome MOO_MOO_MEADOW = ModBiomeBuilder.createMushroomGrasslands();
    private static final Biome MUSHROOM_GORGE = ModBiomeBuilder.createMushroomGorge();
    private static final Biome FOSSIL_FALLS = ModBiomeBuilder.createMushroomGrasslands();
    private static final Biome DRY_DRY_DESERT = ModBiomeBuilder.createDryDryDesert();
    private static final Biome CHEEP_CHEEP_REEF = ModBiomeBuilder.createMushroomGrasslands();
    private static final Biome AMANITA_FOREST = ModBiomeBuilder.createAmanitaForest();
    private static final Biome AUTUMN_FOREST = ModBiomeBuilder.createMushroomGrasslands();
    private static final Biome FOREST_OF_ILLUSION = ModBiomeBuilder.createMushroomGrasslands();

    private static void registerBiome(String id, Biome type) {
        Registry.register(BuiltinRegistries.BIOME, RegistryKey.of(Registry.BIOME_KEY, new Identifier(Main.MOD_ID, id)), type);
    }

    public static void registerBiomes() {
        registerBiome("mushroom_grasslands", MUSHROOM_GRASSLANDS);
        registerBiome("moo_moo_meadow", MOO_MOO_MEADOW);
        registerBiome("mushroom_gorge", MUSHROOM_GORGE);
        registerBiome("fossil_falls", FOSSIL_FALLS);
        registerBiome("dry_dry_desert", DRY_DRY_DESERT);
        registerBiome("cheep_cheep_reef", CHEEP_CHEEP_REEF);
        registerBiome("amanita_forest", AMANITA_FOREST);
        registerBiome("autumn_forest", AUTUMN_FOREST);
        registerBiome("forest_of_illusion", FOREST_OF_ILLUSION);
    }
}
