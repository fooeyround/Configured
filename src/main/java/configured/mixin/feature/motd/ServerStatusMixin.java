package configured.mixin.feature.motd;


import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.status.ServerStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import configured.Settings;

@Mixin(ServerStatus.class)
public class ServerStatusMixin {
    @Inject(method = "description", at=@At("HEAD"), cancellable = true)
    private void toggleEnd$configurableMOTD(CallbackInfoReturnable<Component> cir){
        if (!Settings.motd.equals("_")) {
            cir.setReturnValue(Component.literal(Settings.motd.replace("_"," ")));
        }
    }
}
