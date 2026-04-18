package configured.mixin.feature.maxPlayers;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import configured.Settings;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @ModifyExpressionValue(method = "canPlayerLogin", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;getMaxPlayers()I"))
    private static int configured$maxPlayers$joinCheck(int original) {
        return Settings.maxPlayers < 0 ? original : Settings.maxPlayers;
    }

}
