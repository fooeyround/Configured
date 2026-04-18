package configured.mixin.feature.disableEndPortalFrameFilling;

import configured.Settings;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {

    @Inject(method="useOn",at=@At("HEAD"), cancellable = true)
    private void configured$conditionalEndPortalFrameFilling(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (Settings.disableEndPortalFrameFilling) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

}
