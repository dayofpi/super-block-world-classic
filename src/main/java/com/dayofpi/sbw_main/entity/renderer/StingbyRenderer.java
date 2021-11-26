package com.dayofpi.sbw_main.entity.renderer;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.entity.model.StingbyModel;
import com.dayofpi.sbw_main.entity.registry.ModelLayers;
import com.dayofpi.sbw_main.entity.types.mobs.StingbyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class StingbyRenderer<T extends StingbyEntity> extends MobEntityRenderer<T, StingbyModel<T>> {
    private static final Identifier NORMAL = new Identifier(Main.MOD_ID, "textures/entity/stingby.png");
    private static final Identifier ANGRY = new Identifier(Main.MOD_ID, "textures/entity/stingby_angry.png");

    public StingbyRenderer(EntityRendererFactory.Context context) {
        super(context, new StingbyModel<>(context.getPart(ModelLayers.STINGBY)), 0.5F);
    }

    @Override
    public Identifier getTexture(T entity) {
        return entity.isAttacking() ? ANGRY : NORMAL;
    }
}
