package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.DryBonesModel;
import com.dayofpi.super_block_world.client.renderers.layers.DryBonesLayer;
import com.dayofpi.super_block_world.common.entities.mob.DryBonesEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DryBonesRenderer<T extends DryBonesEntity> extends LeadableEntityRenderer<T> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/koopa/dry_bones.png");

    public DryBonesRenderer(EntityRendererFactory.Context context) {
        super(context, new DryBonesModel<>());
        this.shadowRadius = 0.4F;
        addLayer(new DryBonesLayer<>(this));
    }

    protected float getDeathMaxRotation(T entityLivingBaseIn) {
        return 0.0F;
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
