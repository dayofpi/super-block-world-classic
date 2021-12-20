package com.dayofpi.super_block_world.main.client.entity.renderer.mob;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.client.entity.model.BooModel;
import com.dayofpi.super_block_world.main.client.entity.ModelLayers;
import com.dayofpi.super_block_world.main.client.entity.renderer.feature.BooColorFeature;
import com.dayofpi.super_block_world.main.client.entity.renderer.feature.BooItem;
import com.dayofpi.super_block_world.main.common.entity.mob.ghost.BooEntity;
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
    private static final Identifier FACE_0 = new Identifier(Main.MOD_ID, "textures/entity/boo/face_cheeky.png");
    private static final Identifier FACE_1 = new Identifier(Main.MOD_ID, "textures/entity/boo/face_mad.png");
    private static final Identifier FACE_2 = new Identifier(Main.MOD_ID, "textures/entity/boo/face_scrunkly.png");
    private static final Identifier FACE_3 = new Identifier(Main.MOD_ID, "textures/entity/boo/face_poyo.png");

    public BooRenderer(EntityRendererFactory.Context context) {
        super(context, new BooModel<>(context.getPart(ModelLayers.BOO)), 0.5F);
        this.addFeature(new BooColorFeature(this));
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
                else return FACE_3;
            } else return BOO;
        } else return BOO;
    }
}
