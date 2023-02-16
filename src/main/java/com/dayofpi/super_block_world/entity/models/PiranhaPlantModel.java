package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.entity.entities.hostile.PiranhaPlantEntity;
import com.dayofpi.super_block_world.entity.entities.hostile.PutridPiranhaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class PiranhaPlantModel extends SinglePartEntityModel<PiranhaPlantEntity> {
    private final ModelPart root;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart rightLeaf;
    private final ModelPart leftLeaf;

    public PiranhaPlantModel(ModelPart root) {
        this.root = root.getChild("root");
        this.neck = this.root.getChild("neck");
        this.head = this.neck.getChild("head");
        this.rightLeaf = this.root.getChild("right_leaf");
        this.leftLeaf = this.root.getChild("left_leaf");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(24, 17).cuboid(-2.5F, 10.0F, -2.5F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

        ModelPartData neck = root.addChild("neck", ModelPartBuilder.create().uv(28, 24).cuboid(-1.5F, -7.5F, -1.5F, 3.0F, 9.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.5F, 0.0F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -10.5F, 0.0F));

        head.addChild("mouth1", ModelPartBuilder.create().uv(0, 8).cuboid(-5.0F, -10.0F, 0.0F, 10.0F, 10.0F, 4.0F, new Dilation(0.0F))
                .uv(26, 0).cuboid(-5.0F, -9.0F, -1.0F, 10.0F, 10.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        head.addChild("mouth2", ModelPartBuilder.create().uv(0, 22).cuboid(-5.0F, -10.0F, -4.0F, 10.0F, 10.0F, 4.0F, new Dilation(0.0F))
                .uv(26, 0).cuboid(-5.0F, -9.0F, 0.0F, 10.0F, 10.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        root.addChild("right_leaf", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-8.5F, 0.0F, -4.5F, 9.0F, 0.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-1.5F, 10.0F, 0.0F));

        root.addChild("left_leaf", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, 0.0F, -3.5F, 9.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 10.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(PiranhaPlantEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(entity.bitingAnimationState, ModAnimations.PiranhaPlant.BITE, animationProgress);
        if (entity instanceof PutridPiranhaEntity) {
            this.updateAnimation(((PutridPiranhaEntity) entity).poisoningAnimationState, ModAnimations.PiranhaPlant.POISON, animationProgress);
        }

        float progress = animationProgress * 5F * 0.04F;
        leftLeaf.roll = -0.2F - (MathHelper.cos(progress * 0.5F) * 0.05F);
        rightLeaf.roll = 0.2F + (MathHelper.cos(progress * 0.5F) * 0.05F);
        neck.roll = MathHelper.cos(progress * 0.07F) * 0.1F;
        float neckPitch = 0.0F;
        if (entity.isAttacking()) {
            neckPitch = 0.8F;
            neck.pitch = (neckPitch * 0.8F);
            neck.roll = 0;
        }
        head.pitch = neckPitch;
        head.yaw = headYaw * ((float) Math.PI / 180F);
    }
}
