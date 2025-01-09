package configured.mixin;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import configured.Configured;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @ModifyReturnValue(method = "startServer", at = @At("RETURN"))
    private static MinecraftServer configured$serverInitCollectInstance(MinecraftServer original) {
        Configured.MC_SERVER = original;
        return original;
    }
}
