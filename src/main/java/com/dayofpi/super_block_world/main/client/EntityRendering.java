package com.dayofpi.super_block_world.main.client;

import com.dayofpi.super_block_world.main.client.entity.ModelLayers;
import com.dayofpi.super_block_world.main.client.entity.model.*;
import com.dayofpi.super_block_world.main.client.entity.renderer.mob.*;
import com.dayofpi.super_block_world.main.client.entity.renderer.other.*;
import com.dayofpi.super_block_world.main.registry.EntityRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

@Environment(EnvType.CLIENT)
public class EntityRendering {
    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(EntityRegistry.TURNIP, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.HAMMER, HammerRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.BOMB, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.FIREBALL, FlowerFireballRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ENEMY_FIREBALL, EnemyFireballRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ICEBALL, IceballRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.SPIRIT, EmptyEntityRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.BUZZY_SHELL, (context) -> new BuzzyShellRenderer<>(context, ModelLayers.BUZZY_SHELL));
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BUZZY_SHELL, BuzzyShellModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.MOO_MOO, MooMooRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.MOO_MOO, MooMooModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.GOOMBA, GoombaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.GOOMBA, GoombaModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.PARAGOOMBA, ParagoombaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.PARAGOOMBA, ParagoombaModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.KOOPA_TROOPA, KoopaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.KOOPA, KoopaModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.KOOPA_SADDLE, KoopaModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.PARATROOPA, KoopaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.PARATROOPA, KoopaModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.BOB_OMB, BobOmbRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BOB_OMB, BobOmbModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.BOO, BooRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BOO, BooModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.HAMMER_BRO, HammerBroRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.HAMMER_BRO, HammerBroModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.FIRE_BRO, FireBroRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.FIRE_BRO, HammerBroModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.BUZZY_BEETLE, BuzzyRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.SPIKE_TOP, SpikeTopRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BUZZY_BEETLE, BuzzyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BUZZY_BEETLE_SADDLE, BuzzyModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.NIPPER_PLANT, NipperPlantRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.NIPPER_PLANT, NipperPlantModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.STINGBY, StingbyRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.STINGBY, StingbyModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.ROTTEN_MUSHROOM, RottenMushroomRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.ROTTEN_MUSHROOM, RottenMushroomModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.THWOMP, ThwompRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.THWOMP, ThwompModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.FAKE_BLOCK, FakeBlockRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.FAKE_BLOCK, FakeBlockModel::getTexturedModelData);
    }
}
