package configured.mixin.feature.simulationDistance;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import configured.Settings;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DedicatedPlayerList.class)
public class DedicatedPlayerListMixin {
    @ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/dedicated/DedicatedServer;simulationDistance()I"))
    private static int configured$simulationDistance$init(int original) {
        return Settings.simulationDistance > 0 ? Settings.simulationDistance : original;
    }
}
