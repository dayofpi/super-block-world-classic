package com.dayofpi.super_block_world.client.model;

import com.dayofpi.super_block_world.main.common.entity.mob.StingbyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class StingbyModel<T extends StingbyEntity> extends SinglePartEntityModel<T> {
    private final ModelPart ROOT;
    private final ModelPart RIGHT_LEG;
    private final ModelPart LEFT_LEG;
    private final ModelPart RIGHT_WING;
    private final ModelPart LEFT_WING;

    public StingbyModel(ModelPart root) {
        this.ROOT = root.getChild("body");
        this.RIGHT_LEG = ROOT.getChild("right_leg");
        this.LEFT_LEG = ROOT.getChild("left_leg");
        this.RIGHT_WING = ROOT.getChild("right_wing");
        this.LEFT_WING = ROOT.getChild("left_wing");
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData rootData = modelData.getRoot();
        ModelPartData main = rootData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -9.5F, -3.0F, 9.0F, 9.0F, 10.0F).uv(0, 6).cuboid(-1.0F, -3.5F, -5.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 22.0F, -2.0F));

        main.addChild("left_leg", ModelPartBuilder.create().uv(0,0).cuboid(-1.0F, -0.7F, -0.7F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(3.0F, -1.0F, 3.0F));
        main.addChild("right_leg", ModelPartBuilder.create().uv(0,0).cuboid(-1.0F, -0.7F, -0.7F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(-3.0F, -1.0F, 3.0F));

        main.addChild("left_wing", ModelPartBuilder.create().uv(38, 7).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 6.0F), ModelTransform.pivot(-3.0F, -9.0F, 4.0F));
        main.addChild("right_wing", ModelPartBuilder.create().uv(38, 7).cuboid(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 6.0F), ModelTransform.pivot(3.0F, -9.0F, 4.0F));
        return modelData;
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return ROOT;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float angryMode = 0F;
        if (entity.getTarget() != null) {
            angryMode = 0.4F;
        }
        float l = animationProgress * 5F * 0.017453292F;

        this.ROOT.pitch = MathHelper.cos(l) * 0.2F + headPitch * 0.017453292F + angryMode;

        float wingMotion = animationProgress * 5F * 0.15F;
        float legMotion = animationProgress * 5F * 0.028F;
        this.RIGHT_WING.setAngles(0F,0F, MathHelper.cos(wingMotion) + 0.8F);
        this.LEFT_WING.setAngles(0F,0F, (MathHelper.cos(wingMotion) + 0.8F) * -1);
        this.RIGHT_LEG.setAngles(MathHelper.cos(legMotion) * 0.2F + 0.5F,0F, 0F);
        this.LEFT_LEG.setAngles(MathHelper.cos(legMotion) * 0.2F + 0.5F,0F, 0F);
    }
}
