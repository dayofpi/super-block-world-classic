package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.block.blocks.TrampolineBlock;
import com.dayofpi.super_block_world.entity.entities.misc.TrampolineMinecartEntity;
import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MinecartEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class TrampolineMinecartRenderer extends MinecartEntityRenderer<TrampolineMinecartEntity> {
    public TrampolineMinecartRenderer(EntityRendererFactory.Context context) {
        super(context, ModModelLayers.TRAMPOLINE_MINECART);
    }

    protected void renderBlock(TrampolineMinecartEntity entity, float delta, BlockState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.isSquashed())
            state = ModBlocks.TRAMPOLINE.getDefaultState().with(TrampolineBlock.SQUASHED, true);
        super.renderBlock(entity, delta, state, matrices, vertexConsumers, light);
    }
}
