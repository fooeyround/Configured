package configured.mixin.feature.enableCommandBlock;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import configured.Settings;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftDedicatedServer.class)
public class MinecraftDedicatedServerMixin {

    @ModifyReturnValue(method = "areCommandBlocksEnabled", at = @At("RETURN"))
    private static boolean configured$forceEnableCommandBlock(boolean original) {
        return Settings.forceEnableCommandBlock || original;
    }

}
