package com.dayofpi.sbw_main.block.block_entity;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.registry.ModBlockEntities;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BooLanternBE extends BlockEntity {

    public BooLanternBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOO_LANTERN, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BooLanternBE be) {
        Box box = (new Box(pos)).expand(5);
        Random random = new Random();
        List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, box);
        if (state.isOf(ModBlocks.BOO_LANTERN) && state.get(Properties.LIT) != list.isEmpty() && be != null) {
            world.setBlockState(pos, state.with(Properties.LIT, list.isEmpty()));
            if (!state.get(Properties.WATERLOGGED))
                world.playSound(null, pos, state.get(Properties.LIT) ? ModSounds.BLOCK_BOO_LANTERN_HIDE : ModSounds.BLOCK_BOO_LANTERN_REVEAL, SoundCategory.BLOCKS, 0.7F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    // Serialize the BlockEntity
    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
    }

    // Deserialize the BlockEntity
    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
    }

}