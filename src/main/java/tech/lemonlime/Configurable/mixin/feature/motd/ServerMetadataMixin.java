package tech.lemonlime.Configurable.mixin.feature.motd;


import net.minecraft.server.ServerMetadata;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.lemonlime.Configurable.Settings;
import tech.lemonlime.Configurable.util.TextHelper;

@Mixin(ServerMetadata.class)
public class ServerMetadataMixin {



    //#if MC >= 11900
    @Inject(method = "description", at=@At("HEAD"), cancellable = true)
    //#else
    //$$ @Inject(method = "getDescription", at=@At("HEAD"), cancellable = true)
    //#endif
    private void toggleEnd$configurableMOTD(CallbackInfoReturnable<Text> cir){


        if (!Settings.motd.equals("_")) {
            cir.setReturnValue(TextHelper.literal(Settings.motd.replace("_","")));
        }

    }



}
