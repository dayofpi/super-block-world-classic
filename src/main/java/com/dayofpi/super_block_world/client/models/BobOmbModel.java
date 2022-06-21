package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.BobOmbEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class BobOmbModel<T extends BobOmbEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T object) {
        return new Identifier(Main.MOD_ID, "geo/bob_omb.geo.json");
    }

    @Override
    public Identifier getTextureResource(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/bob_omb.png");
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return null;
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        if (entity.hasParachute()) {
            IBone root = this.getAnimationProcessor().getBone("root");
            float progress = (float) (customPredicate.animationTick * 5F * 0.01F);
            root.setRotationZ(MathHelper.cos(progress) * 0.2F);
        }
        IBone r_leg = this.getAnimationProcessor().getBone("r_leg");
        IBone l_leg = this.getAnimationProcessor().getBone("l_leg");

        r_leg.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);
        l_leg.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);

        IBone key = this.getAnimationProcessor().getBone("key");
        IBone fuse = this.getAnimationProcessor().getBone("fuse");
        IBone parachute = this.getAnimationProcessor().getBone("parachute");
        key.setRotationZ(entity.windUp * 0.2F);
        String string = Formatting.strip(entity.getName().getString());
        if ("Bobby".equals(string))
            fuse.setHidden(true);
        else if (fuse.isHidden())
            fuse.setHidden(false);
        parachute.setHidden(!entity.hasParachute());
    }
}
