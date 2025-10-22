package configured.mixin.feature.maxPlayers;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import configured.Settings;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @ModifyExpressionValue(method = "checkCanJoin", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;getMaxPlayerCount()I"))
    private static int configured$maxPlayers$joinCheck(int original) {
        return Settings.maxPlayers < 0 ? original : Settings.maxPlayers;
    }

}
