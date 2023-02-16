package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.entity.entities.hostile.GoombaEntity;
import com.dayofpi.super_block_world.entity.entities.passive.GladGoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class GoombaModel<T extends MobEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    public GoombaModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
        this.leftWing = this.root.getChild("left_wing");
        this.rightWing = this.root.getChild("right_wing");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(20, 18).cuboid(-2.5F, -5.0F, -2.5F, 5.0F, 3.0F, 5.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F), ModelTransform.pivot(0.0F, -5.5F, 0.0F));
        root.addChild("left_foot", ModelPartBuilder.create().uv(0, 18).cuboid(1.0F, 1.0F, -3.0F, 4.0F, 2.0F, 6.0F), ModelTransform.pivot(0.0F, -3.0F, 0.0F));
        root.addChild("right_foot", ModelPartBuilder.create().uv(0, 18).cuboid(-5.0F, 1.0F, -3.0F, 4.0F, 2.0F, 6.0F), ModelTransform.pivot(0.0F, -3.0F, 0.0F));
        root.addChild("left_wing", ModelPartBuilder.create().uv(40, 9).cuboid(-1.0F, -9.0F, 0.5F, 2.0F, 10.0F, 7.0F), ModelTransform.pivot(2.5F, -8.0F, 3.5F));
        root.addChild("right_wing", ModelPartBuilder.create().uv(40, 9).cuboid(-1.0F, -9.0F, 0.5F, 2.0F, 10.0F, 7.0F), ModelTransform.pivot(-2.5F, -8.0F, 3.5F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        boolean isWinged = entity instanceof Flutterer;

        if (entity instanceof GoombaEntity) {
            this.updateAnimation(((GoombaEntity) entity).walkingAnimationState, isWinged ? ModAnimations.Goomba.FLY : ModAnimations.Goomba.WALK, animationProgress);
        }
        else if (entity instanceof GladGoombaEntity) {
            this.updateAnimation(((GladGoombaEntity) entity).walkingAnimationState, isWinged ? ModAnimations.Goomba.FLY : ModAnimations.Goomba.WALK, animationProgress);
            this.updateAnimation(((GladGoombaEntity) entity).sittingAnimationState, ModAnimations.Goomba.SIT, animationProgress);
        }
        float progress = animationProgress * 5F * 0.017453292F;
        this.rightWing.pitch = MathHelper.cos(progress) * 0.2F - 0.1F;
        this.leftWing.pitch = MathHelper.cos(progress) * 0.2F - 0.1F;
        this.leftWing.visible = isWinged;
        this.rightWing.visible = isWinged;

        if (entity.getHealth() > 0) {
            this.head.pitch = headPitch * ((float) Math.PI / 180F);
            this.head.yaw = headYaw * ((float) Math.PI / 180F);
        } else {
            this.head.pitch = 0;
            this.head.yaw = 0;
        }
    }
}
