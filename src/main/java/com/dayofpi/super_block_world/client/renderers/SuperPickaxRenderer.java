package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.SuperPickaxModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.misc.SuperPickaxEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class SuperPickaxRenderer extends EntityRenderer<SuperPickaxEntity> {
    private final EntityModel<SuperPickaxEntity> model;
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/super_pickax.png");

    public SuperPickaxRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.4f;
        this.model = new SuperPickaxModel(ctx.getPart(ModModelLayers.SUPER_PICKAX));
    }

    @Override
    public void render(SuperPickaxEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light) {
        VertexConsumer consumer = provider.getBuffer(this.model.getLayer(getTexture(entity)));

        matrices.push();
        matrices.translate(0.0, 1.5, 0.0);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - yaw));
        matrices.scale(-1.0f, -1.0f, 1.0f);

        this.model.setAngles(entity, tickDelta, 0.0f, -0.1f, 0.0f, 0.0f);
        this.model.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, provider, light);
    }

    @Override
    public Identifier getTexture(SuperPickaxEntity entity) {
        return TEXTURE;
    }
}
