package com.dayofpi.super_block_world.main.client.model;

import com.dayofpi.super_block_world.main.common.entity.mob.BobOmbEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class BobOmbModel<T extends BobOmbEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart fuse;
    private final ModelPart key;
    private final ModelPart right_leg;
    private final ModelPart left_leg;

    public BobOmbModel(ModelPart root) {
        this.root = root;
        ModelPart body = root.getChild("body");
        this.fuse = body.getChild("fuse");
        this.key = root.getChild("key");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0)
        .cuboid(-4.0F, -12.0F, -3.0F, 8.0F, 8.0F, 7.0F).uv(16, 19).cuboid(-2.0F, -13.0F, -1.5F, 4.0F, 1.0F, 4.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        body.addChild("fuse", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -4.0F, -0.5F, 2.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, -12.0F, 0.0F));
        modelPartData.addChild("key", ModelPartBuilder.create().uv(0, 11).cuboid(0.0F, -4.0F, 1.0F, 0.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F, 16.0F, 3.0F));
        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(12, 24).cuboid(-1.0F, 2.5F, -2.25F, 2.0F, 2.0F, 4.0F).uv(0, 27).cuboid(-1.0F, 0.5F, -0.25F, 2.0F, 4.0F, 1.0F), ModelTransform.pivot(-2.0F, 19.5F, 0.25F));
        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(12, 24).cuboid(-1.0F, 2.5F, -2.25F, 2.0F, 2.0F, 4.0F).uv(0, 27).cuboid(-1.0F, 0.5F, -0.25F, 2.0F, 4.0F, 1.0F), ModelTransform.pivot(2.0F, 19.5F, 0.25F));
        return modelData;
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 32, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.right_leg.pitch =  MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.left_leg.pitch =  MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        fuse.pitch = -0.3927F;
        key.roll += 0.1 * limbDistance;
    }
}
