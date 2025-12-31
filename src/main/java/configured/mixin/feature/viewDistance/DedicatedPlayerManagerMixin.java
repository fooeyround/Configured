package configured.mixin.feature.viewDistance;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import configured.Settings;
import net.minecraft.server.dedicated.DedicatedPlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DedicatedPlayerManager.class)
public class DedicatedPlayerManagerMixin {

    @ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/dedicated/MinecraftDedicatedServer;getViewDistance()I"))
    private static int configured$viewDistance$init(int original) {
        return Settings.viewDistance > 0 ? Settings.viewDistance : original;
    }
}
