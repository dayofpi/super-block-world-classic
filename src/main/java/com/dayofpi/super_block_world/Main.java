package com.dayofpi.super_block_world;

import com.dayofpi.super_block_world.block.ModBlockEntities;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.criterion.ModCriteria;
import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.entity.effects.StarPowerEffect;
import com.dayofpi.super_block_world.entity.entities.ToadSpecificSensor;
import com.dayofpi.super_block_world.item.ModItemGroup;
import com.dayofpi.super_block_world.item.ModItems;
import com.dayofpi.super_block_world.mixin.SensorTypeMixin;
import com.dayofpi.super_block_world.util.DispenserBehaviors;
import com.dayofpi.super_block_world.util.PowerUp;
import com.dayofpi.super_block_world.util.PowerUpCommand;
import com.dayofpi.super_block_world.world.ModFluids;
import com.dayofpi.super_block_world.world.WorldInit;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.UUID;

public class Main implements ModInitializer {
    public static final String MOD_ID = "super_block_world";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final StatusEffect STAR_POWER = new StarPowerEffect().addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID.randomUUID().toString(), 0.3D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.randomUUID().toString(), 3.0, EntityAttributeModifier.Operation.ADDITION);

    public static final SensorType<ToadSpecificSensor> TOAD_SPECIFIC_SENSOR = SensorTypeMixin.register("toad_specific_sensor", ToadSpecificSensor::new);
    public static final GameStateChangeS2CPacket.Reason KING_BOO_CURSE = new GameStateChangeS2CPacket.Reason(64);

    @Override
    public void onInitialize() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Main.MOD_ID, "star_power"), STAR_POWER);
        ModParticles.register();
        ModEntities.register();
        ModFluids.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModItems.register();
        ModItemGroup.register();
        ModTags.register();
        WorldInit.initialize();
        ModCriteria.register();
        DispenserBehaviors.register();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal("powerup").requires((source) -> source.hasPermissionLevel(2));
            PowerUp[] powerUps = PowerUp.values();
            for (PowerUp powerUp : powerUps) {
                literalArgumentBuilder.then((CommandManager.literal(powerUp.asString())
                        .executes(context -> PowerUpCommand.execute(context, Collections.singleton(context.getSource().getPlayerOrThrow()), powerUp))).then(CommandManager.argument("target", EntityArgumentType.players())
                                .executes(context -> PowerUpCommand.execute(context, EntityArgumentType.getPlayers(context, "target"), powerUp))));
            }
            dispatcher.register(literalArgumentBuilder);
        });

        LOGGER.info("Mod initialized");
    }
}
