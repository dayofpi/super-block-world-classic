package com.dayofpi.sbw_main.entity.renderer;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.entity.model.FakeBlockModel;
import com.dayofpi.sbw_main.entity.registry.ModelLayers;
import com.dayofpi.sbw_main.entity.types.mobs.FakeBlockEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class FakeBlockRenderer<T extends FakeBlockEntity> extends MobEntityRenderer<T, FakeBlockModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/fake_block.png");

    public FakeBlockRenderer(EntityRendererFactory.Context context) {
        super(context, new FakeBlockModel<>(context.getPart(ModelLayers.FAKE_BLOCK)), 0.2F);
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
