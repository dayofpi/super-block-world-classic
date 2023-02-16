package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.BlockstepperModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.BlockstepperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BlockstepperRenderer extends MobEntityRenderer<BlockstepperEntity, BlockstepperModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/blockstepper.png");

    public BlockstepperRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BlockstepperModel(ctx.getPart(ModModelLayers.BLOCKSTEPPER)), 0.5F);
    }

    @Override
    public Identifier getTexture(BlockstepperEntity entity) {
        return TEXTURE;
    }
}
