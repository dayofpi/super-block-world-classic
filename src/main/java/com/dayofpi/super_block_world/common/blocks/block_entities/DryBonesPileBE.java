package com.dayofpi.super_block_world.common.blocks.block_entities;

import com.dayofpi.super_block_world.common.blocks.DryBonesPileBlock;
import com.dayofpi.super_block_world.common.entities.mob.DryBonesEntity;
import com.dayofpi.super_block_world.registry.main.BlockEntityInit;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DryBonesPileBE extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static int alarm;
    private static final int maxAlarm = 50;

    public DryBonesPileBE(BlockPos pos, BlockState state) {
        super(BlockEntityInit.DRY_BONES_PILE, pos, state);
        alarm = 0;
    }

    public static void tick(World world, BlockPos pos, BlockState state, DryBonesPileBE be) {
        if (state.get(DryBonesPileBlock.ALIVE)) {
            if (alarm < maxAlarm)
                ++alarm;
            if (alarm == maxAlarm) {
                int rotation = state.get(DryBonesPileBlock.ROTATION);
                DryBonesEntity dryBonesEntity = EntityInit.DRY_BONES.create(world);
                if (dryBonesEntity != null && world != null) {
                    dryBonesEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
                    world.spawnEntity(dryBonesEntity);
                    world.removeBlock(pos, false);
                    world.removeBlockEntity(pos);
                }
            }
        }
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (world != null && world.getBlockState(pos).isOf(BlockInit.DRY_BONES_PILE) && world.getBlockState(pos).get(DryBonesPileBlock.ALIVE)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("wakeup", true));
            return PlayState.CONTINUE;
        } event.getController().setAnimation(new AnimationBuilder().addAnimation("dead", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
