package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.BulletBillModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.BulletBillEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BulletBillRenderer extends MobEntityRenderer<BulletBillEntity, BulletBillModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/bullet_bill.png");

    public BulletBillRenderer(EntityRendererFactory.Context context) {
        super(context, new BulletBillModel(context.getPart(ModModelLayers.BULLET_BILL)), 0.5f);

    }

    @Override
    public Identifier getTexture(BulletBillEntity entity) {
        return TEXTURE;
    }
}
