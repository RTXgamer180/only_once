package com.enderdevelopment.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "broadcastMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/ChatType;Ljava/util/UUID;)V", at = @At("HEAD"), cancellable = true)
    private void onBroadcastMessage(Component message, ChatType type, UUID sender, CallbackInfo ci) {
        if (type == ChatType.SYSTEM && (message.getString().contains("joined the game") || message.getString().contains("left the game"))) {
            ci.cancel();
        }
    }
}