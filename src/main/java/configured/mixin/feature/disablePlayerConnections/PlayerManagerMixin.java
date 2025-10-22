package configured.mixin.feature.disablePlayerConnections;


import configured.Settings;
import net.minecraft.server.PlayerConfigEntry;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import configured.util.TextHelper;

import java.net.SocketAddress;
import java.util.List;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Shadow public abstract boolean isOperator(PlayerConfigEntry profile);

    @Shadow @Final private List<ServerPlayerEntity> players;

    @Inject(
            method = "checkCanJoin",
            at = @At("HEAD"),
            cancellable = true
    )
    private void configured$conditionalPlayerJoin(SocketAddress socketAddress, PlayerConfigEntry playerConfigEntry, CallbackInfoReturnable<Text> cir) {
        switch (Settings.playerConnections) {
            case ALLOW_ALL -> {}
            case ALLOW_OPS -> {
                if (!this.isOperator(playerConfigEntry)) {
                    cir.setReturnValue(TextHelper.literal(Settings.disablePlayerConnectionsJoinMessage));
                }
            }
            case ALLOW_ONLY_NON_BLOCKED -> {
                if (Settings.playerConnectionBlockList.contains(playerConfigEntry.id().toString())) {
                    cir.setReturnValue(TextHelper.literal(Settings.disablePlayerConnectionsJoinMessage));
                }
            }
        }


    }




}
