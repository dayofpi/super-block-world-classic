package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.FakeBlockModel;
import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.mob.FakeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FakeBlockRenderer<T extends FakeBlockEntity> extends LeadableEntityRenderer<T> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/fake_block.png");

    public FakeBlockRenderer(EntityRendererFactory.Context context) {
        super(context, new FakeBlockModel<>());
        this.shadowRadius = 0.3F;
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
