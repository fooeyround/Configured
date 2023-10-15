package tech.lemonlime.configured.mixin.feature.disableEndPortalFrameFilling;

import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.lemonlime.configured.Settings;


@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {


    @Inject(method="useOnBlock",at=@At("HEAD"), cancellable = true)
    private void configured$conditionalEndPortalFrameFilling(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (Settings.disableEndPortalFrameFilling) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }


}
