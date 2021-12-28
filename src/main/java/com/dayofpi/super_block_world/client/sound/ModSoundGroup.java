package com.dayofpi.super_block_world.client.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class ModSoundGroup {
    public static final BlockSoundGroup TOADSTONE = new BlockSoundGroup(1.0F, 1.0F, ModSounds.BLOCK_TOADSTONE_BREAK, ModSounds.BLOCK_TOADSTONE_STEP, ModSounds.BLOCK_TOADSTONE_PLACE, ModSounds.BLOCK_TOADSTONE_HIT, ModSounds.BLOCK_TOADSTONE_FALL);
    public static final BlockSoundGroup GRASSY_TOADSTONE = new BlockSoundGroup(1.0F, 1.0F, ModSounds.BLOCK_GRASSY_STONE_BREAK, ModSounds.BLOCK_GRASSY_STONE_STEP, ModSounds.BLOCK_GRASSY_STONE_PLACE, ModSounds.BLOCK_GRASSY_STONE_HIT, ModSounds.BLOCK_GRASSY_STONE_FALL);
    public static final BlockSoundGroup HARDSTONE = new BlockSoundGroup(1.0F, 0.8F, ModSounds.BLOCK_TOADSTONE_BREAK, ModSounds.BLOCK_TOADSTONE_STEP, ModSounds.BLOCK_TOADSTONE_PLACE, ModSounds.BLOCK_TOADSTONE_HIT, ModSounds.BLOCK_TOADSTONE_FALL);
    public static final BlockSoundGroup FROSTED_STONE = new BlockSoundGroup(1.0F, 1.0F, ModSounds.BLOCK_FROSTED_STONE_BREAK, ModSounds.BLOCK_FROSTED_STONE_STEP, ModSounds.BLOCK_FROSTED_STONE_PLACE, ModSounds.BLOCK_FROSTED_STONE_HIT, ModSounds.BLOCK_FROSTED_STONE_FALL);
    public static final BlockSoundGroup SHOREGRASS = new BlockSoundGroup(0.9F, 1.0F, ModSounds.BLOCK_SHOREGRASS_BREAK, ModSounds.BLOCK_SHOREGRASS_STEP, ModSounds.BLOCK_SHOREGRASS_PLACE, ModSounds.BLOCK_SHOREGRASS_HIT, ModSounds.BLOCK_SHOREGRASS_FALL);
    public static final BlockSoundGroup SHERBET_SOIL = new BlockSoundGroup(0.9F, 1.0F, ModSounds.BLOCK_SHERBET_SOIL_BREAK, ModSounds.BLOCK_SHERBET_SOIL_STEP, ModSounds.BLOCK_SHERBET_SOIL_PLACE, ModSounds.BLOCK_SHERBET_SOIL_HIT, ModSounds.BLOCK_SHERBET_SOIL_FALL);
    public static final BlockSoundGroup SNOWY_SHERBET_SOIL = new BlockSoundGroup(0.9F, 1.0F, ModSounds.BLOCK_SHERBET_SOIL_BREAK, SoundEvents.BLOCK_SNOW_STEP, ModSounds.BLOCK_SHERBET_SOIL_PLACE, ModSounds.BLOCK_SHERBET_SOIL_HIT, SoundEvents.BLOCK_SNOW_FALL);
}
