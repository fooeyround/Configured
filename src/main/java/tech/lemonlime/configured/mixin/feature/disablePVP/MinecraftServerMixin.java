package tech.lemonlime.configured.mixin.feature.disablePVP;


import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.lemonlime.configured.Settings;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {


    @Inject(method = "isPvpEnabled", at = @At(value = "HEAD"), cancellable = true)
    private void configured$conditionalPVP(CallbackInfoReturnable<Boolean> cir) {
        if (Settings.disablePVP) {
            cir.setReturnValue(false);
        }
    }

}
