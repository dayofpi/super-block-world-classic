package com.dayofpi.super_block_world.client.renderer.mob;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.client.model.RottenMushroomModel;
import com.dayofpi.super_block_world.client.ModelLayers;
import com.dayofpi.super_block_world.client.renderer.feature.RottenMushroomEyes;
import com.dayofpi.super_block_world.main.common.entity.mob.RottenMushroomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class RottenMushroomRenderer<T extends RottenMushroomEntity> extends MobEntityRenderer<T, RottenMushroomModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/rotten_mushroom.png");

    public RottenMushroomRenderer(EntityRendererFactory.Context context) {
        super(context, new RottenMushroomModel<>(context.getPart(ModelLayers.ROTTEN_MUSHROOM)), 0.5F);
        this.addFeature(new RottenMushroomEyes<>(this));
    }

    public Identifier getTexture(RottenMushroomEntity rottenMushroomEntity) {
        return TEXTURE;
    }
}