package com.dayofpi.super_block_world.client.renderers.other;

import com.dayofpi.super_block_world.common.blocks.block_entities.PlacedItemBE;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class PlacedItemBERenderer<T extends PlacedItemBE> implements BlockEntityRenderer<T> {
    public PlacedItemBERenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(PlacedItemBE entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light, int overlay) {
        World world = entity.getWorld();
        if (world != null) {
            matrices.translate(0.5, 0.5, 0.5);
            matrices.scale(0.8f, 0.8f, 0.8f);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((world.getTime() + tickDelta) * 4));
            MinecraftClient.getInstance().getItemRenderer().renderItem(world.getBlockState(entity.getPos()).getBlock().asItem().getDefaultStack(), ModelTransformation.Mode.GUI, light, OverlayTexture.DEFAULT_UV, matrices, provider, 0);
        }
    }
}
