package configured.mixin.feature.simulationDistance;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import configured.Settings;
import net.minecraft.server.dedicated.DedicatedPlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DedicatedPlayerManager.class)
public class DedicatedPlayerManagerMixin {

    @ModifyExpressionValue(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/server/dedicated/ServerPropertiesHandler;simulationDistance:I"))
    private static int configured$simulationDistance$init(int original) {
        return Settings.simulationDistance > 0 ? Settings.simulationDistance : original;
    }
}
