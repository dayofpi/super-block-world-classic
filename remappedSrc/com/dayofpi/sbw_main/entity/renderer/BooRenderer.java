package com.dayofpi.sbw_main.entity.renderer;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.entity.model.BooModel;
import com.dayofpi.sbw_main.entity.registry.ModelLayers;
import com.dayofpi.sbw_main.entity.feature.BooColor;
import com.dayofpi.sbw_main.entity.feature.BooItem;
import com.dayofpi.sbw_main.entity.types.mobs.BooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class BooRenderer extends MobEntityRenderer<BooEntity, BooModel<BooEntity>> {
    private static final Identifier NORMAL_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/boo/seeking.png");
    private static final Identifier HIDING_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/boo/hiding.png");
    private static final Identifier TAMED_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/boo/tamed.png");
    private static final Identifier SITTING_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/boo/sitting.png");

    public BooRenderer(EntityRendererFactory.Context context) {
        super(context, new BooModel<>(context.getPart(ModelLayers.BOO)), 0.5F);
        this.addFeature(new BooColor(this));
        this.addFeature(new BooItem(this));
    }

    protected int getBlockLight(BooEntity booEntity, BlockPos blockPos) {
        return booEntity.isHiding() ? 7 : 15;
    }

    public Identifier getTexture(BooEntity booEntity) {
        if (booEntity.isHiding()) {
            return HIDING_TEXTURE;
        } else if (booEntity.isTamed()) {
            return booEntity.isInSittingPose() ? SITTING_TEXTURE : TAMED_TEXTURE;
        } else return NORMAL_TEXTURE;
    }
}
