package com.dayofpi.super_block_world.block.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.block.block_entities.ChinchoTorchBE;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class ChinchoTorchRenderer implements BlockEntityRenderer<ChinchoTorchBE> {
    public static final SpriteIdentifier ACTIVE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/chincho_torch/active"));
    public static final SpriteIdentifier INACTIVE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/chincho_torch/inactive"));
    private final ModelPart root;
    private final ModelPart orb;

    public ChinchoTorchRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(ModModelLayers.CHINCHO_TORCH);
        this.root = modelPart.getChild("root");
        this.orb = modelPart.getChild("orb");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 8).cuboid(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData orb = modelPartData.addChild("orb", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -15.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData stars = orb.addChild("stars", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

        ModelPartData bone4 = stars.addChild("bone4", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -16.5F, 0.0F));
        bone4.addChild("cube_r1", ModelPartBuilder.create().uv(8, 8).cuboid(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        ModelPartData bone5 = stars.addChild("bone5", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -14.0F, 0.0F));
        bone5.addChild("cube_r2", ModelPartBuilder.create().uv(8, 8).cuboid(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData bone6 = stars.addChild("bone6", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -14.0F, 0.0F));
        bone6.addChild("cube_r3", ModelPartBuilder.create().uv(8, 8).cuboid(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        stars.addChild("bone3", ModelPartBuilder.create().uv(8, 8).cuboid(-2.5F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -14.0F, -2.5F));
        stars.addChild("bone2", ModelPartBuilder.create().uv(8, 8).cuboid(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -14.0F, -2.5F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(ChinchoTorchBE entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.isLit()) {
            VertexConsumer consumer = ACTIVE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityTranslucent);

            matrices.push();
            matrices.scale(-1.0F, -1.0F, 1.0F);
            matrices.translate(-0.5, -1.5, 0.5);

            this.root.render(matrices, consumer, light, overlay);

            matrices.translate(0.0, 1.5, 0.0);
            if (entity.getWorld() != null)
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getWorld().getTime()));
            this.orb.render(matrices, consumer, light, overlay);
            matrices.pop();
        } else {
            VertexConsumer consumer = INACTIVE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityTranslucent);

            matrices.push();
            matrices.scale(-1.0F, -1.0F, 1.0F);
            matrices.translate(-0.5, -1.5, 0.5);

            this.root.render(matrices, consumer, light, overlay);

            matrices.translate(0.0, 1.5, 0.0);
            this.orb.render(matrices, consumer, light, overlay);
            matrices.pop();
        }
    }
}
