package com.dayofpi.sbw_main.block.type;

import com.dayofpi.sbw_main.entity.type.bases.AbstractBuzzy;
import com.dayofpi.sbw_main.entity.type.mobs.ThwompEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BrickBlock extends Block {
    public BrickBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity instanceof ThwompEntity thwompEntity && ((ThwompEntity) entity).getStage() == 3)
            world.breakBlock(pos, false);
        else if (entity instanceof AbstractBuzzy && ((AbstractBuzzy) entity).isUpsideDown() && entity.fallDistance > 0)
            world.breakBlock(pos, false);
    }
}
