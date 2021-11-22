package com.dayofpi.sbw_main.entity.registry;

import com.dayofpi.sbw_main.entity.model.*;
import com.dayofpi.sbw_main.entity.renderer.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

@Environment(EnvType.CLIENT)
public class EntityClient {
    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(ModEntities.TURNIP, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.HAMMER, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOMB, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FIREBALL, FlowerFireballRenderer::new);
        EntityRendererRegistry.register(ModEntities.ENEMY_FIREBALL, EnemyFireballRenderer::new);
        EntityRendererRegistry.register(ModEntities.ICEBALL, IceballRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPIRIT, EmptyEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.BUZZY_SHELL, (context) -> new BuzzyShellRenderer<>(context, ModelLayers.BUZZY_SHELL));
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BUZZY_SHELL, BuzzyShellModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.MOO_MOO, MooMooRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.MOO_MOO, MooMooModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.GOOMBA, GoombaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.GOOMBA, GoombaModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.PARAGOOMBA, ParagoombaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.PARAGOOMBA, ParagoombaModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.KOOPA_TROOPA, KoopaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.KOOPA, KoopaModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.KOOPA_SADDLE, KoopaModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.PARATROOPA, KoopaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.PARATROOPA, KoopaModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.BOB_OMB, BobOmbRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BOB_OMB, BobOmbModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.BOO, BooRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BOO, BooModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.HAMMER_BRO, HammerBroRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.HAMMER_BRO, HammerBroModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.FIRE_BRO, FireBroRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.FIRE_BRO, HammerBroModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.BUZZY_BEETLE, BuzzyRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPIKE_TOP, SpikeTopRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BUZZY_BEETLE, BuzzyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BUZZY_BEETLE_SADDLE, BuzzyModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.NIPPER_PLANT, NipperPlantRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.NIPPER_PLANT, NipperPlantModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.STINGBY, StingbyRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.STINGBY, StingbyModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.ROTTEN_MUSHROOM, RottenMushroomRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.ROTTEN_MUSHROOM, RottenMushroomModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.FAKE_BLOCK, FakeBlockRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.FAKE_BLOCK, FakeBlockModel::getTexturedModelData);
    }
}
