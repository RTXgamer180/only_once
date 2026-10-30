package com.enderdevelopment;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.ChatType;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("invisibleguy")
public class InvisibleGuyMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Register event handlers
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }
    @Override
    public void onInitialize() {
        // Initialization code
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        // Remove join message
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        // Remove leave message
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Entity killer = player.getKillCredit();

            if (killer != null && !killer.isAlive()) {
                // Replace name with "???(the invisible Guy)"
                event.setCanceled(true);
                player.getLevel().getServer().getPlayerList().broadcastMessage(
                    Component.literal("???(the invisible Guy) killed " + player.getName().getString()),
                    ChatType.SYSTEM,
                    player.getUUID()
                );
            }
        }
    }

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        Level level = player.getLevel();
        Vec3 playerPos = player.position();

        for (ServerPlayer otherPlayer : level.getServer().getPlayerList().getPlayers()) {
            if (otherPlayer != player && otherPlayer.distanceToSqr(playerPos) > 100) {
                // Hide chat message for players outside the 10-block radius
                event.setCanceled(true);
                break;
            }
        }
    }
}