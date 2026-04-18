package configured.mixin.feature.maxPlayersFakeListing;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import configured.Settings;
import net.minecraft.server.dedicated.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DedicatedServer.class)
public class DedicatedServerMixin {
    @ModifyReturnValue(method = "getMaxPlayers", at = @At("RETURN"))
    private static int configured$maxPlayersFakeListing$listing(int original) {
        return Settings.maxPlayersFakeListing < 0 ? original : Settings.maxPlayersFakeListing;
    }
}
