package com.dayofpi.super_block_world.main.client.entity.renderer.mob;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.client.entity.renderer.feature.HammerBroItem;
import com.dayofpi.super_block_world.main.client.entity.model.HammerBroModel;
import com.dayofpi.super_block_world.main.client.entity.ModelLayers;
import com.dayofpi.super_block_world.main.common.entity.mob.hammer_bro.HammerBroEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class HammerBroRenderer<T extends HammerBroEntity> extends MobEntityRenderer<T, HammerBroModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID,"textures/entity/hammer_bro.png");

    public HammerBroRenderer(EntityRendererFactory.Context context) {
        super(context, new HammerBroModel<>(context.getPart(ModelLayers.HAMMER_BRO)), 0.5F);
        this.addFeature(new HammerBroItem<>(this));
    }

    public Identifier getTexture(HammerBroEntity hammerBroEntity) {
        return TEXTURE;
    }
}