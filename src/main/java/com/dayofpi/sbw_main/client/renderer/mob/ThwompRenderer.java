package com.dayofpi.sbw_main.client.renderer.mob;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.client.model.ThwompModel;
import com.dayofpi.sbw_main.registry.entity.ModelLayers;
import com.dayofpi.sbw_main.common.entity.type.mobs.ThwompEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ThwompRenderer<T extends ThwompEntity> extends MobEntityRenderer<T, ThwompModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/thwomp/idle.png");

    public ThwompRenderer(EntityRendererFactory.Context context) {
        super(context, new ThwompModel<>(context.getPart(ModelLayers.THWOMP)), 0.5F);
    }

    @Override
    public Identifier getTexture(T entity) {
        if (entity.getStage() == 1)
            return TEXTURE;
        else if (entity.getStage() == 2)
            return new Identifier(Main.MOD_ID, "textures/entity/thwomp/peek.png");
        else
            return new Identifier(Main.MOD_ID, "textures/entity/thwomp/attack.png");
    }
}
