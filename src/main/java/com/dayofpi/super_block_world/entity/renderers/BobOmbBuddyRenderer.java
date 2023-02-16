package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.BobOmbModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BobOmbBuddyRenderer extends MobEntityRenderer<PathAwareEntity, BobOmbModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/bob_omb_buddy.png");

    public BobOmbBuddyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BobOmbModel(ctx.getPart(ModModelLayers.BOB_OMB)), 0.4F);
    }

    @Override
    public Identifier getTexture(PathAwareEntity entity) {
        return TEXTURE;
    }
}
