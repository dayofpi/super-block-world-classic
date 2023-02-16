package com.dayofpi.super_block_world.block.block_entities;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.ModBlockEntities;
import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class BooLanternBE extends BlockEntity {

    public BooLanternBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOO_LANTERN, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BooLanternBE be) {
        Box box = (new Box(pos)).expand(5);
        List<PlayerEntity> players = world.getNonSpectatingEntities(PlayerEntity.class, box);
        if (state.isOf(ModBlocks.BOO_LANTERN) && state.get(Properties.LIT) != players.isEmpty() && be != null) {
            if (!world.isClient) {
                world.setBlockState(pos, state.with(Properties.LIT, players.isEmpty()));
                PlayerEntity player = world.getClosestEntity(players, TargetPredicate.DEFAULT, null, pos.getX(), pos.getY(), pos.getZ());
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            }
            if (!state.get(Properties.WATERLOGGED)) {
                Random random = world.getRandom();
                world.playSound(null, pos, state.get(Properties.LIT) ? Sounds.BLOCK_BOO_LANTERN_HIDE : Sounds.BLOCK_BOO_LANTERN_REVEAL, SoundCategory.BLOCKS, 0.6F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            }
        }
    }
}
