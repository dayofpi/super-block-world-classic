package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SkeletonKeyItem extends Item {
    public SkeletonKeyItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Random random = world.getRandom();
        Vec3d vec3d = context.getHitPos();

        boolean successful = true;
        for (BlockPos blockPos : BlockPos.iterateOutwards(new BlockPos(vec3d), 5, 5, 5)) {
            if (world.getBlockState(blockPos).isOf(ModBlocks.BOWSER_LOCK)) {
                if (successful)
                    world.playSound(null, blockPos, Sounds.BLOCK_BOWSER_LOCK_OPEN, SoundCategory.BLOCKS, 0.8F, 1.0F);
                successful = false;
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                if (world instanceof ServerWorld)
                    for (int i = 0; i < 4; ++i) {
                        ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, blockPos.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                    }
            }
        }
        return successful ? ActionResult.PASS : ActionResult.success(world.isClient);
    }
}
