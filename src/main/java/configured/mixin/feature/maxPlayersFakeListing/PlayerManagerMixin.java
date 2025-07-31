package configured.mixin.feature.maxPlayersFakeListing;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import configured.Settings;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = PlayerManager.class, priority = 1050)
public class PlayerManagerMixin {

    @ModifyReturnValue(method = "getMaxPlayerCount", at = @At("RETURN"))
    private static int configured$maxPlayerCountFakeListing(int original) {
        return Settings.maxPlayersFakeListing < 0 ? original : Settings.maxPlayersFakeListing;
    }

}
