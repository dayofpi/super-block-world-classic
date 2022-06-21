package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.MudTrooperModel;
import com.dayofpi.super_block_world.common.entities.hostile.MudTrooperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;

@Environment(EnvType.CLIENT)
public class MudTrooperRenderer<T extends MudTrooperEntity> extends ModEntityRenderer<T> {
    public MudTrooperRenderer(EntityRendererFactory.Context context) {
        super(context, new MudTrooperModel<>());
        this.shadowRadius = 0.5F;
    }
}
