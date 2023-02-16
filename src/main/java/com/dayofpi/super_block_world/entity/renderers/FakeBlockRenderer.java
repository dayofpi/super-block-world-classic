package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.FakeBlockModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.FakeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FakeBlockRenderer extends MobEntityRenderer<FakeBlockEntity, FakeBlockModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/fake_block.png");

    public FakeBlockRenderer(EntityRendererFactory.Context context) {
        super(context, new FakeBlockModel(context.getPart(ModModelLayers.FAKE_BLOCK)), 0.4f);

    }

    @Override
    public Identifier getTexture(FakeBlockEntity entity) {
        return TEXTURE;
    }
}
