package com.dayofpi.super_block_world.registry.main;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.*;
import com.dayofpi.super_block_world.common.entities.abst.AbstractBoo;
import com.dayofpi.super_block_world.common.entities.abst.AbstractBro;
import com.dayofpi.super_block_world.common.entities.abst.AbstractBuzzy;
import com.dayofpi.super_block_world.common.entities.abst.AbstractShell;
import com.dayofpi.super_block_world.common.entities.mob.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.SpawnRestriction.Location;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
public class EntityInit {
    public static final EntityType<TurnipEntity> TURNIP = FabricEntityTypeBuilder.<TurnipEntity>create(SpawnGroup.MISC, TurnipEntity::new).dimensions(EntityDimensions.fixed(0.8F, 0.8F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
    public static final EntityType<StarBitEntity> STAR_BIT = FabricEntityTypeBuilder.<StarBitEntity>create(SpawnGroup.MISC, StarBitEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
    public static final EntityType<HammerEntity> HAMMER = FabricEntityTypeBuilder.<HammerEntity>create(SpawnGroup.MISC, HammerEntity::new).dimensions(EntityDimensions.fixed(0.8F, 0.8F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
    public static final EntityType<BombEntity> BOMB = FabricEntityTypeBuilder.<BombEntity>create(SpawnGroup.MISC, BombEntity::new).dimensions(EntityDimensions.fixed(0.8F, 0.8F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
    public static final EntityType<FireballEntity> FIREBALL = FabricEntityTypeBuilder.<FireballEntity>create(SpawnGroup.MISC, FireballEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeBlocks(4).trackedUpdateRate(20).build();
    public static final EntityType<FireBroFireballEntity> FIRE_BRO_FIREBALL = FabricEntityTypeBuilder.<FireBroFireballEntity>create(SpawnGroup.MISC, FireBroFireballEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeBlocks(4).trackedUpdateRate(20).build();
    public static final EntityType<IceballEntity> ICEBALL = FabricEntityTypeBuilder.<IceballEntity>create(SpawnGroup.MISC, IceballEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeBlocks(4).trackedUpdateRate(20).build();
    public static final EntityType<IceBroIceballEntity> ICE_BRO_ICEBALL = FabricEntityTypeBuilder.<IceBroIceballEntity>create(SpawnGroup.MISC, IceBroIceballEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeBlocks(4).trackedUpdateRate(20).build();

    public static final EntityType<SpiritEntity> SPIRIT = FabricEntityTypeBuilder.<SpiritEntity>create(SpawnGroup.MISC, SpiritEntity::new).dimensions(EntityDimensions.fixed(1.0F, 1.0F)).trackRangeBlocks(10).trackedUpdateRate(10).fireImmune().build();

    public static final EntityType<VolcanicDebrisEntity> VOLCANIC_DEBRIS = FabricEntityTypeBuilder.createMob()
            .entityFactory(VolcanicDebrisEntity::new).spawnGroup(SpawnGroup.MISC)
            .dimensions(EntityDimensions.fixed(0.9F, 0.9F))
            .fireImmune().build();
    public static final EntityType<BuzzyShellEntity> BUZZY_SHELL = FabricEntityTypeBuilder.createLiving()
            .entityFactory(BuzzyShellEntity::new).spawnGroup(SpawnGroup.MISC)
            .dimensions(EntityDimensions.fixed(1.0F, 0.8F)).build();

    public static final EntityType<MooMooEntity> MOO_MOO = FabricEntityTypeBuilder.createMob()
            .entityFactory(MooMooEntity::new)
            .spawnGroup(SpawnGroup.CREATURE)
            .dimensions(EntityDimensions.changing(1.2F, 1.2F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MooMooEntity::isValidNaturalSpawn)
            .build();

    public static final EntityType<ToadEntity> TOAD = FabricEntityTypeBuilder.createMob()
            .entityFactory(ToadEntity::new)
            .spawnGroup(SpawnGroup.CREATURE)
            .dimensions(EntityDimensions.changing(0.6F, 1.8F))
            .build();

    public static final EntityType<MummyToadEntity> MUMMY_TOAD = FabricEntityTypeBuilder.createMob()
            .entityFactory(MummyToadEntity::new)
            .spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.changing(0.6F, 1.8F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MummyToadEntity::canSpawn)
            .build();

    public static final EntityType<GladGoombaEntity> GLAD_GOOMBA = FabricEntityTypeBuilder.createMob().spawnGroup(SpawnGroup.CREATURE)
            .entityFactory(GladGoombaEntity::new)
            .dimensions(EntityDimensions.changing(0.7F, 0.7F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn)
            .build();

    public static final EntityType<GladParagoombaEntity> GLAD_PARAGOOMBA = FabricEntityTypeBuilder.createMob().spawnGroup(SpawnGroup.CREATURE)
            .entityFactory(GladParagoombaEntity::new)
            .dimensions(EntityDimensions.changing(0.7F, 0.7F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn)
            .build();

    public static final EntityType<GoombaEntity> GOOMBA = FabricEntityTypeBuilder.createMob().spawnGroup(SpawnGroup.MONSTER)
            .entityFactory(GoombaEntity::new)
            .dimensions(EntityDimensions.changing(0.9F, 0.9F)).spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GoombaEntity::canSpawn)
            .build();

    public static final EntityType<ParagoombaEntity> PARAGOOMBA = FabricEntityTypeBuilder.createMob().entityFactory(ParagoombaEntity::new).spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.changing(1.0F, 0.9F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, ParagoombaEntity::canParagoombaSpawn)
            .build();

    public static final EntityType<KoopaEntity> KOOPA_TROOPA = FabricEntityTypeBuilder.createMob()
            .entityFactory(KoopaEntity::new).spawnGroup(SpawnGroup.CREATURE)
            .dimensions(EntityDimensions.fixed(1.1F, 1.5F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, KoopaEntity::canSpawn)
            .build();

    public static final EntityType<ParatroopaEntity> PARATROOPA = FabricEntityTypeBuilder.createMob()
            .entityFactory(ParatroopaEntity::new).spawnGroup(SpawnGroup.CREATURE)
            .dimensions(EntityDimensions.fixed(1.1F, 1.5F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ParatroopaEntity::canSpawn)
            .build();

    public static final EntityType<BobOmbEntity> BOB_OMB = FabricEntityTypeBuilder.createMob().entityFactory(BobOmbEntity::new).spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.fixed(0.6F, 0.77F)).spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BobOmbEntity::canSpawn).build();

    public static final EntityType<BooEntity> BOO = FabricEntityTypeBuilder.createMob()
            .entityFactory(BooEntity::new)
            .spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.fixed(0.7F,0.7F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractBoo::canSpawn)
            .fireImmune().build();

    public static final EntityType<HammerBroEntity> HAMMER_BRO = FabricEntityTypeBuilder.createMob()
            .entityFactory(HammerBroEntity::new)
            .spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.fixed(0.8F, 1.7F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractBro::canSpawn)
            .build();

    public static final EntityType<FireBroEntity> FIRE_BRO = FabricEntityTypeBuilder.createMob()
            .entityFactory(FireBroEntity::new)
            .spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.fixed(0.8F, 1.7F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractBro::canSpawn)
            .build();

    public static final EntityType<IceBroEntity> ICE_BRO = FabricEntityTypeBuilder.createMob()
            .entityFactory(IceBroEntity::new)
            .spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.fixed(0.8F, 1.7F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractBro::canSpawn)
            .build();

    public static final EntityType<BuzzyEntity> BUZZY_BEETLE = FabricEntityTypeBuilder.createMob()
            .entityFactory(BuzzyEntity::new)
            .spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.changing(1.2F, 0.9F))
            .build();

    public static final EntityType<SpikeTopEntity> SPIKE_TOP = FabricEntityTypeBuilder.createMob()
            .entityFactory(SpikeTopEntity::new)
            .spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.changing(1.2F, 0.9F))
            .build();

    public static final EntityType<NipperPlantEntity> NIPPER_PLANT = FabricEntityTypeBuilder.createMob()
            .entityFactory(NipperPlantEntity::new)
            .spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.fixed(0.4F, 0.4F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, NipperPlantEntity::canSpawn)
            .build();

    public static final EntityType<StingbyEntity> STINGBY = FabricEntityTypeBuilder.createMob()
            .entityFactory(StingbyEntity::new)
            .spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.fixed(0.8F, 0.8F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, StingbyEntity::canSpawn)
            .build();

    public static final EntityType<RottenMushroomEntity> ROTTEN_MUSHROOM = FabricEntityTypeBuilder.createMob()
            .entityFactory(RottenMushroomEntity::new).spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.fixed(0.7F, 0.8F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RottenMushroomEntity::canSpawn)
            .fireImmune().build();

    public static final EntityType<FuzzyEntity> FUZZY = FabricEntityTypeBuilder.createMob()
            .entityFactory(FuzzyEntity::new).spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.fixed(0.72F, 0.72F))
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FuzzyEntity::canSpawn)
            .fireImmune().build();

    public static final EntityType<ThwompEntity> THWOMP = FabricEntityTypeBuilder.createMob()
            .entityFactory(ThwompEntity::new).spawnGroup(SpawnGroup.MONSTER)
            .dimensions(EntityDimensions.fixed(1.0F, 2.0F))
            .fireImmune().build();

    public static final EntityType<FakeBlockEntity> FAKE_BLOCK = FabricEntityTypeBuilder.createMob()
            .entityFactory(FakeBlockEntity::new).spawnGroup(SpawnGroup.MISC)
            .dimensions(EntityDimensions.fixed(0.85F, 0.85F))
            .build();

    public static void register() {
        register("turnip", TURNIP);
        register("star_bit", STAR_BIT);
        register("hammer", HAMMER);
        register("bomb", BOMB);
        register("fireball", FIREBALL);
        register("enemy_fireball", FIRE_BRO_FIREBALL);
        register("enemy_iceball", ICE_BRO_ICEBALL);
        register("iceball", ICEBALL);
        register("ghost", SPIRIT);
        registerMob("buzzy_shell", BUZZY_SHELL, AbstractShell.createAttributes());
        registerMob("volcanic_debris", VOLCANIC_DEBRIS, VolcanicDebrisEntity.createAttributes());
        registerMob("moo_moo", MOO_MOO, MooMooEntity.createAttributes());
        registerMob("toad", TOAD, ToadEntity.createAttributes());
        registerMob("mummy_toad", MUMMY_TOAD, MummyToadEntity.createAttributes());
        registerMob("glad_goomba", GLAD_GOOMBA, GladGoombaEntity.createAttributes());
        registerMob("glad_paragoomba", GLAD_PARAGOOMBA, GladParagoombaEntity.createAttributes());
        registerMob("goomba", GOOMBA, GoombaEntity.createAttributes());
        registerMob("paragoomba", PARAGOOMBA, ParagoombaEntity.createAttributes());
        registerMob("koopa_troopa", KOOPA_TROOPA, KoopaEntity.createAttributes());
        registerMob("paratroopa", PARATROOPA, ParatroopaEntity.createAttributes());
        registerMob("bob_omb", BOB_OMB, BobOmbEntity.createAttributes());
        registerMob("boo", BOO, BooEntity.createAttributes());
        registerMob("hammer_bro", HAMMER_BRO, AbstractBro.createAttributes());
        registerMob("fire_bro", FIRE_BRO, AbstractBro.createAttributes());
        registerMob("ice_bro", ICE_BRO, AbstractBro.createAttributes());
        registerMob("buzzy_beetle", BUZZY_BEETLE, AbstractBuzzy.createAttributes());
        registerMob("spike_top", SPIKE_TOP, SpikeTopEntity.createAttributes());
        registerMob("nipper_plant", NIPPER_PLANT, NipperPlantEntity.createAttributes());
        registerMob("stingby", STINGBY, StingbyEntity.createAttributes());
        registerMob("rotten_mushroom", ROTTEN_MUSHROOM, RottenMushroomEntity.createAttributes());
        registerMob("fuzzy", FUZZY, FuzzyEntity.createAttributes());
        registerMob("thwomp", THWOMP, ThwompEntity.createAttributes());
        registerMob("fake_block", FAKE_BLOCK, FakeBlockEntity.createAttributes());
    }

    private static <T extends Entity> void register(String id, EntityType<T> type) {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MOD_ID, id), type);
    }

    @SuppressWarnings("all")
    private static <T extends LivingEntity> void registerMob(String id, EntityType<T> type, DefaultAttributeContainer.Builder builder) {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MOD_ID, id), type);
        FabricDefaultAttributeRegistry.register(type, builder);
    }
}