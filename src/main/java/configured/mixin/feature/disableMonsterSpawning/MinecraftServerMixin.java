package configured.mixin.feature.disableMonsterSpawning;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import configured.Settings;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @ModifyReturnValue(method = "isMonsterSpawningEnabled", at = @At("RETURN"))
    private static boolean configured$disableMonsterSpawning(boolean original) {
        return !Settings.disableMonsterSpawning && original;
    }

}
