package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.blocks.block_entities.PlacedItemBE;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class PlacedItemBlock extends BlockWithEntity {
    public PlacedItemBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity.isPlayer()) {
            world.removeBlock(pos, false);
            world.addParticle(ParticleTypes.POOF, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            SoundEvent soundEvent = SoundEvents.ENTITY_ITEM_PICKUP;
            if (this.asItem() == ItemInit.COIN)
                soundEvent = SoundInit.ITEM_COLLECT_COIN;
            else if (this.asItem() == ItemInit.STAR_COIN)
                soundEvent = SoundInit.ITEM_COLLECT_STAR_COIN;
            entity.playSound(soundEvent, 0.5f, 1.0f);
            if (!((PlayerEntity)entity).isCreative()) {
                ((PlayerEntity) entity).giveItemStack(new ItemStack(this.asItem()));
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlacedItemBE(pos, state);
    }
}
