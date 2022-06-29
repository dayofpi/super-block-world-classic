package com.dayofpi.super_block_world.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class ModAnimations {
    private static Vec3f posVec(float d, float e, float f) {
    return AnimationHelper.method_41823(d, e, f);
}

    private static Vec3f degreeVec(float d, float e, float f) {
        return AnimationHelper.method_41829(d, e, f);
    }

    private static Vec3f scaleVec(double d, double e, double f) {
        return AnimationHelper.method_41822(d, e, f);
    }

    public static class Fuzzy {
        public static final Animation IDLE = Animation.Builder.create(0.25f).looping().addBoneAnimation("right_eye", new Transformation(Transformation.Targets.SCALE, new Keyframe(0f, scaleVec(1f, 1f, 1f), Transformation.Interpolations.field_37884), new Keyframe(0.125f, scaleVec(0.7f, 0.7f, 0.7f), Transformation.Interpolations.field_37884), new Keyframe(0.25f, scaleVec(1f, 1f, 1f), Transformation.Interpolations.field_37884))).addBoneAnimation("left_eye", new Transformation(Transformation.Targets.SCALE, new Keyframe(0f, scaleVec(0.7f, 0.7f, 0.7f), Transformation.Interpolations.field_37884), new Keyframe(0.125f, scaleVec(1f, 1f, 1f), Transformation.Interpolations.field_37884), new Keyframe(0.25f, scaleVec(0.8f, 0.8f, 0.8f), Transformation.Interpolations.field_37884))).addBoneAnimation("spikes", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, degreeVec(0f, 0f, 0f), Transformation.Interpolations.field_37884), new Keyframe(0.125f, degreeVec(0f, 0f, 45f), Transformation.Interpolations.field_37884), new Keyframe(0.25f, degreeVec(0f, 0f, 90f), Transformation.Interpolations.field_37884))).build();
    }

    public static class LaunchStar {
        public static final Animation IDLE = Animation.Builder.create(16f).looping().addBoneAnimation("big", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, degreeVec(0f, 0f, 0f), Transformation.Interpolations.field_37884), new Keyframe(8f, degreeVec(0f, 360f, 0f), Transformation.Interpolations.field_37884), new Keyframe(15.958333333333334f, degreeVec(0f, 360+360f, 0f), Transformation.Interpolations.field_37884))).addBoneAnimation("tiny", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.08333333333333333f, degreeVec(0f, 0f, 0f), Transformation.Interpolations.field_37884), new Keyframe(16f, degreeVec(0f, 360f, 0f), Transformation.Interpolations.field_37884))).build();
        public static final Animation LAUNCH = Animation.Builder.create(1.8333333333333333f).looping().addBoneAnimation("big", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, posVec(0f, 0f, 0f), Transformation.Interpolations.field_37884), new Keyframe(0.3333333333333333f, posVec(0f, -1f, 0f), Transformation.Interpolations.field_37884), new Keyframe(1f, posVec(0f, 1f, 0f), Transformation.Interpolations.field_37884), new Keyframe(1.5f, posVec(0f, 0f, 0f), Transformation.Interpolations.field_37884))).addBoneAnimation("big", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, degreeVec(0f, 0f, 0f), Transformation.Interpolations.field_37884), new Keyframe(0.625f, degreeVec(0f, 360f, 0f), Transformation.Interpolations.field_37884))).addBoneAnimation("tiny", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, posVec(0f, 0f, 0f), Transformation.Interpolations.field_37884), new Keyframe(0.5f, posVec(0f, -3f, 0f), Transformation.Interpolations.field_37884), new Keyframe(0.875f, posVec(0f, 6f, 0f), Transformation.Interpolations.field_37884), new Keyframe(1.625f, posVec(0f, -2f, 0f), Transformation.Interpolations.field_37884), new Keyframe(1.8333333333333333f, posVec(0f, -1f, 0f), Transformation.Interpolations.field_37884), new Keyframe(1.125f, posVec(0f, 6f, 0f), Transformation.Interpolations.field_37884))).addBoneAnimation("tiny", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, degreeVec(0f, 0f, 0f), Transformation.Interpolations.field_37884), new Keyframe(1.625f, degreeVec(0f, -360f, 0f), Transformation.Interpolations.field_37884))).build();
    }

    public static class RottenMushroom {
        public static final Animation WALK = Animation.Builder.create(0.41667f).looping().addBoneAnimation("root", new Transformation(Transformation.Targets.SCALE, new Keyframe(0f, scaleVec(1f, 1f, 1f), Transformation.Interpolations.field_37884), new Keyframe(0.2083f, scaleVec(1.1f, 0.9f, 1.1f), Transformation.Interpolations.field_37884), new Keyframe(0.4167f, scaleVec(1f, 1f, 1f), Transformation.Interpolations.field_37884))).build();
    }
}