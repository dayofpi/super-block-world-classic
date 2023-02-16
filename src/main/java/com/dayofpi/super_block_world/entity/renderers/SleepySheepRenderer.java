package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.SleepySheepModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.passive.SleepySheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SleepySheepRenderer extends MobEntityRenderer<SleepySheepEntity, SleepySheepModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/sleepy_sheep.png");

    public SleepySheepRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SleepySheepModel(ctx.getPart(ModModelLayers.SLEEPY_SHEEP)), 0.4F);
    }

    @Override
    public Identifier getTexture(SleepySheepEntity entity) {
        return TEXTURE;
    }
}
