package com.dayofpi.super_block_world.client.renderers.other;

import com.dayofpi.super_block_world.client.models.DryBonesPileModel;
import com.dayofpi.super_block_world.common.blocks.DryBonesPileBlock;
import com.dayofpi.super_block_world.common.blocks.block_entities.DryBonesPileBE;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class DryBonesPileBERenderer<T extends DryBonesPileBE> extends GeoBlockRenderer<T> {
    public DryBonesPileBERenderer() {
        super(new DryBonesPileModel<>());
    }

    @Override
    public RenderLayer getRenderType(T animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(GeoModel model, T animatable, float partialTicks, RenderLayer type, MatrixStack matrices, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrices.push();
        World world  = animatable.getWorld();
        if (world != null) {
        BlockState blockState = world.getBlockState(animatable.getPos());
            if (blockState.isOf(BlockInit.DRY_BONES_PILE)) {
                float rot = 22.5f + (float) blockState.get(DryBonesPileBlock.ROTATION);
                GeoBone root = model.topLevelBones.get(0);
                root.setRotationY(rot);
            }
        }
        matrices.pop();
        super.render(model, animatable, partialTicks, type, matrices, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);

    }
}
