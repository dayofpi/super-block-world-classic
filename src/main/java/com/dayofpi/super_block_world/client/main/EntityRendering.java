package com.dayofpi.super_block_world.client.main;

import com.dayofpi.super_block_world.client.models.*;
import com.dayofpi.super_block_world.client.renderers.*;
import com.dayofpi.super_block_world.client.renderers.other.*;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
@Environment(EnvType.CLIENT)
public class EntityRendering {
    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(EntityInit.TURNIP, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.STAR_BIT, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.HAMMER, HammerRenderer::new);
        EntityRendererRegistry.register(EntityInit.BOMB, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.FIREBALL, FlowerFireballRenderer::new);
        EntityRendererRegistry.register(EntityInit.FIRE_BRO_FIREBALL, EnemyFireballRenderer::new);
        EntityRendererRegistry.register(EntityInit.ICEBALL, IceballRenderer::new);
        EntityRendererRegistry.register(EntityInit.ICE_BRO_ICEBALL, EnemyIceballRenderer::new);
        EntityRendererRegistry.register(EntityInit.SPIRIT, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.BUZZY_SHELL, (context) -> new BuzzyShellRenderer<>(context, ModelLayers.BUZZY_SHELL));
        EntityRendererRegistry.register(EntityInit.VOLCANIC_DEBRIS, VolcanicDebrisRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BUZZY_SHELL, BuzzyShellModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.MOO_MOO, MooMooRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.MOO_MOO, MooMooModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.TOAD, ToadRenderer::new);
        EntityRendererRegistry.register(EntityInit.MUMMY_TOAD, MummyToadRenderer::new);
        EntityRendererRegistry.register(EntityInit.GLAD_GOOMBA, GladGoombaRenderer::new);
        EntityRendererRegistry.register(EntityInit.GLAD_PARAGOOMBA, GladGoombaRenderer::new);
        EntityRendererRegistry.register(EntityInit.GOOMBA, GoombaRenderer::new);
        EntityRendererRegistry.register(EntityInit.PARAGOOMBA, GoombaRenderer::new);
        EntityRendererRegistry.register(EntityInit.KOOPA_TROOPA, KoopaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.KOOPA, KoopaModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.KOOPA_SADDLE, KoopaModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.PARATROOPA, KoopaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.PARATROOPA, KoopaModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.BOB_OMB, BobOmbRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BOB_OMB, BobOmbModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.BOO, BooRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BOO, BooModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.HAMMER_BRO, HammerBroRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.HAMMER_BRO, HammerBroModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.FIRE_BRO, FireBroRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.FIRE_BRO, HammerBroModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.ICE_BRO, IceBroRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.ICE_BRO, HammerBroModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.BUZZY_BEETLE, BuzzyRenderer::new);
        EntityRendererRegistry.register(EntityInit.SPIKE_TOP, SpikeTopRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BUZZY_BEETLE, BuzzyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.BUZZY_BEETLE_SADDLE, BuzzyModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.NIPPER_PLANT, NipperPlantRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.NIPPER_PLANT, NipperPlantModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.STINGBY, StingbyRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.STINGBY, StingbyModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.ROTTEN_MUSHROOM, RottenMushroomRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.ROTTEN_MUSHROOM, RottenMushroomModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.FUZZY, FuzzyRenderer::new);
        EntityRendererRegistry.register(EntityInit.THWOMP, ThwompRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.THWOMP, ThwompModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.FAKE_BLOCK, FakeBlockRenderer::new);
    }
}
