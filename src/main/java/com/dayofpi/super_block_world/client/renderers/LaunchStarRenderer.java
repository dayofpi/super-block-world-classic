package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.LaunchStarModel;
import com.dayofpi.super_block_world.common.entities.misc.LaunchStarEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import java.util.Collections;

public class LaunchStarRenderer<T extends LaunchStarEntity> extends GeoProjectilesRenderer<T> {
    public LaunchStarRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LaunchStarModel<>());
    }

    @Override
    public Identifier getTexture(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/launch_star.png");
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrices, VertexConsumerProvider bufferIn, int packedLightIn) {
        GeoModel model = this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelResource(entity));
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(entity.getPitch()));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - entity.getYaw()));
        matrices.translate(0.0, -0.5, 0.5);
        MinecraftClient.getInstance().getTextureManager().bindTexture(getTexture(entity));
        Color renderColor = getRenderColor(entity, partialTicks, matrices, bufferIn, null, packedLightIn);
        RenderLayer renderType = getRenderType(entity, partialTicks, matrices, bufferIn, null, packedLightIn, getTexture(entity));
        render(model, entity, partialTicks, renderType, matrices, bufferIn, null, packedLightIn, getPackedOverlay(entity, 0), (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);

        float lastLimbDistance = 0.0F;
        float limbSwing = 0.0F;
        EntityModelData entityModelData = new EntityModelData();
        AnimationEvent<T> predicate = new AnimationEvent<>(entity, limbSwing, lastLimbDistance, partialTicks, false, Collections.singletonList(entityModelData));
        if (this.getGeoModelProvider() instanceof IAnimatableModel) {
            ((IAnimatableModel<T>) this.getGeoModelProvider()).setLivingAnimations(entity, this.getUniqueID(entity), predicate);
        }
        matrices.pop();
    }
}
