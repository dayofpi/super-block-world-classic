package com.dayofpi.sbw_main;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.entity.registry.ModEffects;
import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.item.registry.ModItems;
import com.dayofpi.sbw_main.misc.DispenserBehaviors;
import com.dayofpi.sbw_main.world.registry.*;
import net.fabricmc.api.ModInitializer;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
    public static final String MOD_ID = "super_block_world";
    public static final Identifier DIMENSION_ID = new Identifier(MOD_ID, "mushroom_kingdom");

    public static final BlockSoundGroup TOADSTONE = new BlockSoundGroup(1.0F, 1.0F, ModSounds.BLOCK_TOADSTONE_BREAK, ModSounds.BLOCK_TOADSTONE_STEP, ModSounds.BLOCK_TOADSTONE_PLACE, ModSounds.BLOCK_TOADSTONE_HIT, ModSounds.BLOCK_TOADSTONE_FALL);
    public static final BlockSoundGroup GRASSY_STONE = new BlockSoundGroup(1.0F, 1.0F, ModSounds.BLOCK_GRASSY_STONE_BREAK, ModSounds.BLOCK_GRASSY_STONE_STEP, ModSounds.BLOCK_GRASSY_STONE_PLACE, ModSounds.BLOCK_GRASSY_STONE_HIT, ModSounds.BLOCK_GRASSY_STONE_FALL);
    public static final BlockSoundGroup FROSTED_STONE = new BlockSoundGroup(1.0F, 1.0F, ModSounds.BLOCK_FROSTED_STONE_BREAK, ModSounds.BLOCK_FROSTED_STONE_STEP, ModSounds.BLOCK_FROSTED_STONE_PLACE, ModSounds.BLOCK_FROSTED_STONE_HIT, ModSounds.BLOCK_FROSTED_STONE_FALL);

    public static final MusicSound GRASSLAND = new MusicSound(ModSounds.MUSIC_GRASSLAND, 6000, 24000, false);
    public static final MusicSound CAVE = new MusicSound(ModSounds.MUSIC_CAVE, 12000, 24000, false);

    @Override
    public void onInitialize() {
        ModBlocks.registerBlocks();
        ModEntities.registerEntities();
        ModItems.registerItems();
        ModEffects.registerEffects();
        ModFluids.registerFluids();
        ModFeatureTypes.registerFeatureTypes();
        ModFeatures.registerFeatures();
        TagList.registerTags();
        ModStructures.registerStructures();
        ModParticles.registerParticles();
        SoundList.registerSounds();
        ModSounds.registerSounds();
        DispenserBehaviors.addDispenserBehaviors();
        CustomPortalBuilder.beginPortal()
                .frameBlock(ModBlocks.WARP_FRAME)
                .tintColor(188, 112, 255).destDimID(DIMENSION_ID).registerPortal();

    }
}
