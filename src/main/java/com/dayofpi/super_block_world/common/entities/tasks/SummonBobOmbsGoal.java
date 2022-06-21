package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.boss.KingBobOmbEntity;
import com.dayofpi.super_block_world.common.entities.hostile.BobOmbEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class SummonBobOmbsGoal extends Goal {
    private final KingBobOmbEntity kingBobOmbEntity;
    private int timer;
    @Nullable
    private LivingEntity target;

    public SummonBobOmbsGoal(KingBobOmbEntity kingBobOmbEntity) {
        this.kingBobOmbEntity = kingBobOmbEntity;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = kingBobOmbEntity.getTarget();
        List<Entity> list = this.kingBobOmbEntity.world.getOtherEntities(this.kingBobOmbEntity, this.kingBobOmbEntity.getBoundingBox().expand(8), entity -> entity instanceof BobOmbEntity);
        if (!list.isEmpty())
            return false;
        return livingEntity != null;
    }

    @Override
    public void stop() {
        this.kingBobOmbEntity.setSummoning(false);
    }

    @Override
    public void start() {
        timer = 50;
        this.kingBobOmbEntity.setLaughing(false);
        this.kingBobOmbEntity.setSummoning(true);
        this.target = this.kingBobOmbEntity.getTarget();
    }

    @Override
    public void tick() {
        this.kingBobOmbEntity.lookAtEntity(target, 30.0F, 30.0F);
        if (timer > 0)
            --timer;
        if (timer == 0) {
            timer = 40;
            this.summonBobOmbs();
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    private void summonBobOmbs() {
        World world = this.kingBobOmbEntity.world;
        BobOmbEntity bobOmbEntity = ModEntities.BOB_OMB.create(world);
        if (bobOmbEntity != null) {
            bobOmbEntity.updatePosition(kingBobOmbEntity.getX(), kingBobOmbEntity.getY() + 6, kingBobOmbEntity.getZ());
            bobOmbEntity.addVelocity(0.0D, 0.9D, 0.0D);
            bobOmbEntity.setTarget(this.target);
            world.spawnEntity(bobOmbEntity);
            kingBobOmbEntity.playSound(Sounds.ENTITY_BULLET_SHOOT, 1.0F, 1.0F);
        }
    }

    @Override
    public boolean shouldContinue() {
        if (this.target == null)
            return false;
        List<Entity> list = this.kingBobOmbEntity.world.getOtherEntities(this.kingBobOmbEntity, this.kingBobOmbEntity.getBoundingBox().expand(8), entity -> entity instanceof BobOmbEntity);
        return list.size() < 3 && this.kingBobOmbEntity.canTarget(this.target);
    }
}
