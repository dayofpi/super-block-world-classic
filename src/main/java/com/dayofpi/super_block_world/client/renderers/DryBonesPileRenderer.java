package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.common.block_entities.DryBonesPileBE;
import com.dayofpi.super_block_world.common.blocks.DryBonesPileBlock;
import com.dayofpi.super_block_world.common.entities.hostile.DryBonesEntity;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class DryBonesPileRenderer implements BlockEntityRenderer<DryBonesPileBE> {
    private final EntityRenderDispatcher entityRenderDispatcher;
    public DryBonesPileRenderer(BlockEntityRendererFactory.Context ctx) {
        this.entityRenderDispatcher = ctx.getEntityRenderDIspatcher();
    }

    @Override
    public void render(DryBonesPileBE blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5, 0.0, 0.5);
        World world = blockEntity.getWorld();
        DryBonesEntity entity = ModEntities.DRY_BONES.create(world);
        if (entity != null && world != null) {
            entity.blockEntityContext = true;
            if (world.getBlockState(blockEntity.getPos()).isOf(ModBlocks.DRY_BONES_PILE) && world.getBlockState(blockEntity.getPos()).get(DryBonesPileBlock.ALIVE)) {
                entity.wakingUpAnimationState.startIfNotRunning(entity.age);
            } else {
                entity.wakingUpAnimationState.stop();
            }
            BlockState blockState = blockEntity.getCachedState();
            float h = 22.5f * (float) blockState.get(DryBonesPileBlock.ROTATION);
            entity.setHeadYaw(0.0f);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
            matrices.translate(0.0, -0.2f, 0.0);
            this.entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, tickDelta, matrices, vertexConsumers, light);
        }
        matrices.pop();
    }
}
