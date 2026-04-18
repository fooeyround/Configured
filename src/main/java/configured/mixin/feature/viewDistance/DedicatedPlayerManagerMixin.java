package configured.mixin.feature.viewDistance;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import configured.Settings;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DedicatedPlayerList.class)
public class DedicatedPlayerManagerMixin {
    @ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/dedicated/DedicatedServer;viewDistance()I"))
    private static int configured$viewDistance$init(int original) {
        return Settings.viewDistance > 0 ? Settings.viewDistance : original;
    }
}
