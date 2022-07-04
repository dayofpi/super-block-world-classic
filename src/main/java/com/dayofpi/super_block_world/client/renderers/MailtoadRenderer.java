package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.MailtoadLayer;
import com.dayofpi.super_block_world.client.models.MailtoadModel;
import com.dayofpi.super_block_world.common.entities.passive.MailtoadEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class MailtoadRenderer<T extends MailtoadEntity> extends GeoEntityRenderer<T> {
    public MailtoadRenderer(EntityRendererFactory.Context context) {
        super(context, new MailtoadModel<>());
        this.addLayer(new MailtoadLayer<>(this));
        this.shadowRadius = 0.5f;
    }

    @Override
    public Identifier getTexture(T entity) {
        if (entity.isScared())
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad_scared.png");
        else if (entity.isHappy())
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad_happy.png");
        if (entity.isAnnoyed())
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad_annoyed.png");
        else
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad.png");
    }
}
