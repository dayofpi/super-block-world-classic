package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.common.entities.projectile.GoopEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
@SuppressWarnings("deprecation")
public class GoopRenderer extends EntityRenderer<GoopEntity> {
    private final ItemRenderer itemRenderer;
    private final float scale;

    public GoopRenderer(EntityRendererFactory.Context context, float scale) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.scale = scale;
    }

    public GoopRenderer(EntityRendererFactory.Context context) {
        this(context, 1.0f);
    }

    @Override
    public void render(GoopEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.age < 2 && this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) < 12.25) {
            return;
        }
        matrices.push();
        matrices.scale(this.scale, this.scale, this.scale);
        matrices.multiply(this.dispatcher.getRotation());
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f));
        this.itemRenderer.renderItem(new ItemStack(entity.getBlockState().getBlock()), ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getId());
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(GoopEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}