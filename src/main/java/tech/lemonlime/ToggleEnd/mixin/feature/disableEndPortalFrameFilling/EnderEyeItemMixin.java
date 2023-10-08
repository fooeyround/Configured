package tech.lemonlime.ToggleEnd.mixin.feature.disableEndPortalFrameFilling;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.lemonlime.ToggleEnd.Settings;


@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {


    @Inject(method="useOnBlock",at=@At("HEAD"), cancellable = true)
    private void configured$conditionalEndPortalFrameFilling(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (Settings.disableEndPortalFrameFilling) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }


}
