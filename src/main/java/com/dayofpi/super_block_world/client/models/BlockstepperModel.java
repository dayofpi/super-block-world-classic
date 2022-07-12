package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.hostile.BlockstepperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BlockstepperModel extends SinglePartEntityModel<BlockstepperEntity> {
    private final ModelPart root;
    private final ModelPart key;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public BlockstepperModel(ModelPart root) {
        this.root = root.getChild(EntityModelPartNames.ROOT);
        this.key = this.root.getChild("key");
        this.rightLeg = this.root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftLeg = this.root.getChild(EntityModelPartNames.LEFT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -16.0F, -4.0F, 10.0F, 12.0F, 9.0F, new Dilation(0.0F)).uv(0, 21).cuboid(-5.0F, -12.0F, -7.0F, 10.0F, 2.0F, 3.0F, new Dilation(0.0F)).uv(12, 27).cuboid(-2.0F, -8.0F, -6.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)).uv(0, 6).cuboid(-1.0F, -7.0F, -5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, -1.0F));
        ModelPartData key = root.addChild("key", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -7.0F, 7.0F));
        key.addChild("key_part", ModelPartBuilder.create().uv(38, 13).cuboid(-7.0F, -8.0F, 3.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 3.0F, -3.0F, 0.0F, 1.5708F, 0.0F));
        root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, -0.25F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).uv(21, 21).cuboid(-2.5F, 3.0F, -2.25F, 4.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -5.0F, 0.25F));
        root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(21, 21).cuboid(-1.5F, 3.0F, -2.25F, 4.0F, 2.0F, 5.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-0.5F, 0.0F, -0.25F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -5.0F, 0.25F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(BlockstepperEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;

        key.roll = entity.getWindUp() * 0.2F;
    }
}
