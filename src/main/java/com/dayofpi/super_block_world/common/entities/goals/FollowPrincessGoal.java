package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.mob.ToadEntity;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;

import java.util.List;

public class FollowPrincessGoal extends Goal {
    private final ToadEntity toadEntity;
    private final World world;

    public FollowPrincessGoal(ToadEntity toadEntity) {
        this.toadEntity = toadEntity;
        world = toadEntity.world;
    }

    @Override
    public boolean canStart() {
        List<Entity> list = world.getOtherEntities(toadEntity, toadEntity.getBoundingBox().expand(6, 5, 6), entity -> entity instanceof LivingEntity && ((LivingEntity) entity).getEquippedStack(EquipmentSlot.HEAD).isOf(ItemInit.PRINCESS_CROWN));
        return !list.isEmpty();
    }

    @Override
    public void tick() {
        super.tick();
        List<Entity> list = world.getOtherEntities(toadEntity, toadEntity.getBoundingBox().expand(6, 5, 6), entity -> entity instanceof LivingEntity && ((LivingEntity) entity).getEquippedStack(EquipmentSlot.HEAD).isOf(ItemInit.PRINCESS_CROWN));
        if (!list.isEmpty())
            this.toadEntity.getNavigation().startMovingTo(list.get(0), 1.0D);
    }
}
