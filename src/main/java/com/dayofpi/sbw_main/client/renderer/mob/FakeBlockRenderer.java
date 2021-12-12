package com.dayofpi.sbw_main.client.renderer.mob;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.client.model.FakeBlockModel;
import com.dayofpi.sbw_main.registry.entity.ModelLayers;
import com.dayofpi.sbw_main.common.entity.type.mobs.FakeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
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
