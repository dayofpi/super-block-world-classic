package com.dayofpi.sbw_mixin;

import com.dayofpi.sbw_main.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PathAwareEntity.class)
public abstract class UndeadPoisonSwimming extends MobEntity {
    public final SwimGoal swimGoal = new SwimGoal(this);

    protected UndeadPoisonSwimming(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tickMovement() {
        // This makes it so mobs that are immune to poison can swim in it
        boolean bl = this.getType().isIn(ModTags.POISON_IMMUNE);
        boolean bl2 = this.updateMovementInFluid(ModTags.POISON, 0.014D);
        if (bl) {
            if (bl2) {
                this.goalSelector.add(0, this.swimGoal);
            } else this.goalSelector.remove(this.swimGoal);
        }
        super.tickMovement();
    }
}

