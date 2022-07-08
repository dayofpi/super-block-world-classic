package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.PiranhaPlantModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.hostile.PiranhaPlantEntity;
import com.dayofpi.super_block_world.common.entities.hostile.PutridPiranhaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PiranhaPlantRenderer extends MobEntityRenderer<PiranhaPlantEntity, PiranhaPlantModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/piranha_plant.png");
    private static final Identifier PUTRID_PIRANHA_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/putrid_piranha.png");

    public PiranhaPlantRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PiranhaPlantModel(ctx.getPart(ModModelLayers.PIRANHA_PLANT)), 0.5F);
    }

    @Override
    public Identifier getTexture(PiranhaPlantEntity entity) {
        if (entity instanceof PutridPiranhaEntity)
            return PUTRID_PIRANHA_TEXTURE;
        return TEXTURE;
    }
}
