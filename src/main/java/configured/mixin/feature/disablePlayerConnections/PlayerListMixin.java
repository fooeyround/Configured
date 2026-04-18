package configured.mixin.feature.disablePlayerConnections;


import configured.Settings;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;
import java.util.List;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {

    @Shadow public abstract boolean isOp(final NameAndId nameAndId);

    @Shadow @Final private List<ServerPlayer> players;

    @Inject(
            method = "canPlayerLogin",
            at = @At("HEAD"),
            cancellable = true
    )
    private void configured$conditionalPlayerJoin(SocketAddress socketAddress, NameAndId playerConfigEntry, CallbackInfoReturnable<Component> cir) {
        switch (Settings.playerConnections) {
            case ALLOW_ALL -> {}
            case ALLOW_OPS -> {
                if (!this.isOp(playerConfigEntry)) {
                    cir.setReturnValue(Component.literal(Settings.disablePlayerConnectionsJoinMessage));
                }
            }
            case ALLOW_ONLY_NON_BLOCKED -> {
                if (Settings.playerConnectionBlockList.contains(playerConfigEntry.id().toString())) {
                    cir.setReturnValue(Component.literal(Settings.disablePlayerConnectionsJoinMessage));
                }
            }
        }


    }




}
