package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.misc.GoKartEntity;
import com.dayofpi.super_block_world.entity.models.GoKartModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

import java.util.OptionalInt;

public class GoKartRenderer extends EntityRenderer<GoKartEntity> {
    private final EntityModel<GoKartEntity> model;
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/go_kart/base.png");
    private static final Identifier FRAME = new Identifier(Main.MOD_ID, "textures/entity/go_kart/frame.png");

    public GoKartRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.7f;
        this.model = new GoKartModel<>(ctx.getPart(ModModelLayers.GO_KART));
    }

    private Identifier getFlagTexture(int i) {
        String flag = "rainbow";
        if (i < 16)
            flag = DyeColor.byId(i).getName();
        if (i == 17)
            flag = "trans";
        if (i == 18)
            flag = "bi";
        if (i == 19)
            flag = "lesbian";
        return new Identifier(Main.MOD_ID, "textures/entity/go_kart/flag_" + flag + ".png");
    }

    @Override
    public void render(GoKartEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light) {
        matrices.push();
        matrices.translate(0.0, 1.5, 0.0);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - yaw));

        float wobbleTicks = (float)entity.getDamageWobbleTicks() - tickDelta;
        float wobbleStrength = entity.getDamageWobbleStrength() - tickDelta;

        if (wobbleStrength < 0.0f) {
            wobbleStrength = 0.0f;
        }
        if (wobbleTicks > 0.0f) {
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.sin(wobbleTicks) * wobbleTicks * wobbleStrength / 10.0f * (float)entity.getDamageWobbleSide()));
        }

        matrices.scale(-1.0f, -1.0f, 1.0f);
        this.model.setAngles(entity, tickDelta, 0.0f, -0.1f, 0.0f, 0.0f);

        this.renderKart(entity, matrices, provider, light);
        this.renderFrame(entity, matrices, provider, light);
        this.renderFlag(entity, matrices, provider, light);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, provider, light);
    }

    private void renderKart(GoKartEntity entity, MatrixStack matrices, VertexConsumerProvider provider, int light){
        VertexConsumer vertices = provider.getBuffer(this.model.getLayer(getTexture(entity)));
        this.model.render(matrices, vertices, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderFrame(GoKartEntity entity, MatrixStack matrices, VertexConsumerProvider provider, int light) {
        float[] color = entity.getKartColor().getColorComponents();
        VertexConsumer vertices = provider.getBuffer(RenderLayer.getEntityCutoutNoCull(FRAME));
        this.model.render(matrices, vertices, light, OverlayTexture.DEFAULT_UV, color[0], color[1], color[2], 1.0f);
    }

    private void renderFlag(GoKartEntity entity, MatrixStack matrixStack, VertexConsumerProvider provider, int light) {
        OptionalInt flagType = entity.getFlagType();
        if (flagType.isPresent()) {
            int i = flagType.getAsInt();
            VertexConsumer vertices = provider.getBuffer(RenderLayer.getEntityCutoutNoCull(getFlagTexture(i)));
            this.model.render(matrixStack, vertices, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public Identifier getTexture(GoKartEntity entity) {
        return TEXTURE;
    }
}
