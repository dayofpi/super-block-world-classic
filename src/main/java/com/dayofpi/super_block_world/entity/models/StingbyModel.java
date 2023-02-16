package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.StingbyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class StingbyModel<T extends StingbyEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public StingbyModel(ModelPart root) {
        this.root = root.getChild(EntityModelPartNames.BODY);
        this.rightLeg = this.root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftLeg = this.root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightWing = this.root.getChild("right_wing");
        this.leftWing = this.root.getChild("left_wing");
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData rootData = modelData.getRoot();
        ModelPartData body = rootData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -9.5F, -3.0F, 9.0F, 9.0F, 10.0F).uv(0, 6).cuboid(-1.0F, -3.5F, -5.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 22.0F, -2.0F));

        body.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0,0).cuboid(-1.0F, -0.7F, -0.7F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(3.0F, -1.0F, 3.0F));
        body.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0,0).cuboid(-1.0F, -0.7F, -0.7F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(-3.0F, -1.0F, 3.0F));

        body.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(38, 7).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 6.0F), ModelTransform.pivot(-3.0F, -9.0F, 4.0F));
        body.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(38, 7).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 6.0F), ModelTransform.pivot(3.0F, -9.0F, 4.0F));
        return modelData;
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float angryMode = 0F;
        if (entity.getTarget() != null) {
            angryMode = 0.4F;
        }
        float progress = animationProgress * 5F * 0.017453292F;

        this.root.pitch = MathHelper.cos(progress) * 0.2F + headPitch * 0.017453292F + angryMode;

        float wingMotion = animationProgress * 5F * 0.15F;
        float legMotion = animationProgress * 5F * 0.028F;
        this.rightWing.setAngles(0F,0F, MathHelper.cos(wingMotion) + 0.8F);
        this.leftWing.setAngles(0F,0F, (MathHelper.cos(wingMotion) + 0.8F) * -1);
        this.rightLeg.setAngles(MathHelper.cos(legMotion) * 0.2F + 0.5F,0F, 0F);
        this.leftLeg.setAngles(MathHelper.cos(legMotion) * 0.2F + 0.5F,0F, 0F);
    }
}
