package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.misc.GoKartEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class GoKartModel<T extends GoKartEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart flag;
    private final ModelPart wheel;
    private final ModelPart hind_left_tire;
    private final ModelPart hind_right_tire;
    private final ModelPart front_right_tire;
    private final ModelPart front_left_tire;

    public GoKartModel(ModelPart modelPart) {
        this.root = modelPart.getChild("root");
        this.flag = root.getChild("flagpole").getChild("flag_root");
        this.wheel = root.getChild("body").getChild("wheel");
        this.hind_left_tire = root.getChild("tires").getChild("hind_left_tire");
        this.hind_right_tire = root.getChild("tires").getChild("hind_right_tire");
        this.front_right_tire = root.getChild("tires").getChild("front_right_tire");
        this.front_left_tire = root.getChild("tires").getChild("front_left_tire");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData tires = root.addChild("tires", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        tires.addChild("hind_left_tire", ModelPartBuilder.create().uv(0, 9).mirrored().cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F).mirrored(false), ModelTransform.pivot(7.0F, -2.0F, 5.0F));

        tires.addChild("hind_right_tire", ModelPartBuilder.create().uv(0, 9).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F), ModelTransform.pivot(-7.0F, -2.0F, 5.0F));

        tires.addChild("front_right_tire", ModelPartBuilder.create().uv(0, 9).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F), ModelTransform.pivot(-7.0F, -2.0F, -5.0F));

        tires.addChild("front_left_tire", ModelPartBuilder.create().uv(0, 9).mirrored().cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F).mirrored(false), ModelTransform.pivot(7.0F, -2.0F, -5.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 23).cuboid(-5.0F, -5.0F, -1.0F, 10.0F, 2.0F, 6.0F).uv(0, 31).cuboid(-3.0F, -7.0F, -9.0F, 6.0F, 4.0F, 5.0F).uv(29, 28).cuboid(-5.0F, -7.0F, 7.0F, 10.0F, 4.0F, 3.0F).uv(22, 35).cuboid(-5.0F, -9.0F, 3.0F, 10.0F, 4.0F, 2.0F).uv(0, 0).cuboid(-5.0F, -3.0F, -10.0F, 10.0F, 3.0F, 20.0F).uv(0, 0).cuboid(5.0F, -3.0F, -3.0F, 2.0F, 3.0F, 6.0F).uv(0, 0).cuboid(-7.0F, -3.0F, -3.0F, 2.0F, 3.0F, 6.0F), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

        body.addChild("wheel", ModelPartBuilder.create().uv(0, 40).cuboid(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F), ModelTransform.of(0.0F, -7.0F, -4.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData flagpole = root.addChild("flagpole", ModelPartBuilder.create().uv(48, 50).cuboid(-0.5F, -2.75F, -0.5F, 1.0F, 12.0F, 1.0F).uv(16, 58).cuboid(-1.5F, -4.75F, -1.5F, 3.0F, 3.0F, 3.0F), ModelTransform.pivot(0.0F, -12.25F, 6.0F));
        ModelPartData flag = flagpole.addChild("flag_root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.25F, -0.5F));
        flag.addChild("flag", ModelPartBuilder.create().uv(32, 58).cuboid(-14.0F, -8.0F, 5.0F, 7.0F, 6.0F, 0.0F), ModelTransform.of(-5.0F, 5.0F, -6.5F, 0.0F, 1.5708F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    private void updateTires(ModelPart modelPart, GoKartEntity entity) {
        modelPart.setAngles(entity.tirePitch * 0.4f, entity.getYaw() * 0.01f, 0.0f);
    }

    @Override
    public void setAngles(GoKartEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.flag.yaw = (MathHelper.cos(entity.world.getTime())) * 0.1f;
        this.wheel.setAngles(1.5708f, entity.getYaw() * 0.01f, 0.0f);
        this.wheel.pivotY = -7.1f;
        this.updateTires(this.front_left_tire, entity);
        this.updateTires(this.front_right_tire, entity);
        this.updateTires(this.hind_left_tire, entity);
        this.updateTires(this.hind_right_tire, entity);
    }
}
