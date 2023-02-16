package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.KingBobOmbModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.boss.KingBobOmbEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class KingBobOmbRenderer extends MobEntityRenderer<KingBobOmbEntity, KingBobOmbModel> {
    public KingBobOmbRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new KingBobOmbModel(ctx.getPart(ModModelLayers.KING_BOB_OMB)), 0.9f);
    }

    @Override
    public Identifier getTexture(KingBobOmbEntity entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/king_bob_omb.png");
    }

    @Override
    protected void scale(KingBobOmbEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(2.0f, 2.0f, 2.0f);
    }
}
