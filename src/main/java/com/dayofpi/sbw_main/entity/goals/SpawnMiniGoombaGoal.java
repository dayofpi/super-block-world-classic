package com.dayofpi.sbw_main.entity.goals;

import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.entity.types.mobs.ParagoombaEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.List;

public class SpawnMiniGoombaGoal extends Goal {
    private final ParagoombaEntity paragoomba;
    private final List<ParagoombaEntity> list;
    private int timer = 0;

    public SpawnMiniGoombaGoal(ParagoombaEntity paragoomba) {
        this.paragoomba = paragoomba;
        list = paragoomba.world.getEntitiesByClass(ParagoombaEntity.class, paragoomba.getBoundingBox().expand(32.0D, 32.0D, 32.0D), (entity) -> entity.isAlive() && entity.getSize() == 0);
        this.setControls(EnumSet.of(Control.TARGET));
    }

    @Override
    public boolean canStart() {
        return list.size() < 5 && paragoomba.isMother() && paragoomba.getTarget() != null && !paragoomba.isOnGround();
    }

    public void tick() {
        if (timer > 0) {
            --timer;
        } else if (timer == 0) {
            final ParagoombaEntity baby = ModEntities.PARAGOOMBA.create(paragoomba.world);
            if (baby != null) {
                baby.refreshPositionAndAngles(paragoomba.getX() + 0.5D, paragoomba.getY() + 1D, paragoomba.getZ() + 0.5D, 0.0F, 0.0F);
                baby.setSize(0);
                paragoomba.world.spawnEntity(baby);
                this.timer = 50;
            }
        } else timer = 0;
    }


    public boolean shouldContinue() {
        return this.canStart();
    }
}
