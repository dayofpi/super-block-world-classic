package com.dayofpi.sbw_main.entity.renderer;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.entity.model.BooModel;
import com.dayofpi.sbw_main.entity.registry.ModelLayers;
import com.dayofpi.sbw_main.entity.renderer.feature.BooColor;
import com.dayofpi.sbw_main.entity.renderer.feature.BooItem;
import com.dayofpi.sbw_main.entity.type.mobs.BooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
@Environment(EnvType.CLIENT)
public class BooRenderer extends MobEntityRenderer<BooEntity, BooModel<BooEntity>> {
    private static final Identifier BOO = new Identifier(Main.MOD_ID, "textures/entity/boo/boo.png");
    private static final Identifier SHY = new Identifier(Main.MOD_ID, "textures/entity/boo/shy.png");
    private static final Identifier TAMED = new Identifier(Main.MOD_ID, "textures/entity/boo/tamed.png");
    private static final Identifier FACE_0 = new Identifier(Main.MOD_ID, "textures/entity/boo/face_0.png");
    private static final Identifier FACE_1 = new Identifier(Main.MOD_ID, "textures/entity/boo/face_1.png");
    private static final Identifier FACE_2 = new Identifier(Main.MOD_ID, "textures/entity/boo/face_2.png");
    private static final Identifier FACE_3 = new Identifier(Main.MOD_ID, "textures/entity/boo/face_3.png");
    private static final Identifier FACE_4 = new Identifier(Main.MOD_ID, "textures/entity/boo/face_4.png");

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
            return SHY;
        } else if (booEntity.isTamed()) {
            if (booEntity.isInSittingPose()) {
                if (booEntity.getBooFace() == 0)
                    return FACE_0;
                else if (booEntity.getBooFace() == 1)
                    return FACE_1;
                else if (booEntity.getBooFace() == 2)
                    return FACE_2;
                else if (booEntity.getBooFace() == 3)
                    return FACE_3;
                else
                    return FACE_4;
            } else return TAMED;
        } else return BOO;
    }
}
