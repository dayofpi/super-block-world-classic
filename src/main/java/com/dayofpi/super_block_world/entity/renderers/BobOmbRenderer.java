package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.BobOmbModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.BobOmbEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BobOmbRenderer extends MobEntityRenderer<PathAwareEntity, BobOmbModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/bob_omb.png");

    public BobOmbRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BobOmbModel(ctx.getPart(ModModelLayers.BOB_OMB)), 0.4F);
    }

    @Override
    public Identifier getTexture(PathAwareEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(PathAwareEntity bobOmbEntity, MatrixStack matrixStack, float amount) {
        if (!(bobOmbEntity instanceof BobOmbEntity))
            return;
        float time = ((BobOmbEntity) bobOmbEntity).getClientFuseTime(amount);
        float h = 1.0F + MathHelper.sin(time * 100.0F) * time * 0.01F;
        time = MathHelper.clamp(time, 0.0F, 1.0F);
        time *= time;
        time *= time;
        float i = (1.0F + time * 0.4F) * h;
        float j = (1.0F + time * 0.1F) / h;
        matrixStack.scale(i, j, i);
    }
}
