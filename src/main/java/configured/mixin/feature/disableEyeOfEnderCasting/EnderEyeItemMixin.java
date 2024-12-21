package configured.mixin.feature.disableEyeOfEnderCasting;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
//#if MC >= 12104
import net.minecraft.util.ActionResult;
//#else
//$$ import net.minecraft.util.TypedActionResult;
//#endif
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import configured.Settings;


@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {


    //#if MC >= 12104

    @Inject(method = "use", at= @At(value = "HEAD"), cancellable = true)
    private void configured$conditionalEnderEyeCasting(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
         if (Settings.disableEyeOfEnderCasting) cir.setReturnValue(ActionResult.PASS);
     }
    //#else
//$$   @Inject(method = "use", at= @At(value = "HEAD"), cancellable = true)
//$$   private void configured$conditionalEnderEyeCasting(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
//$$       if (Settings.disableEyeOfEnderCasting) cir.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
//$$   }
    //#endif

}
