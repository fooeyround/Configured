package configured.mixin.feature.maxPlayers;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import configured.Settings;
import net.minecraft.server.PlayerManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @ModifyExpressionValue(method = {"checkCanJoin", "getMaxPlayerCount"}, at = @At(value = "FIELD", target = "Lnet/minecraft/server/PlayerManager;maxPlayers:I", opcode = Opcodes.GETFIELD))
    private static int configured$maxPlayers$joinCheck(int original) {
        return Settings.maxPlayers < 0 ? original : Settings.maxPlayers;
    }


}
