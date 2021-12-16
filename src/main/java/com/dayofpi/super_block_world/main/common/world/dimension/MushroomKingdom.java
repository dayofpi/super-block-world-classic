package com.dayofpi.super_block_world.main.common.world.dimension;

import com.dayofpi.super_block_world.main.Main;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

import static net.minecraft.server.command.CommandManager.literal;

public class MushroomKingdom {
    public static MultiNoiseBiomeSource.Preset PRESET;

    public static RegistryKey<World> WORLD_KEY;
    public static RegistryKey<ChunkGeneratorSettings> GENERATOR_SETTINGS_KEY;
    public static RegistryKey<DimensionOptions> DIMENSION_KEY;
    public static RegistryKey<DimensionType> DIMENSION_TYPE_KEY;

    public static DimensionType DIMENSION_TYPE;
    public static ServerWorld DIMENSION;

    public static void initializeDimension() {
        PRESET = BiomeParameters.MUSHROOM_KINGDOM;
        WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, Main.DIMENSION_ID);
        GENERATOR_SETTINGS_KEY = RegistryKey.of(Registry.CHUNK_GENERATOR_SETTINGS_KEY, Main.DIMENSION_ID);
        DIMENSION_KEY = RegistryKey.of(Registry.DIMENSION_KEY, Main.DIMENSION_ID);
        DIMENSION_TYPE_KEY = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, Main.DIMENSION_ID);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            DIMENSION_TYPE = server.getRegistryManager().get(Registry.DIMENSION_TYPE_KEY).get(DIMENSION_TYPE_KEY);
            DIMENSION = server.getWorld(WORLD_KEY);

            Main.LOGGER.info("Preparing dimension for server");

            if (DIMENSION == null) throw new AssertionError("Mario doesn't exist.");
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) ->
                dispatcher.register(literal("fabric_dimension_test").executes(MushroomKingdom::swapTargeted))
        );
    }

    private static int swapTargeted(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ServerWorld serverWorld = player.getWorld();
        ServerWorld modWorld = getModWorld(context);

        if (serverWorld != modWorld) {
            TeleportTarget target = new TeleportTarget(new Vec3d(0.5, 101, 0.5), Vec3d.ZERO, 0, 0);
            FabricDimensions.teleport(player, modWorld, target);

            if (player.world != modWorld) {
                throw new CommandException(new LiteralText("Teleportation failed!"));
            }

            modWorld.setBlockState(new BlockPos(0, 100, 0), Blocks.DIAMOND_BLOCK.getDefaultState());
            modWorld.setBlockState(new BlockPos(0, 101, 0), Blocks.TORCH.getDefaultState());
        } else {
            TeleportTarget target = new TeleportTarget(new Vec3d(0, 100, 0), Vec3d.ZERO,
                    (float) Math.random() * 360 - 180, (float) Math.random() * 360 - 180);
            FabricDimensions.teleport(player, getWorld(context, World.OVERWORLD), target);
        }

        return 1;
    }

    private static ServerWorld getModWorld(CommandContext<ServerCommandSource> context) {
        return getWorld(context, WORLD_KEY);
    }

    private static ServerWorld getWorld(CommandContext<ServerCommandSource> context, RegistryKey<World> dimensionRegistryKey) {
        return context.getSource().getServer().getWorld(dimensionRegistryKey);
    }
}
