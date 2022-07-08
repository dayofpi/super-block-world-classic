package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.BobOmb;
import com.dayofpi.super_block_world.common.entities.passive.BobOmbBuddyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BobOmbModel extends SinglePartEntityModel<PathAwareEntity> {
    private final ModelPart root;
    private final ModelPart key;
    private final ModelPart fuse;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart parachute;

    public BobOmbModel(ModelPart root) {
        this.root = root.getChild("root");
        this.key = this.root.getChild("key");
        this.fuse = this.root.getChild("fuse");
        this.right_leg = this.root.getChild("right_leg");
        this.left_leg = this.root.getChild("left_leg");
        this.parachute = this.root.getChild("parachute");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 12.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(16, 19).cuboid(-2.0F, 11.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

        ModelPartData key = root.addChild("key", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.0F, 7.0F));

        key.addChild("key_r1", ModelPartBuilder.create().uv(0, 19).cuboid(-6.0F, -7.0F, 3.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 3.0F, -3.0F, 0.0F, 1.5708F, 0.0F));

        root.addChild("fuse", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -5.0F, -0.5F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 13.0F, 0.0F));

        root.addChild("right_leg", ModelPartBuilder.create().uv(0, 27).cuboid(-1.0F, 0.0F, -0.25F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(12, 24).cuboid(-1.5F, 3.0F, -2.25F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 18.0F, 0.25F));

        root.addChild("left_leg", ModelPartBuilder.create().uv(12, 24).cuboid(-1.5F, 3.0F, -2.25F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 27).cuboid(-1.0F, 0.0F, -0.25F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 18.0F, 0.25F));
        root.addChild("parachute", ModelPartBuilder.create().uv(32, 19).cuboid(-4.0F, -23.0F, -4.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.0F))
                .uv(48, 0).cuboid(-4.0F, -18.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 23.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(PathAwareEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (!(entity instanceof BobOmb))
            return;
        this.root.roll = 0.0F;
        if (((BobOmb) entity).hasParachute()) {
            float progress = animationProgress * 5F * 0.01F;
            this.root.roll = MathHelper.cos(progress) * 0.2F;
        }

        this.right_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.left_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;

        key.roll = ((BobOmb) entity).getWindUp() * 0.2F;
        String string = Formatting.strip(entity.getName().getString());
        if ("Bobby".equals(string) || entity instanceof BobOmbBuddyEntity)
            fuse.visible = false;
        else if (fuse.hidden)
            fuse.visible = true;
        parachute.visible = ((BobOmb) entity).hasParachute();
    }
}
