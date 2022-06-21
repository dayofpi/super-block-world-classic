package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.MechakoopaMissileModel;
import com.dayofpi.super_block_world.common.entities.projectile.MechakoopaMissileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

@Environment(EnvType.CLIENT)
public class MechakoopaMissileRenderer<T extends MechakoopaMissileEntity> extends GeoProjectilesRenderer<T> {
    public MechakoopaMissileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MechakoopaMissileModel<>());
    }

}
