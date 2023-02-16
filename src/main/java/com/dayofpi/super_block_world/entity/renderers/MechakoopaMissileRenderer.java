package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.MechakoopaMissileModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.projectile.MechakoopaMissileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class MechakoopaMissileRenderer extends EntityRenderer<MechakoopaMissileEntity> {
    private final EntityModel<MechakoopaMissileEntity> model;
    public MechakoopaMissileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new MechakoopaMissileModel(ctx.getPart(ModModelLayers.MECHAKOOPA_MISSILE));
    }

    @Override
    public Identifier getTexture(MechakoopaMissileEntity object) {
        return new Identifier(Main.MOD_ID, "textures/entity/mechakoopa/missile.png");
    }

    @Override
    public void render(MechakoopaMissileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0.0, 1.5, 0.0);
        matrices.scale(-1.0f, -1.0f, 1.0f);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - yaw));
        VertexConsumer vertices = vertexConsumers.getBuffer(this.model.getLayer(getTexture(entity)));
        this.model.render(matrices, vertices, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
