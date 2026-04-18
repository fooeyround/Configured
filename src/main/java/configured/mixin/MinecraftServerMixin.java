package configured.mixin;


import configured.Configured;
import net.minecraft.server.dedicated.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServer.class)
public class MinecraftServerMixin {
    @Inject(method = "initServer", at = @At("HEAD"))
    private void configured$serverInitCollectInstance(CallbackInfoReturnable<Boolean> cir) {
        Configured.MC_SERVER = (DedicatedServer)((Object)this);
    }
}
