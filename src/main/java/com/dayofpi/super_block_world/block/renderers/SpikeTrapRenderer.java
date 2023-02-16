package com.dayofpi.super_block_world.block.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.block.block_entities.SpikeTrapBE;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Environment(value= EnvType.CLIENT)
public class SpikeTrapRenderer implements BlockEntityRenderer<SpikeTrapBE> {
    public static final SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/spike_trap"));
    public static final SpriteIdentifier POWERED_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/spike_trap_on"));
    private final ModelPart spikes;
    private final ModelPart bone;

    public SpikeTrapRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(ModModelLayers.SPIKE_TRAP);
        this.spikes = modelPart.getChild("spikes");
        this.bone = modelPart.getChild("bone");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("spikes", ModelPartBuilder.create().uv(0, 32).cuboid(0.0F, -18.0F, -3.0F, 16.0F, 10.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 32).cuboid(0.0F, -18.0F, -13.0F, 16.0F, 10.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 32).cuboid(0.0F, -18.0F, -8.0F, 16.0F, 10.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 16).cuboid(13.0F, -18.0F, -16.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 16).cuboid(8.0F, -18.0F, -16.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 16).cuboid(3.0F, -18.0F, -16.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, 16.0F, 8.0F));

        modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(SpikeTrapBE entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light, int overlay) {
        World world = entity.getWorld();
        BlockPos blockPos = entity.getPos();
        if (world != null) {
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            matrices.translate(0.5, -1.5, -0.5);
            VertexConsumer consumer = TEXTURE.getVertexConsumer(provider, RenderLayer::getEntityCutout);
            BlockState state = world.getBlockState(blockPos);
            if (state.isOf(ModBlocks.SPIKE_TRAP) && state.get(Properties.POWERED)) {
                this.spikes.visible = true;
                consumer = POWERED_TEXTURE.getVertexConsumer(provider, RenderLayer::getEntityCutout);
            } else this.spikes.visible = false;
            this.spikes.render(matrices, consumer, light, overlay);
            this.bone.render(matrices, consumer, light, overlay);
            matrices.pop();
        }
    }
}
