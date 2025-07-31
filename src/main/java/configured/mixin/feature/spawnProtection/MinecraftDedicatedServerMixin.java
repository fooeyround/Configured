package configured.mixin.feature.spawnProtection;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import configured.Settings;
import net.minecraft.server.dedicated.DedicatedPlayerManager;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftDedicatedServer.class)
public class MinecraftDedicatedServerMixin {

    @ModifyReturnValue(method = "getSpawnProtectionRadius", at = @At("RETURN"))
    private static int configured$spawnProtection$getRadius(int original) {
        return Settings.spawnProtection >= 0 ? Settings.spawnProtection : original;
    }

}
