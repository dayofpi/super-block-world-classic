package com.dayofpi.super_block_world.client.registry;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.GoKartModel;
import com.dayofpi.super_block_world.client.models.LilOinkModel;
import com.dayofpi.super_block_world.client.models.SpindriftModel;
import com.dayofpi.super_block_world.client.models.SuperPickaxModel;
import com.dayofpi.super_block_world.client.renderers.*;
import com.dayofpi.super_block_world.registry.ModBlockEntities;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EntityClient {
    public static final EntityModelLayer FLAG = new EntityModelLayer(new Identifier(Main.MOD_ID, "flag"), "main");
    public static final EntityModelLayer GO_KART = new EntityModelLayer(new Identifier(Main.MOD_ID, "go_kart"), "main");
    public static final EntityModelLayer LIL_OINK = new EntityModelLayer(new Identifier(Main.MOD_ID, "lil_oink"), "main");
    public static final EntityModelLayer SPINDRIFT = new EntityModelLayer(new Identifier(Main.MOD_ID, "spindrift"), "main");
    public static final EntityModelLayer SUPER_PICKAX = new EntityModelLayer(new Identifier(Main.MOD_ID, "super_pickax"), "main");
    public static final EntityModelLayer TRAMPOLINE_MINECART = new EntityModelLayer(new Identifier(Main.MOD_ID, "trampoline_minecart"), "main");

    @SuppressWarnings({"unchecked"})
    public static void renderEntities() {
        BlockEntityRendererRegistry.register(ModBlockEntities.CHINCHO_TORCH, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new ChinchoTorchRenderer());
        BlockEntityRendererRegistry.register(ModBlockEntities.FLAG, FlagRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.PLACED_ITEM, ctx -> new PlacedItemRenderer());
        EntityModelLayerRegistry.registerModelLayer(FLAG, FlagRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GO_KART, GoKartModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LIL_OINK, LilOinkModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SPINDRIFT, SpindriftModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SUPER_PICKAX, SuperPickaxModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TRAMPOLINE_MINECART, MinecartEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.GHOST_ESSENCE, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BABY_YOSHI, BabyYoshiRenderer::new);
        EntityRendererRegistry.register(ModEntities.BLOOPER, BlooperRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOB_OMB, BobOmbRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOB_OMB_BUDDY, BobOmbBuddyRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOMB, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CHEEP_CHEEP, CheepCheepRenderer::new);
        EntityRendererRegistry.register(ModEntities.DINO_RHINO, DinoRhinoRenderer::new);
        EntityRendererRegistry.register(ModEntities.FAKE_BLOCK, FakeBlockRenderer::new);
        EntityRendererRegistry.register(ModEntities.FIREBALL, ModFireballRenderer::new);
        EntityRendererRegistry.register(ModEntities.ICEBALL, IceballRenderer::new);
        EntityRendererRegistry.register(ModEntities.FUZZY, FuzzyRenderer::new);
        EntityRendererRegistry.register(ModEntities.GLAD_GOOMBA, GladGoombaRenderer::new);
        EntityRendererRegistry.register(ModEntities.GLAD_PARAGOOMBA, GladGoombaRenderer::new);
        EntityRendererRegistry.register(ModEntities.GOOMBA, GoombaRenderer::new);
        EntityRendererRegistry.register(ModEntities.GO_KART, GoKartRenderer::new);
        EntityRendererRegistry.register(ModEntities.HAMMER, HammerRenderer::new);
        EntityRendererRegistry.register(ModEntities.KING_BOB_OMB, KingBobOmbRenderer::new);
        EntityRendererRegistry.register(ModEntities.KING_BOO, KingBooRenderer::new);
        EntityRendererRegistry.register(ModEntities.KOOPA_TROOPA, KoopaRenderer::new);
        EntityRendererRegistry.register(ModEntities.LAUNCH_STAR, LaunchStarRenderer::new);
        EntityRendererRegistry.register(ModEntities.LIL_OINK, LilOinkRenderer::new);
        EntityRendererRegistry.register(ModEntities.MAILTOAD, MailtoadRenderer::new);
        EntityRendererRegistry.register(ModEntities.MECHAKOOPA, MechakoopaRenderer::new);
        EntityRendererRegistry.register(ModEntities.MECHAKOOPA_MISSILE, MechakoopaMissileRenderer::new);
        EntityRendererRegistry.register(ModEntities.MOO_MOO, MooMooRenderer::new);
        EntityRendererRegistry.register(ModEntities.MUD_TROOPER, MudTrooperRenderer::new);
        EntityRendererRegistry.register(ModEntities.MUMMY_ME, MummyMeRenderer::new);
        EntityRendererRegistry.register(ModEntities.PARAGOOMBA, GoombaRenderer::new);
        EntityRendererRegistry.register(ModEntities.PARATROOPA, KoopaRenderer::new);
        EntityRendererRegistry.register(ModEntities.PIRANHA_PLANT, PiranhaPlantRenderer::new);
        EntityRendererRegistry.register(ModEntities.PROPELLER_BLOCK, PropellerBlockRenderer::new);
        EntityRendererRegistry.register(ModEntities.PUTRID_PIRANHA, PiranhaPlantRenderer::new);
        EntityRendererRegistry.register(ModEntities.ROTTEN_MUSHROOM, RottenMushroomRenderer::new);
        EntityRendererRegistry.register(ModEntities.SHY_GUY, ShyGuyRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPINDRIFT, SpindriftRenderer::new);
        EntityRendererRegistry.register(ModEntities.STAMP, StampRenderer::new);
        EntityRendererRegistry.register(ModEntities.SUPER_HEART, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SUPER_PICKAX, SuperPickaxRenderer::new);
        EntityRendererRegistry.register(ModEntities.TOAD, ToadRenderer::new);
        EntityRendererRegistry.register(ModEntities.TRAMPOLINE_MINECART, TrampolineMinecartRenderer::new);
        EntityRendererRegistry.register(ModEntities.TURNIP, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.UNAGI, UnagiRenderer::new);
        EntityRendererRegistry.register(ModEntities.YOSHI, YoshiRenderer::new);
        EntityRendererRegistry.register(ModEntities.YOSHI_EGG, FlyingItemEntityRenderer::new);
    }
}
