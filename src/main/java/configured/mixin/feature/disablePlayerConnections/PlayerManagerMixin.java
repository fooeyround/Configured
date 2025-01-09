package configured.mixin.feature.disablePlayerConnections;


import com.mojang.authlib.GameProfile;
import configured.Settings;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import configured.util.TextHelper;

import java.net.SocketAddress;
import java.util.Set;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Shadow public abstract boolean isOperator(GameProfile profile);

    @Inject(
            method = "checkCanJoin",
            at = @At("HEAD"),
            cancellable = true
    )
    private void configured$conditionalPlayerJoin(SocketAddress socketAddress, GameProfile gameProfile, CallbackInfoReturnable<Text> cir) {
        switch (Settings.playerConnections) {
            case ALLOW_ALL -> {}
            case ALLOW_OPS -> {
                if (!this.isOperator(gameProfile)) {
                    cir.setReturnValue(TextHelper.literal(Settings.disablePlayerConnectionsJoinMessage));
                }
            }
            case ALLOW_ONLY_NON_BLOCKED -> {
                if (Settings.playerConnectionBlockList.contains(gameProfile.getId().toString())) {
                    cir.setReturnValue(TextHelper.literal(Settings.disablePlayerConnectionsJoinMessage));
                }
            }
        }


    }




}
