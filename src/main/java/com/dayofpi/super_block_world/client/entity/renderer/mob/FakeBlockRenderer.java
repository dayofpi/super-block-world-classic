package com.dayofpi.super_block_world.client.entity.renderer.mob;

import com.dayofpi.super_block_world.client.entity.model.FakeBlockModel;
import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.entity.mob.FakeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class FakeBlockRenderer<T extends FakeBlockEntity> extends GeoEntityRenderer<T> {
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
