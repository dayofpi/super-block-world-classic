package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.KoopaShellModel;
import com.dayofpi.super_block_world.common.entities.mob.KoopaShellEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class KoopaShellRenderer<T extends KoopaShellEntity> extends LeadableEntityRenderer<T> {
    private static final Identifier GREEN = new Identifier(Main.MOD_ID, "textures/entity/shell/green.png");
    private static final Identifier GREEN_KOOPA = new Identifier(Main.MOD_ID, "textures/entity/shell/green_koopa.png");
    private static final Identifier RED = new Identifier(Main.MOD_ID, "textures/entity/shell/red.png");
    private static final Identifier RED_KOOPA = new Identifier(Main.MOD_ID, "textures/entity/shell/red_koopa.png");

    public KoopaShellRenderer(EntityRendererFactory.Context context) {
        super(context, new KoopaShellModel<>());
        this.shadowRadius = 0.4F;
    }

    @Override
    public Identifier getTexture(T entity) {
        if (entity.getKoopaType() == 0)
            return entity.hasMob() ? GREEN_KOOPA : GREEN;
        else
            return entity.hasMob() ? RED_KOOPA : RED;
    }
}
