package com.dayofpi.sbw_main.entity.goals;

import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.entity.types.mobs.ParagoombaEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Direction;

import java.util.EnumSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SpawnMiniGoombaGoal extends Goal {
    Timer timer = new Timer();
    private final ParagoombaEntity paragoomba;

    public SpawnMiniGoombaGoal(ParagoombaEntity paragoomba) {
        this.paragoomba = paragoomba;
        this.setControls(EnumSet.of(Control.TARGET));
    }

    @Override
    public boolean canStart() {
        return paragoomba.isMother() && paragoomba.getTarget() != null && !paragoomba.isOnGround() && paragoomba.world.getBlockState(paragoomba.getBlockPos().down().down()).isSideSolidFullSquare(paragoomba.world, paragoomba.getBlockPos().down().down(), Direction.UP);
    }

    public void tick() {
        List<ParagoombaEntity> list = paragoomba.world.getEntitiesByClass(ParagoombaEntity.class, paragoomba.getBoundingBox().expand(32.0D, 32.0D, 32.0D), (entity) -> entity.isAlive() && entity.getSize() == 0);
        TimerTask summonBabies = new TimerTask() {
            ParagoombaEntity baby = ModEntities.PARAGOOMBA.create(paragoomba.world);

            @Override
            public void run() {
                baby.setSize(0);
                baby.setTarget(paragoomba.getTarget());
                baby.refreshPositionAndAngles(paragoomba.getX() + 0.5D, paragoomba.getY() + 0.5D, paragoomba.getZ() + 0.5D, 0.0F, 0.0F);

                paragoomba.world.spawnEntity(baby);
            }
        };

        if (list.size() < 5 && paragoomba.world.random.nextInt(15) == 0) {
            timer.schedule(summonBabies, 100);
        }
    }

    public boolean shouldContinue() {
        return this.canStart();
    }
}
