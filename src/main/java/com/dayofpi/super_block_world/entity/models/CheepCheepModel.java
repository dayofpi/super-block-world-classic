package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.CheepCheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CheepCheepModel<T extends CheepCheepEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart tail;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart mouth;

    public CheepCheepModel(ModelPart root) {
        this.root = root.getChild(EntityModelPartNames.ROOT);
        this.tail = this.root.getChild(EntityModelPartNames.TAIL);
        this.rightWing = this.root.getChild(EntityModelPartNames.RIGHT_WING);
        this.leftWing = this.root.getChild(EntityModelPartNames.LEFT_WING);
        this.mouth = this.root.getChild(EntityModelPartNames.MOUTH);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        root.addChild(EntityModelPartNames.TOP_FIN, ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, -2.5F, 0.5F, 2.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.5F, -4.5F, -0.1309F, 0.0F, 0.0F));
        root.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create().uv(32, 16).cuboid(-1.0F, -3.0F, 0.0F, 2.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 3.0F));
        root.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(18, 16).mirrored().cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.5F, -4.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
        root.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(18, 16).cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(4.5F, -4.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
        root.addChild(EntityModelPartNames.MOUTH, ModelPartBuilder.create().uv(11, 16).cuboid(-2.0F, -1.5F, -1.5F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.5F, -4.5F));

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float progress = animationProgress * 5F * 0.07F;

        this.rightWing.yaw = MathHelper.cos(limbAngle * 0.5F) * 1.4F * limbDistance - 0.5F;
        this.leftWing.yaw = MathHelper.cos(limbAngle * 0.5F + 3.1415927F) * 1.4F * limbDistance + 0.5F;
        this.tail.yaw = MathHelper.cos(limbAngle * 0.6F) * 1.6F * limbDistance;
        this.mouth.xScale = 1 + (MathHelper.cos(progress) * 0.07F);
        this.mouth.yScale = 1 + (MathHelper.cos(progress) * 0.07F);
    }
}
