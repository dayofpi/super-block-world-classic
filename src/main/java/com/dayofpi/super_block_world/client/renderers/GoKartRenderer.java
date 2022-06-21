package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.GoKartModel;
import com.dayofpi.super_block_world.client.registry.EntityClient;
import com.dayofpi.super_block_world.common.entities.misc.GoKartEntity;
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
import net.minecraft.util.math.Vec3f;

import java.util.OptionalInt;

public class GoKartRenderer extends EntityRenderer<GoKartEntity> {
    private final EntityModel<GoKartEntity> model;
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/go_kart/base.png");
    private static final Identifier FRAME = new Identifier(Main.MOD_ID, "textures/entity/go_kart/frame.png");

    public GoKartRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.7f;
        this.model = new GoKartModel<>(ctx.getPart(EntityClient.GO_KART));
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
    public void render(GoKartEntity goKartEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0, 1.5, 0.0);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - f));

        float wobbleTicks = (float)goKartEntity.getDamageWobbleTicks() - g;
        float strength = goKartEntity.getDamageWobbleStrength() - g;
        if (strength < 0.0f) {
            strength = 0.0f;
        }
        if (wobbleTicks > 0.0f) {
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(wobbleTicks) * wobbleTicks * strength / 10.0f * (float)goKartEntity.getDamageWobbleSide()));
        }

        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        this.model.setAngles(goKartEntity, g, 0.0f, -0.1f, 0.0f, 0.0f);

        VertexConsumer base = vertexConsumerProvider.getBuffer(this.model.getLayer(getTexture(goKartEntity)));
        this.model.render(matrixStack, base, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

        float[] color = goKartEntity.getKartColor().getColorComponents();
        VertexConsumer frame = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(FRAME));
        this.model.render(matrixStack, frame, i, OverlayTexture.DEFAULT_UV, color[0], color[1], color[2], 1.0f);

        OptionalInt flagType = goKartEntity.getFlagType();
        if (flagType.isPresent()) {
            VertexConsumer flag = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(getFlagTexture(flagType.getAsInt())));
            this.model.render(matrixStack, flag, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }

        matrixStack.pop();
        super.render(goKartEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(GoKartEntity entity) {
        return TEXTURE;
    }
}
