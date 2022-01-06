package com.dayofpi.super_block_world.client.renderer.mob;

import com.dayofpi.super_block_world.client.ModelLayers;
import com.dayofpi.super_block_world.client.model.HammerBroModel;
import com.dayofpi.super_block_world.client.renderer.feature.HammerBroItem;
import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.entity.mob.hammer_bro.IceBroEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class IceBroRenderer<T extends IceBroEntity> extends MobEntityRenderer<T, HammerBroModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID,"textures/entity/ice_bro.png");

    public IceBroRenderer(EntityRendererFactory.Context context) {
        super(context, new HammerBroModel<>(context.getPart(ModelLayers.ICE_BRO)), 0.5F);
        this.addFeature(new HammerBroItem<>(this));
    }

    public Identifier getTexture(IceBroEntity hammerBroEntity) {
        return TEXTURE;
    }
}