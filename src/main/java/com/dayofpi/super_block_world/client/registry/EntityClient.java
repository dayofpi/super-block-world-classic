package com.dayofpi.super_block_world.client.registry;

import com.dayofpi.super_block_world.client.renderers.*;
import com.dayofpi.super_block_world.registry.ModBlockEntities;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

@Environment(EnvType.CLIENT)
public class EntityClient {
    public static void renderEntities() {
        BlockEntityRendererRegistry.register(ModBlockEntities.CHINCHO_TORCH, ChinchoTorchRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.DRY_BONES_PILE, DryBonesPileRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.FLAG, FlagRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.PLACED_ITEM, ctx -> new PlacedItemRenderer());
        EntityRendererRegistry.register(ModEntities.BABY_YOSHI, BabyYoshiRenderer::new);
        EntityRendererRegistry.register(ModEntities.BLOOPER, BlooperRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOB_OMB, BobOmbRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOB_OMB_BUDDY, BobOmbBuddyRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOMB, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOO, BooRenderer::new);
        EntityRendererRegistry.register(ModEntities.BLOCKSTEPPER, BlockstepperRenderer::new);
        EntityRendererRegistry.register(ModEntities.CHEEP_CHEEP, CheepCheepRenderer::new);
        EntityRendererRegistry.register(ModEntities.DINO_RHINO, DinoRhinoRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRY_BONES, DryBonesRenderer::new);
        EntityRendererRegistry.register(ModEntities.DRY_BONES_SHELL, DryBonesShellRenderer::new);
        EntityRendererRegistry.register(ModEntities.FAKE_BLOCK, FakeBlockRenderer::new);
        EntityRendererRegistry.register(ModEntities.FIREBALL, ModFireballRenderer::new);
        EntityRendererRegistry.register(ModEntities.FUZZY, FuzzyRenderer::new);
        EntityRendererRegistry.register(ModEntities.GHOST_ESSENCE, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.GLAD_GOOMBA, GladGoombaRenderer::new);
        EntityRendererRegistry.register(ModEntities.GLAD_PARAGOOMBA, GladGoombaRenderer::new);
        EntityRendererRegistry.register(ModEntities.GOOMBA, GoombaRenderer::new);
        EntityRendererRegistry.register(ModEntities.GOO_ME, GooMeRenderer::new);
        EntityRendererRegistry.register(ModEntities.GO_KART, GoKartRenderer::new);
        EntityRendererRegistry.register(ModEntities.HAMMER, HammerRenderer::new);
        EntityRendererRegistry.register(ModEntities.ICEBALL, IceballRenderer::new);
        EntityRendererRegistry.register(ModEntities.KING_BOB_OMB, KingBobOmbRenderer::new);
        EntityRendererRegistry.register(ModEntities.KING_BOO, KingBooRenderer::new);
        EntityRendererRegistry.register(ModEntities.KOOPA_TROOPA, KoopaTroopaRenderer::new);
        EntityRendererRegistry.register(ModEntities.LAUNCH_STAR, LaunchStarRenderer::new);
        EntityRendererRegistry.register(ModEntities.LAVA_BUBBLE, LavaBubbleRenderer::new);
        EntityRendererRegistry.register(ModEntities.LIL_OINK, LilOinkRenderer::new);
        EntityRendererRegistry.register(ModEntities.MAILTOAD, MailtoadRenderer::new);
        EntityRendererRegistry.register(ModEntities.MECHAKOOPA, MechakoopaRenderer::new);
        EntityRendererRegistry.register(ModEntities.MECHAKOOPA_MISSILE, MechakoopaMissileRenderer::new);
        EntityRendererRegistry.register(ModEntities.MOO_MOO, MooMooRenderer::new);
        EntityRendererRegistry.register(ModEntities.MUD_TROOPER, MudTrooperRenderer::new);
        EntityRendererRegistry.register(ModEntities.MUMMY_ME, MummyMeRenderer::new);
        EntityRendererRegistry.register(ModEntities.PARABONES, DryBonesRenderer::new);
        EntityRendererRegistry.register(ModEntities.PARAGOOMBA, GoombaRenderer::new);
        EntityRendererRegistry.register(ModEntities.PARATROOPA, KoopaTroopaRenderer::new);
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
