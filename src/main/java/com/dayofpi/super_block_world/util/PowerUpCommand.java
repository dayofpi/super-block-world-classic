package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.common.entities.PowerUp;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

import java.util.Collection;
import java.util.Iterator;

public class PowerUpCommand {
    private static void sendFeedback(ServerCommandSource source, ServerPlayerEntity player, PowerUp powerUp) {
        Text text = Text.translatable("powerUp." + powerUp.asString());
        if (source.getEntity() == player) {
            source.sendFeedback(Text.translatable("commands.powerup.success.self", text), true);
        } else {
            if (source.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK)) {
                player.sendMessage(Text.translatable("powerUp.changed", text));
            }

            source.sendFeedback(Text.translatable("commands.powerup.success.other", player.getDisplayName(), text), true);
        }

    }

    public static int execute(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> targets, PowerUp powerUp) {
        int i = 0;
        Iterator<ServerPlayerEntity> var4 = targets.iterator();

        while(var4.hasNext()) {
            ServerPlayerEntity serverPlayerEntity = var4.next();
            serverPlayerEntity.getDataTracker().set(FormManager.POWER_UP, powerUp.asString());
            sendFeedback(context.getSource(), serverPlayerEntity, powerUp);
            ++i;
        }

        return i;
    }
}
