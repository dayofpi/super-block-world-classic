package com.dayofpi.super_block_world.block.renderers;

import com.dayofpi.super_block_world.block.block_entities.PlacedItemBE;
import com.dayofpi.super_block_world.block.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class PlacedItemRenderer implements BlockEntityRenderer<PlacedItemBE> {
    public PlacedItemRenderer() {
    }

    @Override
    public void render(PlacedItemBE entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        BlockPos pos = entity.getPos();

        if (world != null) {
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            if (blockState.isOf(ModBlocks.POWER_STAR) || blockState.isOf(ModBlocks.ZTAR))
                light = 15728850;
            matrices.translate(0.5, 0.5, 0.5);
            matrices.scale(0.8f, 0.8f, 0.8f);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((world.getTime() + tickDelta) * 4));
            MinecraftClient.getInstance().getItemRenderer().renderItem(block.asItem().getDefaultStack(), ModelTransformation.Mode.GUI, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
        }
    }
}
