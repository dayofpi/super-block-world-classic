package com.dayofpi.sbw_main.entity.model;

import com.dayofpi.sbw_main.entity.types.bases.AbstractBro;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class HammerBroModel<T extends AbstractBro> extends SinglePartEntityModel<T> implements ModelWithArms {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    private final ModelPart ROOT;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart head;
    public BipedEntityModel.ArmPose left_armPose = BipedEntityModel.ArmPose.EMPTY;
    public BipedEntityModel.ArmPose right_armPose = BipedEntityModel.ArmPose.EMPTY;

    public HammerBroModel(ModelPart root) {
        this.ROOT = root;
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
        ModelPart body = root.getChild("body");
        this.left_arm = body.getChild("left_arm");
        this.right_arm = body.getChild("right_arm");
        this.head = body.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(28, 30).cuboid(-2.0F, 0.5F, -2.0F, 4.0F, 9.0F, 4.0F), ModelTransform.pivot(1.0F, 14.5F, -1.0F));

        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(28, 30).mirrored().cuboid(-2.0F, 0.5F, -2.0F, 4.0F, 9.0F, 4.0F), ModelTransform.pivot(-3.0F, 14.5F, -1.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), ModelTransform.pivot(-1.0F, 15.0F, 0.0F));

        body.addChild("head", ModelPartBuilder.create().uv(24, 10).cuboid(-4.0F, -7.8333F, -4.3333F, 8.0F, 8.0F, 8.0F)
                .uv(0, 18).cuboid(-4.0F, -7.8333F, -4.3333F, 8.0F, 8.0F, 8.0F, new Dilation(.5F))
                .uv(24, 0).cuboid(-2.0F, -2.8333F, -6.3333F, 4.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0F, -10.1667F, -1.6667F));

        body.addChild("right_arm", ModelPartBuilder.create().uv(0, 34).mirrored().cuboid(-2.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F), ModelTransform.pivot(-4.5F, -7.5F, -1.0F));

        body.addChild("left_arm", ModelPartBuilder.create().uv(0, 34).cuboid(-0.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F), ModelTransform.pivot(4.5F, -7.5F, -1.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return ROOT;
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
        this.right_leg.yaw = 0.0f;
        this.left_leg.yaw = 0.0f;
        this.right_leg.roll = 0.0f;
        this.left_leg.roll = 0.0f;
        this.right_arm.roll = 0.0f;
        this.left_arm.roll = 0.0f;
        float k = 1.0f;
        boolean bl = entity.getRoll() > 4;
        if (bl) {
            k = (float) entity.getVelocity().lengthSquared();
            k /= 0.2f;
            k *= k * k;
        }
        if (k < 1.0f) {
            k = 1.0f;
        }
        this.right_arm.pitch = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 2.0f * limbSwingAmount * 0.5f / k;
        this.left_arm.pitch = MathHelper.cos(limbSwing * 0.6662f) * 2.0f * limbSwingAmount * 0.5f / k;

        boolean bl3;
        bl3 = ((LivingEntity) entity).getMainArm() == Arm.RIGHT;
        boolean bl4;
        if (entity.isUsingItem()) {
            bl4 = entity.getActiveHand() == Hand.MAIN_HAND;
            if (bl4 == bl3) {
                this.positionRightArm(entity);
            } else {
                this.positionLeftArm(entity);
            }
        } else {
            bl4 = bl3 ? this.left_armPose.isTwoHanded() : this.right_armPose.isTwoHanded();
            if (bl3 != bl4) {
                this.positionLeftArm(entity);
                this.positionRightArm(entity);
            } else {
                this.positionRightArm(entity);
                this.positionLeftArm(entity);
            }
        }

        this.right_leg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount / k;
        this.left_leg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount / k;

        if (this.riding) {
            this.right_arm.pitch -= 0.62831855f;
            this.left_arm.pitch -= 0.62831855f;
            this.right_leg.pitch = -1.4137167f;
            this.right_leg.yaw = 0.31415927f;
            this.right_leg.roll = 0.07853982f;
            this.left_leg.pitch = -1.4137167f;
            this.left_leg.yaw = -0.31415927f;
            this.left_leg.roll = -0.07853982f;
        }

        if (entity.isAttacking() && entity.handSwingProgress == 0) {
            if (entity.isLeftHanded()) {
                this.left_arm.pitch = -1.8f;
            } else {
                this.right_arm.pitch = -1.8f;
            }
        }

        this.animateArms(entity, ageInTicks);

        if (this.right_armPose != BipedEntityModel.ArmPose.SPYGLASS) {
            CrossbowPosing.swingArm(this.right_arm, ageInTicks, 1.0f);
        }
        if (this.left_armPose != BipedEntityModel.ArmPose.SPYGLASS) {
            CrossbowPosing.swingArm(this.left_arm, ageInTicks, -1.0f);
        }
        float leaningPitch = entity.getLeaningPitch(ageInTicks);
        if (leaningPitch > 0.0f) {
            float n;
            float bl42 = limbSwing % 26.0f;
            Arm arm = this.getPreferredArm(entity);
            float l = arm == Arm.RIGHT && this.handSwingProgress > 0.0f ? 0.0f : leaningPitch;
            float m = arm == Arm.LEFT && this.handSwingProgress > 0.0f ? 0.0f : leaningPitch;
            if (!entity.isUsingItem()) {
                if (bl42 < 14.0f) {
                    this.left_arm.pitch = this.lerpAngle(m, this.left_arm.pitch, 0.0f);
                    this.right_arm.pitch = MathHelper.lerp(l, this.right_arm.pitch, 0.0f);
                    this.left_arm.yaw = this.lerpAngle(m, this.left_arm.yaw, (float)Math.PI);
                    this.right_arm.yaw = MathHelper.lerp(l, this.right_arm.yaw, (float)Math.PI);
                    this.left_arm.roll = this.lerpAngle(m, this.left_arm.roll, (float)Math.PI + 1.8707964f * this.method_2807(bl42) / this.method_2807(14.0f));
                    this.right_arm.roll = MathHelper.lerp(l, this.right_arm.roll, (float)Math.PI - 1.8707964f * this.method_2807(bl42) / this.method_2807(14.0f));
                } else if (bl42 >= 14.0f && bl42 < 22.0f) {
                    n = (bl42 - 14.0f) / 8.0f;
                    this.left_arm.pitch = this.lerpAngle(m, this.left_arm.pitch, 1.5707964f * n);
                    this.right_arm.pitch = MathHelper.lerp(l, this.right_arm.pitch, 1.5707964f * n);
                    this.left_arm.yaw = this.lerpAngle(m, this.left_arm.yaw, (float)Math.PI);
                    this.right_arm.yaw = MathHelper.lerp(l, this.right_arm.yaw, (float)Math.PI);
                    this.left_arm.roll = this.lerpAngle(m, this.left_arm.roll, 5.012389f - 1.8707964f * n);
                    this.right_arm.roll = MathHelper.lerp(l, this.right_arm.roll, 1.2707963f + 1.8707964f * n);
                } else if (bl42 >= 22.0f && bl42 < 26.0f) {
                    n = (bl42 - 22.0f) / 4.0f;
                    this.left_arm.pitch = this.lerpAngle(m, this.left_arm.pitch, 1.5707964f - 1.5707964f * n);
                    this.right_arm.pitch = MathHelper.lerp(l, this.right_arm.pitch, 1.5707964f - 1.5707964f * n);
                    this.left_arm.yaw = this.lerpAngle(m, this.left_arm.yaw, (float)Math.PI);
                    this.right_arm.yaw = MathHelper.lerp(l, this.right_arm.yaw, (float)Math.PI);
                    this.left_arm.roll = this.lerpAngle(m, this.left_arm.roll, (float)Math.PI);
                    this.right_arm.roll = MathHelper.lerp(l, this.right_arm.roll, (float)Math.PI);
                }
            }

            this.left_leg.pitch = MathHelper.lerp(leaningPitch, this.left_leg.pitch, 0.3f * MathHelper.cos(limbSwing * 0.33333334f + (float)Math.PI));
            this.right_leg.pitch = MathHelper.lerp(leaningPitch, this.right_leg.pitch, 0.3f * MathHelper.cos(limbSwing * 0.33333334f));
        }
    }

    private float method_2807(float f) {
        return -65.0f * f + f * f;
    }

    protected float lerpAngle(float angleOne, float angleTwo, float magnitude) {
        float f = (magnitude - angleTwo) % ((float)Math.PI * 2);
        if (f < (float)(-Math.PI)) {
            f += (float)Math.PI * 2;
        }
        if (f >= (float)Math.PI) {
            f -= (float)Math.PI * 2;
        }
        return angleTwo + angleOne * f;
    }

    protected ModelPart getArm(Arm arm) {
        return arm == Arm.LEFT ? this.left_arm : this.right_arm;
    }

    private Arm getPreferredArm(T entity) {
        Arm arm = entity.getMainArm();
        return entity.preferredHand == Hand.MAIN_HAND ? arm : arm.getOpposite();
    }

    private void positionRightArm(T entity) {
        switch (this.right_armPose) {
            case EMPTY -> this.right_arm.yaw = 0.0f;
            case BLOCK -> {
                this.right_arm.pitch = this.right_arm.pitch * 0.5f - 0.9424779f;
                this.right_arm.yaw = -0.5235988f;
            }
            case ITEM -> {
                this.right_arm.pitch = this.right_arm.pitch * 0.5f - 0.31415927f;
                this.right_arm.yaw = 0.0f;
            }
            case THROW_SPEAR -> {
                this.right_arm.pitch = this.right_arm.pitch * 0.5f - (float) Math.PI;
                this.right_arm.yaw = 0.0f;
            }
            case BOW_AND_ARROW -> {
                this.right_arm.yaw = -0.1f + this.head.yaw;
                this.left_arm.yaw = 0.1f + this.head.yaw + 0.4f;
                this.right_arm.pitch = -1.5707964f + this.head.pitch;
                this.left_arm.pitch = -1.5707964f + this.head.pitch;
            }
            case CROSSBOW_CHARGE -> CrossbowPosing.charge(this.right_arm, this.left_arm, entity, true);
            case CROSSBOW_HOLD -> CrossbowPosing.hold(this.right_arm, this.left_arm, this.head, true);
            case SPYGLASS -> {
                this.right_arm.pitch = MathHelper.clamp(this.head.pitch - 1.9198622f - (entity.isInSneakingPose() ? 0.2617994f : 0.0f), -2.4f, 3.3f);
                this.right_arm.yaw = this.head.yaw - 0.2617994f;
            }
        }
    }

    private void positionLeftArm(T entity) {
        switch (this.left_armPose) {
            case EMPTY -> this.left_arm.yaw = 0.0f;
            case BLOCK -> {
                this.left_arm.pitch = this.left_arm.pitch * 0.5f - 0.9424779f;
                this.left_arm.yaw = 0.5235988f;
            }
            case ITEM -> {
                this.left_arm.pitch = this.left_arm.pitch * 0.5f - 0.31415927f;
                this.left_arm.yaw = 0.0f;
            }
            case THROW_SPEAR -> {
                this.left_arm.pitch = this.left_arm.pitch * 0.5f - (float) Math.PI;
                this.left_arm.yaw = 0.0f;
            }
            case BOW_AND_ARROW -> {
                this.right_arm.yaw = -0.1f + this.head.yaw - 0.4f;
                this.left_arm.yaw = 0.1f + this.head.yaw;
                this.right_arm.pitch = -1.5707964f + this.head.pitch;
                this.left_arm.pitch = -1.5707964f + this.head.pitch;
            }
            case CROSSBOW_CHARGE -> CrossbowPosing.charge(this.right_arm, this.left_arm, entity, false);
            case CROSSBOW_HOLD -> CrossbowPosing.hold(this.right_arm, this.left_arm, this.head, false);
            case SPYGLASS -> {
                this.left_arm.pitch = MathHelper.clamp(this.head.pitch - 1.9198622f - (entity.isInSneakingPose() ? 0.2617994f : 0.0f), -2.4f, 3.3f);
                this.left_arm.yaw = this.head.yaw + 0.2617994f;
            }
        }
    }

    protected void animateArms(T entity, float a) {
        if (this.handSwingProgress <= 0.0f) {
            return;
        }

        if (this.handSwingProgress > 0.0f && entity.isAttacking()) {
            attack(entity.isLeftHanded() ? this.left_arm : this.right_arm, entity, this.handSwingProgress, a);
            return;
        }

        Arm arm = this.getPreferredArm(entity);
        ModelPart modelPart = this.getArm(arm);
        float f = this.handSwingProgress;
        this.ROOT.yaw = MathHelper.sin(MathHelper.sqrt(f) * ((float)Math.PI * 2)) * 0.2f;
        if (arm == Arm.LEFT) {
            this.ROOT.yaw *= -1.0f;
        }
        this.right_arm.pivotZ = MathHelper.sin(this.ROOT.yaw) * 5.0f;
        this.right_arm.pivotX = -MathHelper.cos(this.ROOT.yaw) * 5.0f;
        this.left_arm.pivotZ = -MathHelper.sin(this.ROOT.yaw) * 5.0f;
        this.left_arm.pivotX = MathHelper.cos(this.ROOT.yaw) * 5.0f;
        this.right_arm.yaw += this.ROOT.yaw;
        this.left_arm.yaw += this.ROOT.yaw;
        this.left_arm.pitch += this.ROOT.yaw;
        f = 1.0f - this.handSwingProgress;
        f *= f;
        f *= f;
        f = 1.0f - f;
        float g = MathHelper.sin(f * (float)Math.PI);
        float h = MathHelper.sin(this.handSwingProgress * (float)Math.PI) * -(this.head.pitch - 0.7f) * 0.75f;
        modelPart.pitch = (float)((double)modelPart.pitch - ((double)g * 1.2 + (double)h));
        modelPart.yaw += this.ROOT.yaw * 2.0f;
        modelPart.roll += MathHelper.sin(this.handSwingProgress * (float)Math.PI) * -0.4f;
    }

    public static <T extends MobEntity> void attack(ModelPart arm, T actor, float swingProgress, float animationProgress) {
        float f = MathHelper.sin(swingProgress * (float)Math.PI);
        float g = MathHelper.sin((1.0f - (1.0f - swingProgress) * (1.0f - swingProgress)) * (float)Math.PI);
        arm.roll = 0.0f;
        arm.yaw = 0.15707964f;
        if (actor.getMainArm() == Arm.RIGHT) {
            arm.pitch = -1.8849558f + MathHelper.cos(animationProgress * 0.09f) * 0.15f;
            arm.pitch += f * 2.2f - g * 0.4f;
        } else {
            arm.pitch = -0.0f + MathHelper.cos(animationProgress * 0.19f) * 0.5f;
            arm.pitch += f * 1.2f - g * 0.4f;
        }
        CrossbowPosing.swingArm(arm, animationProgress, 1.0F);
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        this.getArm(arm).rotate(matrices);
    }
}
