package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.CheepCheepModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.hostile.CheepCheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CheepCheepRenderer<T extends CheepCheepEntity> extends MobEntityRenderer<T, CheepCheepModel<T>> {
    public CheepCheepRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CheepCheepModel<>(ctx.getPart(ModModelLayers.CHEEP_CHEEP)), 0.4F);
        this.shadowRadius = 0.4F;
    }

    @Override
    public Identifier getTexture(T entity) {
        if (entity.isSnowy())
            return new Identifier(Main.MOD_ID, "textures/entity/cheep_cheep_snow.png");
        return new Identifier(Main.MOD_ID, "textures/entity/cheep_cheep.png");
    }
}
