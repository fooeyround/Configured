package tech.lemonlime.ToggleEnd.mixin.feature.disablePlayerConnections;


import com.mojang.authlib.GameProfile;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.lemonlime.ToggleEnd.Settings;

import java.net.SocketAddress;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {


    @Shadow public abstract boolean isOperator(GameProfile profile);



    @Inject(
            method = "checkCanJoin",
            at = @At("HEAD"),
            cancellable = true
    )
    private void configured$conditionalPlayerJoin(SocketAddress socketAddress, GameProfile gameProfile, CallbackInfoReturnable<Text> cir) {
         if (Settings.disablePlayerConnections && !this.isOperator(gameProfile)) {
         	cir.setReturnValue(Text.of(Settings.disablePlayerConnectionsJoinMessage));
         }
    }




}
