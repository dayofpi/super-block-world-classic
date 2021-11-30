package com.dayofpi.sbw_main.entity.renderer;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.entity.model.BuzzyShellModel;
import com.dayofpi.sbw_main.entity.type.BuzzyShellEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
@Environment(EnvType.CLIENT)
public class BuzzyShellRenderer<T extends BuzzyShellEntity> extends EntityRenderer<T> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/buzzy_shell.png");
    protected final EntityModel<T> model;

    public BuzzyShellRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer) {
        super(ctx);
        this.shadowRadius = 0.7F;
        this.model = new BuzzyShellModel<>(ctx.getPart(layer));
    }

    @Override
    public void render(T entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0D, 1.0D, 0.0D);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - f));
        matrixStack.scale(-0.8F, -0.8F, 0.8F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(model.getLayer(TEXTURE));
        model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        model.setAngles(entity, g, 0.0F, -0.1F, 0.0F, 0.0F);
        matrixStack.pop();
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
