package com.dayofpi.super_block_world.entity;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.entities.boss.KingBobOmbEntity;
import com.dayofpi.super_block_world.entity.entities.boss.KingBooEntity;
import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.entity.entities.boss.PeteyPiranhaEntity;
import com.dayofpi.super_block_world.entity.entities.hostile.*;
import com.dayofpi.super_block_world.entity.entities.misc.*;
import com.dayofpi.super_block_world.entity.entities.passive.*;
import com.dayofpi.super_block_world.entity.entities.projectile.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

import java.util.function.Supplier;

import static net.minecraft.entity.SpawnRestriction.Location;
import static net.minecraft.world.Heightmap.Type;

public class ModEntities {
    public static final EntityType<BabyYoshiEntity> BABY_YOSHI = createMob(
            BabyYoshiEntity::new, SpawnGroup.MISC,
            BabyYoshiEntity::createBabyYoshiAttributes,
            0.8F, 0.8F
    );
    public static final EntityType<BlockstepperEntity> BLOCKSTEPPER = createMob(
            BlockstepperEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            BlockstepperEntity::canSpawn,
            BlockstepperEntity::createBlockstepperAttributes,
            0.7F, 0.8F
    );
    public static final EntityType<BlooperEntity> BLOOPER = createMob(
            BlooperEntity::new, SpawnGroup.WATER_CREATURE,
            SpawnRestriction.Location.IN_WATER,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
            BlooperEntity::canBlooperSpawn,
            BlooperEntity::createBlooperAttributes,
            EntityDimensions.changing(0.75F, 0.75F),
            false
    );
    public static final EntityType<DarkGoombaEntity> DARK_GOOMBA = createMob(
            DarkGoombaEntity::new, SpawnGroup.MONSTER,
            GoombaEntity::createGoombaAttributes,
            EntityDimensions.changing(0.9F, 0.9F),
            false
    );
    public static final EntityType<DarkParagoombaEntity> DARK_PARAGOOMBA = createMob(
            DarkParagoombaEntity::new, SpawnGroup.MONSTER,
            ParagoombaEntity::createParagoombaAttributes,
            EntityDimensions.changing(0.9F, 0.9F),
            false
    );
    public static final EntityType<ForagerEntity> FORAGER = createMob(
            ForagerEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            PatrolEntity::canSpawn,
            ForagerEntity::createForagerAttributes,
            0.6F, 1.95F
    );
    public static final EntityType<PokeyEntity> POKEY = createMob(
            PokeyEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            PokeyEntity::canPokeySpawn,
            AbstractPokey::pokeyAttributes,
            EntityDimensions.fixed(0.7F,0.7F),
            false
    );
    public static final EntityType<SnowPokeyEntity> SNOW_POKEY = createMob(
            SnowPokeyEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            SnowPokeyEntity::canSnowPokeySpawn,
            AbstractPokey::pokeyAttributes,
            EntityDimensions.fixed(0.7F,0.7F),
            false
    );
    public static final EntityType<PokeySegmentEntity> POKEY_SEGMENT = createMob(
            PokeySegmentEntity::new, SpawnGroup.MISC,
            PokeySegmentEntity::pokeySegmentAttributes,
            EntityDimensions.fixed(0.7F,0.7F),
            false
    );
    public static final EntityType<MagikoopaEntity> MAGIKOOPA = createMob(
            MagikoopaEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            MagikoopaEntity::canSpawn,
            MagikoopaEntity::createMagikoopaAttributes,
            EntityDimensions.changing(0.6F, 1.3F),
            false
    );
    public static final EntityType<GoombaEntity> GOOMBA = createMob(
            GoombaEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            GoombaEntity::canGoombaSpawn,
            GoombaEntity::createGoombaAttributes,
            EntityDimensions.changing(0.9F, 0.9F),
            false
    );
    public static final EntityType<ParagoombaEntity> PARAGOOMBA = createMob(
            ParagoombaEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            ParagoombaEntity::canParagoombaSpawn,
            ParagoombaEntity::createParagoombaAttributes,
            EntityDimensions.changing(0.9F, 0.9F),
            false
    );
    public static final EntityType<YoshiEntity> YOSHI = createMob(
            YoshiEntity::new, SpawnGroup.MISC,
            YoshiEntity::createYoshiAttributes,
            0.85F, 1.8F
    );
    public static final EntityType<BobOmbBuddyEntity> BOB_OMB_BUDDY = createMob(
            BobOmbBuddyEntity::new, SpawnGroup.MISC,
            BobOmbBuddyEntity::createBobOmbBuddyAttributes,
            0.6F, 0.7F
    );
    public static final EntityType<BobOmbEntity> BOB_OMB = createMob(
            BobOmbEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING,
            BobOmbEntity::canSpawn,
            BobOmbEntity::createBobOmbAttributes,
            0.6F, 0.7F
    );
    public static final EntityType<BooEntity> BOO = createMob(
            BooEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            BooEntity::canSpawn,
            BooEntity::createBooAttributes,
            0.8F, 0.8F
    );
    public static final EntityType<BulletBillEntity> BULLET_BILL = createMob(
            BulletBillEntity::new, SpawnGroup.MONSTER,
            BulletBillEntity::createBulletBillAttributes,
            0.65F, 0.5F
    );
    public static final EntityType<BuzzyBeetleEntity> BUZZY_BEETLE = createMob(
            BuzzyBeetleEntity::new, SpawnGroup.MONSTER,
            Location.NO_RESTRICTIONS,
            Type.MOTION_BLOCKING_NO_LEAVES,
            AbstractBuzzy::canSpawn,
            AbstractBuzzy::createBuzzyAttributes,
            EntityDimensions.changing(1.2F, 1.0F),
            false
    );
    public static final EntityType<SpikeTopEntity> SPIKE_TOP = createMob(
            SpikeTopEntity::new, SpawnGroup.MONSTER,
            Location.NO_RESTRICTIONS,
            Type.MOTION_BLOCKING_NO_LEAVES,
            AbstractBuzzy::canSpawn,
            SpikeTopEntity::createSpikeTopAttributes,
            EntityDimensions.changing(1.2F, 1.0F),
            false
    );
    public static final EntityType<LilOinkEntity> LIL_OINK = createMob(
            LilOinkEntity::new, SpawnGroup.MISC,
            PigEntity::createPigAttributes,
            EntityDimensions.changing(0.7F, 0.7F),
            false
    );
    public static final EntityType<MooMooEntity> MOO_MOO = createMob(
            MooMooEntity::new, SpawnGroup.CREATURE,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            AnimalEntity::isValidNaturalSpawn,
            CowEntity::createCowAttributes,
            EntityDimensions.changing(1.1F, 1.2F),
            false
    );
    public static final EntityType<MudTrooperEntity> MUD_TROOPER = createMob(
            MudTrooperEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            RottenMushroomEntity::canSpawn,
            MudTrooperEntity::createMudTrooperAttributes,
            0.9F, 0.9F
    );
    public static final EntityType<MummyMeEntity> MUMMY_ME = createMob(
            MummyMeEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            MummyMeEntity::canSpawn,
            MummyMeEntity::createMummyMeAttributes,
            0.6F, 1.8F
    );
    public static final EntityType<UnagiEntity> UNAGI = createMob(
            UnagiEntity::new, SpawnGroup.MONSTER,
            Location.IN_WATER,
            Type.MOTION_BLOCKING_NO_LEAVES,
            UnagiEntity::canUnagiSpawn,
            UnagiEntity::createUnagiAttributes,
            0.8F, 0.55F
    );
    public static final EntityType<DryBonesShellEntity> DRY_BONES_SHELL = createMob(
            DryBonesShellEntity::new, SpawnGroup.MISC,
            DryBonesShellEntity::createDryBonesShellAttributes,
            EntityDimensions.fixed(1.0F, 0.8F),
            true
    );
    public static final EntityType<DryBonesEntity> DRY_BONES = createMob(
            DryBonesEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            DryBonesEntity::canSpawn,
            DryBonesEntity::createDryBonesAttributes,
            EntityDimensions.fixed(0.8F, 1.3F),
            false
    );
    public static final EntityType<ParabonesEntity> PARABONES = createMob(
            ParabonesEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING,
            DryBonesEntity::canSpawn,
            ParabonesEntity::createParabonesAttributes,
            EntityDimensions.fixed(0.8F, 1.3F),
            false
    );
    public static final EntityType<ToadEntity> TOAD = createMob(
            ToadEntity::new, SpawnGroup.MISC,
            ToadEntity::createToadAttributes,
            EntityDimensions.changing(0.6F, 1.8F),
            false
    );
    public static final EntityType<MailtoadEntity> MAILTOAD = createMob(
            MailtoadEntity::new, SpawnGroup.MISC,
            ToadEntity::createToadAttributes,
            EntityDimensions.changing(0.6F, 1.8F),
            false
    );
    public static final EntityType<SleepySheepEntity> SLEEPY_SHEEP = createMob(
            SleepySheepEntity::new, SpawnGroup.CREATURE,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            AnimalEntity::isValidNaturalSpawn,
            SleepySheepEntity::createSleepySheepAttributes,
            EntityDimensions.changing(0.7F, 0.7F),
            false
    );
    public static final EntityType<SpindriftEntity> SPINDRIFT = createMob(
            SpindriftEntity::new, SpawnGroup.CREATURE,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            SpindriftEntity::canSpawn,
            SpindriftEntity::createSpindriftAttributes,
            0.6F, 0.7F
    );
    public static final EntityType<ShyGuyEntity> SHY_GUY = createMob(
            ShyGuyEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            ShyGuyEntity::canSpawn,
            ShyGuyEntity::createShyGuyAttributes,
            0.8F, 0.8F
    );
    public static final EntityType<ThwompEntity> THWOMP = createMob(
            ThwompEntity::new, SpawnGroup.MONSTER,
            Location.NO_RESTRICTIONS,
            Type.MOTION_BLOCKING_NO_LEAVES,
            ThwompEntity::canSpawn,
            ThwompEntity::createThwompAttributes,
            EntityDimensions.fixed(1.0F, 2.0F), true
    );
    public static final EntityType<StingbyEntity> STINGBY = createMob(
            StingbyEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING,
            StingbyEntity::canSpawn,
            StingbyEntity::createStingbyAttributes,
            0.72F, 0.7F
    );
    public static final EntityType<FuzzyEntity> FUZZY = createMob(
            FuzzyEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            FuzzyEntity::canFuzzySpawn,
            FuzzyEntity::createFuzzyAttributes,
            EntityDimensions.fixed(0.72F, 0.72F),
            true
    );
    public static final EntityType<FakeBlockEntity> FAKE_BLOCK = createMob(
            FakeBlockEntity::new, SpawnGroup.MISC,
            FakeBlockEntity::createFakeBlockAttributes,
            0.85F, 0.85F
    );
    public static final EntityType<RottenMushroomEntity> ROTTEN_MUSHROOM = createMob(
            RottenMushroomEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            RottenMushroomEntity::canSpawn,
            RottenMushroomEntity::createRottenMushroomAttributes,
            0.7F, 0.7F
    );
    public static final EntityType<GooMeEntity> GOO_ME = createMob(
            GooMeEntity::new, SpawnGroup.MISC,
            GooMeEntity::createGooMeAttributes,
            0.6F, 1.8F
    );
    public static final EntityType<CheepCheepEntity> CHEEP_CHEEP = createMob(
            CheepCheepEntity::new, SpawnGroup.WATER_CREATURE,
            Location.IN_WATER,
            Type.MOTION_BLOCKING_NO_LEAVES,
            CheepCheepEntity::canCheepCheepSpawn,
            CheepCheepEntity::createCheepCheepAttributes,
            0.6F, 0.6F
    );
    public static final EntityType<KoopaTroopaEntity> KOOPA_TROOPA = createMob(
            KoopaTroopaEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND, Type.MOTION_BLOCKING_NO_LEAVES,
            KoopaTroopaEntity::canKoopaSpawn,
            KoopaTroopaEntity::createKoopaAttributes,
            0.8F, 1.3F
    );
    public static final EntityType<ParatroopaEntity> PARATROOPA = createMob(
            ParatroopaEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND, Type.MOTION_BLOCKING,
            ParatroopaEntity::canParatroopaSpawn,
            ParatroopaEntity::createParatroopaAttributes,
            0.8F, 1.3F
    );
    public static final EntityType<KoopaShellEntity> KOOPA_SHELL = createMob(
            KoopaShellEntity::new, SpawnGroup.MISC,
            KoopaShellEntity::createKoopaShellAtrributes,
            0.8F, 0.9F
    );
    public static final EntityType<PeteyPiranhaEntity> PETEY_PIRANHA = createMob(
            PeteyPiranhaEntity::new, SpawnGroup.MISC,
            PeteyPiranhaEntity::createPeteyPiranhaAttributes,
            2.0F, 3.4F
    );
    public static final EntityType<KingBobOmbEntity> KING_BOB_OMB = createMob(
            KingBobOmbEntity::new, SpawnGroup.MISC,
            KingBobOmbEntity::createKingBobOmbAttributes,
            2.6F, 3.4F
    );
    public static final EntityType<KingBooEntity> KING_BOO = createMob(
            KingBooEntity::new, SpawnGroup.MISC,
            KingBooEntity::createKingBooAttributes,
            2.0F, 2.0F
    );
    public static final EntityType<DinoRhinoEntity> DINO_RHINO = createMob(
            DinoRhinoEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            DinoRhinoEntity::canDinoRhinoSpawn,
            DinoRhinoEntity::createDinoRhinoAttributes,
            EntityDimensions.changing(1.25F, 1.35F),
            true
    );
    public static final EntityType<HammerBroEntity> HAMMER_BRO = createMob(
            HammerBroEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            AbstractBro::canSpawn,
            AbstractBro::createBroAttributes,
            0.7F, 1.5F
    );
    public static final EntityType<FireBroEntity> FIRE_BRO = createMob(
            FireBroEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            AbstractBro::canSpawn,
            AbstractBro::createBroAttributes,
            0.7F, 1.5F
    );
    public static final EntityType<IceBroEntity> ICE_BRO = createMob(
            IceBroEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            IceBroEntity::canSpawn,
            AbstractBro::createBroAttributes,
            0.7F, 1.5F
    );
    public static final EntityType<GladGoombaEntity> GLAD_GOOMBA = createMob(
            GladGoombaEntity::new, SpawnGroup.MISC,
            GladGoombaEntity::createGladGoombaAttributes,
            EntityDimensions.changing(0.7F, 0.7F),
            false
    );
    public static final EntityType<GladParagoombaEntity> GLAD_PARAGOOMBA = createMob(
            GladParagoombaEntity::new, SpawnGroup.MISC,
            GladParagoombaEntity::createGladParagoombaAttributes,
            EntityDimensions.changing(0.7F, 0.7F),
            false
    );
    public static final EntityType<MechakoopaEntity> MECHAKOOPA = createMob(
            MechakoopaEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            MechakoopaEntity::canMechakoopaSpawn,
            MechakoopaEntity::createMechakoopaAttributes,
            0.6F, 0.6F
    );
    public static final EntityType<LavaBubbleEntity> LAVA_BUBBLE = createMob(
            LavaBubbleEntity::new, SpawnGroup.MONSTER,
            Location.IN_LAVA,
            Type.MOTION_BLOCKING_NO_LEAVES,
            LavaBubbleEntity::canLavaBubbleSpawn,
            MagmaCubeEntity::createMagmaCubeAttributes,
            EntityDimensions.changing(2.0F, 2.0F),
            true
    );
    public static final EntityType<SpinyEntity> SPINY = createMob(
            SpinyEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            SpinyEntity::canSpawn,
            SpinyEntity::createSpinyAttributes,
            0.8F, 0.7F
    );
    public static final EntityType<PiranhaPlantEntity> PIRANHA_PLANT = createMob(
            PiranhaPlantEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            PiranhaPlantEntity::canPiranhaPlantSpawn,
            PiranhaPlantEntity::createPiranhaPlantAttributes,
            0.6F, 1.2F
    );
    public static final EntityType<PutridPiranhaEntity> PUTRID_PIRANHA = createMob(
            PutridPiranhaEntity::new, SpawnGroup.MONSTER,
            Location.ON_GROUND,
            Type.MOTION_BLOCKING_NO_LEAVES,
            PutridPiranhaEntity::canSpawn,
            PiranhaPlantEntity::createPiranhaPlantAttributes,
            0.6F, 1.2F
    );
    public static final EntityType<TweesterEntity> TWEESTER = createEntity(
            TweesterEntity::new, 0.8F, 1.8F, 8, 3, true
    );
    public static final EntityType<HammerEntity> HAMMER = createEntity(
            HammerEntity::new, 0.45F, 0.45F, 4, 10, false
    );
    public static final EntityType<ModFireballEntity> FIREBALL = createEntity(
            ModFireballEntity::new, 0.25F, 0.25F, 4, 10, true
    );
    public static final EntityType<GoldFireballEntity> GOLD_FIREBALL = createEntity(
            GoldFireballEntity::new, 0.3F, 0.3F, 4, 10, true
    );
    public static final EntityType<IceballEntity> ICEBALL = createEntity(
            IceballEntity::new, 0.25F, 0.25F, 4, 10, true
    );
    public static final EntityType<TurnipEntity> TURNIP = createEntity(
            TurnipEntity::new, 0.25F, 0.25F, 4, 10, false
    );
    public static final EntityType<SuperHeartEntity> SUPER_HEART = createEntity(
            SuperHeartEntity::new, 0.25F, 0.25F, 4, 10, false
    );
    public static final EntityType<YoshiEggEntity> YOSHI_EGG = createEntity(
            YoshiEggEntity::new, 0.5F, 0.5F, 4, 10, false
    );
    public static final EntityType<BombEntity> BOMB = createEntity(
            BombEntity::new, 0.25F, 0.25F, 4, 10, false
    );
    public static final EntityType<BoomerangEntity> BOOMERANG = createEntity(
            BoomerangEntity::new, 0.5F, 0.45F, 4, 10, false
    );
    public static final EntityType<MagicBeamEntity> MAGIC_BEAM = createEntity(
            MagicBeamEntity::new, 0.5F, 0.5F, 4, 10, false
    );
    public static final EntityType<GoopEntity> GOOP = createEntity(
            GoopEntity::new, 0.8F, 0.8F, 4, 10, false
    );
    public static final EntityType<BlackPaintEntity> BLACK_PAINT = createEntity(
            BlackPaintEntity::new, 0.8F, 0.8F, 4, 10, false
    );
    public static final EntityType<StarBitEntity> STAR_BIT = createEntity(
            StarBitEntity::new, 0.25F, 0.25F, 8, 10, false
    );
    public static final EntityType<MechakoopaMissileEntity> MECHAKOOPA_MISSILE = createEntity(
        MechakoopaMissileEntity::new, 0.6F, 0.6F, 4, 10, false
    );
    public static final EntityType<HomingFlameEntity> HOMING_FLAME = createEntity(
            HomingFlameEntity::new, 0.5F, 0.5F, 4, 10, true
    );
    public static final EntityType<GhostEssenceEntity> GHOST_ESSENCE = createEntity(
            GhostEssenceEntity::new, 1.0F, 1.0F, 8, 3, false
    );
    public static final EntityType<PropellerBlockEntity> PROPELLER_BLOCK = createEntity(
            PropellerBlockEntity::new, 0.98F, 0.98F, 10, 20, false
    );
    public static final EntityType<LaunchStarEntity> LAUNCH_STAR = createEntity(
            LaunchStarEntity::new, 0.9F, 0.9F, 8, 10, true
    );
    public static final EntityType<GoKartEntity> GO_KART = createEntity(
            GoKartEntity::new, 1.0F, 0.5F, 8, 3, false
    );
    public static final EntityType<TrampolineMinecartEntity> TRAMPOLINE_MINECART = createEntity(
            TrampolineMinecartEntity::new, 0.98F, 0.7F, 8, 3, false
    );
    public static final EntityType<StampEntity> STAMP = createEntity(
            StampEntity::new, 0.5F, 0.5F, 4, 10, false
    );

    private static void registerEntity(String name, EntityType<?> entity) {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MOD_ID, name), entity);
    }

    public static void register() {
        registerEntity("baby_yoshi", BABY_YOSHI);
        registerEntity("blockstepper", BLOCKSTEPPER);
        registerEntity("blooper", BLOOPER);
        registerEntity("bob_omb", BOB_OMB);
        registerEntity("bob_omb_buddy", BOB_OMB_BUDDY);
        registerEntity("bomb", BOMB);
        registerEntity("boo", BOO);
        registerEntity("boomerang", BOOMERANG);
        registerEntity("bullet_bill", BULLET_BILL);
        registerEntity("buzzy_beetle", BUZZY_BEETLE);
        registerEntity("cheep_cheep", CHEEP_CHEEP);
        registerEntity("dark_goomba", DARK_GOOMBA);
        registerEntity("dark_paragoomba", DARK_PARAGOOMBA);
        registerEntity("dino_rhino", DINO_RHINO);
        registerEntity("dry_bones", DRY_BONES);
        registerEntity("dry_bones_shell", DRY_BONES_SHELL);
        registerEntity("fake_block", FAKE_BLOCK);
        registerEntity("fire_bro", FIRE_BRO);
        registerEntity("fireball", FIREBALL);
        registerEntity("forager", FORAGER);
        registerEntity("fuzzy", FUZZY);
        registerEntity("ghost_essence", GHOST_ESSENCE);
        registerEntity("glad_goomba", GLAD_GOOMBA);
        registerEntity("glad_paragoomba", GLAD_PARAGOOMBA);
        registerEntity("go_kart", GO_KART);
        registerEntity("gold_fireball", GOLD_FIREBALL);
        registerEntity("goo_me", GOO_ME);
        registerEntity("pokey", POKEY);
        registerEntity("snow_pokey", SNOW_POKEY);
        registerEntity("pokey_segment", POKEY_SEGMENT);
        registerEntity("magikoopa", MAGIKOOPA);
        registerEntity("goomba", GOOMBA);
        registerEntity("tweester", TWEESTER);
        registerEntity("hammer", HAMMER);
        registerEntity("hammer_bro", HAMMER_BRO);
        registerEntity("homing_flame", HOMING_FLAME);
        registerEntity("ice_bro", ICE_BRO);
        registerEntity("iceball", ICEBALL);
        registerEntity("petey_piranha", PETEY_PIRANHA);
        registerEntity("king_bob_omb", KING_BOB_OMB);
        registerEntity("king_boo", KING_BOO);
        registerEntity("koopa_shell", KOOPA_SHELL);
        registerEntity("koopa_troopa", KOOPA_TROOPA);
        registerEntity("launch_star", LAUNCH_STAR);
        registerEntity("lava_bubble", LAVA_BUBBLE);
        registerEntity("lil_oink", LIL_OINK);
        registerEntity("magic_beam", MAGIC_BEAM);
        registerEntity("goop", GOOP);
        registerEntity("black_paint", BLACK_PAINT);
        registerEntity("mailtoad", MAILTOAD);
        registerEntity("mechakoopa", MECHAKOOPA);
        registerEntity("mechakoopa_missile", MECHAKOOPA_MISSILE);
        registerEntity("moo_moo", MOO_MOO);
        registerEntity("mud_trooper", MUD_TROOPER);
        registerEntity("mummy_me", MUMMY_ME);
        registerEntity("parabones", PARABONES);
        registerEntity("paragoomba", PARAGOOMBA);
        registerEntity("paratroopa", PARATROOPA);
        registerEntity("piranha_plant", PIRANHA_PLANT);
        registerEntity("propeller_block", PROPELLER_BLOCK);
        registerEntity("putrid_piranha", PUTRID_PIRANHA);
        registerEntity("rotten_mushroom", ROTTEN_MUSHROOM);
        registerEntity("shy_guy", SHY_GUY);
        registerEntity("sleepy_sheep", SLEEPY_SHEEP);
        registerEntity("spike_top", SPIKE_TOP);
        registerEntity("spindrift", SPINDRIFT);
        registerEntity("spiny", SPINY);
        registerEntity("stamp", STAMP);
        registerEntity("star_bit", STAR_BIT);
        registerEntity("stingby", STINGBY);
        registerEntity("super_heart", SUPER_HEART);
        registerEntity("thwomp", THWOMP);
        registerEntity("toad", TOAD);
        registerEntity("trampoline_minecart", TRAMPOLINE_MINECART);
        registerEntity("turnip", TURNIP);
        registerEntity("unagi", UNAGI);
        registerEntity("yoshi", YOSHI);
        registerEntity("yoshi_egg", YOSHI_EGG);
    }

    private static<T extends Entity> EntityType<T> createEntity(EntityType.EntityFactory<T> factory, float width, float height, int trackRange, int trackRate, boolean fireImmune) {
        FabricEntityTypeBuilder<T> builder = FabricEntityTypeBuilder.create().entityFactory(factory).spawnGroup(SpawnGroup.MISC).dimensions(EntityDimensions.fixed(width, height)).trackRangeChunks(trackRange).trackedUpdateRate(trackRate);
        if (fireImmune)
            builder.fireImmune();
        return builder.build();
    }
    private static <T extends MobEntity> EntityType<T> createMob(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, Supplier<DefaultAttributeContainer.Builder> defaultAttributes, float width, float height) {
        FabricEntityTypeBuilder.Mob<T> builder = createBuilder(factory, spawnGroup, defaultAttributes, EntityDimensions.fixed(width, height), false);
        if (factory instanceof ModBossEntity)
            builder.trackRangeChunks(10);
        return builder.build();
    }

    private static <T extends MobEntity> EntityType<T> createMob(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, Supplier<DefaultAttributeContainer.Builder> defaultAttributes, EntityDimensions dimensions, boolean fireImmune) {
        FabricEntityTypeBuilder.Mob<T> builder = createBuilder(factory, spawnGroup, defaultAttributes, dimensions, fireImmune);
        return builder.build();
    }

    private static <T extends MobEntity> EntityType<T> createMob(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, SpawnRestriction.Location location, Heightmap.Type heightmap, SpawnRestriction.SpawnPredicate<T> spawnPredicate, Supplier<DefaultAttributeContainer.Builder> defaultAttributes, EntityDimensions dimensions, boolean fireImmune) {
        FabricEntityTypeBuilder.Mob<T> builder = createBuilder(factory, spawnGroup, defaultAttributes, dimensions, fireImmune);
        builder.spawnRestriction(location, heightmap, spawnPredicate);
        return builder.build();
    }

    private static <T extends MobEntity> EntityType<T> createMob(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, SpawnRestriction.Location location, Heightmap.Type heightmap, SpawnRestriction.SpawnPredicate<T> spawnPredicate, Supplier<DefaultAttributeContainer.Builder> defaultAttributes, float width, float height) {
        FabricEntityTypeBuilder.Mob<T> builder = createBuilder(factory, spawnGroup, defaultAttributes, EntityDimensions.fixed(width, height), false);
        builder.spawnRestriction(location, heightmap, spawnPredicate);
        return builder.build();
    }

    private static <T extends MobEntity> FabricEntityTypeBuilder.Mob<T> createBuilder(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, Supplier<DefaultAttributeContainer.Builder> defaultAttributes, EntityDimensions dimensions, boolean fireImmune) {
        FabricEntityTypeBuilder.Mob<T> builder = FabricEntityTypeBuilder.createMob().entityFactory(factory).spawnGroup(spawnGroup).defaultAttributes(defaultAttributes).dimensions(dimensions);
        if (fireImmune)
            builder.fireImmune();
        return builder;
    }
}
