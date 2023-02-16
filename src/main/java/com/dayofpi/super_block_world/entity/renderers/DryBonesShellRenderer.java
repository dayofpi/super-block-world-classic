package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.DryBonesShellModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.misc.DryBonesShellEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DryBonesShellRenderer extends MobEntityRenderer<DryBonesShellEntity, DryBonesShellModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/dry_bones_shell.png");

    public DryBonesShellRenderer(EntityRendererFactory.Context context) {
        super(context, new DryBonesShellModel(context.getPart(ModModelLayers.DRY_BONES_SHELL)), 0.5f);
    }

    @Override
    public Identifier getTexture(DryBonesShellEntity entity) {
        return TEXTURE;
    }
}
