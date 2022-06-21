package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.ThwompModel;
import com.dayofpi.super_block_world.client.main.ModelLayers;
import com.dayofpi.super_block_world.common.entities.mob.ThwompEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ThwompRenderer<T extends ThwompEntity> extends MobEntityRenderer<T, ThwompModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/thwomp/idle.png");

    public ThwompRenderer(EntityRendererFactory.Context context) {
        super(context, new ThwompModel<>(context.getPart(ModelLayers.THWOMP)), 0.5F);
    }

    @Override
    public Identifier getTexture(T entity) {
        if (entity.getStage() == 1)
            return TEXTURE;
        else if (entity.getStage() == 2)
            return new Identifier(Main.MOD_ID, "textures/entity/thwomp/peek.png");
        else
            return new Identifier(Main.MOD_ID, "textures/entity/thwomp/attack.png");
    }
}
