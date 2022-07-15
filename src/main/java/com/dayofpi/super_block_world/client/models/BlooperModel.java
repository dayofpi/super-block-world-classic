package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.hostile.BlooperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BlooperModel extends SinglePartEntityModel<BlooperEntity> {
    private final ModelPart root;
    private final ModelPart limbs;
    private final ModelPart right_tentacle;
    private final ModelPart left_tentacle;

    public BlooperModel(ModelPart root) {
        this.root = root.getChild("root");
        this.limbs = this.root.getChild("limbs");
        this.right_tentacle = this.limbs.getChild("right_tentacle");
        this.left_tentacle = this.limbs.getChild("left_tentacle");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -12.0F, -4.0F, 10.0F, 12.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 20).cuboid(-7.0F, -15.0F, -1.0F, 14.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

        ModelPartData limbs = root.addChild("limbs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        limbs.addChild("right_tentacle", ModelPartBuilder.create().uv(32, 16).cuboid(-1.0F, 0.0F, -2.0F, 2.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 0.0F, 0.0F));
        limbs.addChild("left_tentacle", ModelPartBuilder.create().uv(32, 16).cuboid(-1.0F, 0.0F, -2.0F, 2.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.0F, 0.0F));
        limbs.addChild("right_front_arm", ModelPartBuilder.create().uv(29, 31).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 1.0F, -2.5F));
        limbs.addChild("left_front_arm", ModelPartBuilder.create().uv(29, 31).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 1.0F, -2.5F));
        limbs.addChild("right_hind_arm", ModelPartBuilder.create().uv(29, 31).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 1.0F, 2.5F));
        limbs.addChild("left_hind_arm", ModelPartBuilder.create().uv(29, 31).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 1.0F, 2.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }



    @Override
    public void setAngles(BlooperEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.right_tentacle.roll = MathHelper.lerp(animationProgress, entity.prevTentacleAngle, entity.tentacleAngle);
        this.left_tentacle.roll = MathHelper.lerp(animationProgress, entity.prevTentacleAngle, entity.tentacleAngle) * -1;
        for (ModelPart modelPart : this.limbs.traverse().toList()) {
            modelPart.pitch = animationProgress;
        }
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}