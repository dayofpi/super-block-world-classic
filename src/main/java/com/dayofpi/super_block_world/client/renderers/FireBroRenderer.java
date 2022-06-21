package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.renderers.layers.HammerBroItem;
import com.dayofpi.super_block_world.client.models.HammerBroModel;
import com.dayofpi.super_block_world.client.main.ModelLayers;
import com.dayofpi.super_block_world.common.entities.mob.FireBroEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class FireBroRenderer<T extends FireBroEntity> extends MobEntityRenderer<T, HammerBroModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID,"textures/entity/fire_bro.png");

    public FireBroRenderer(EntityRendererFactory.Context context) {
        super(context, new HammerBroModel<>(context.getPart(ModelLayers.FIRE_BRO)), 0.5F);
        this.addFeature(new HammerBroItem<>(this));
    }

    public Identifier getTexture(FireBroEntity hammerBroEntity) {
        return TEXTURE;
    }
}