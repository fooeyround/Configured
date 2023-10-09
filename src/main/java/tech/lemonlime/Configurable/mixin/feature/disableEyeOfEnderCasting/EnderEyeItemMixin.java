package tech.lemonlime.Configurable.mixin.feature.disableEyeOfEnderCasting;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.lemonlime.Configurable.Settings;


@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {



    @Inject(method = "use", at= @At(value = "HEAD"), cancellable = true)
    private void configured$conditionalEnderEyeCasting(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (Settings.disableEyeOfEnderCasting) {
            cir.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
        }
    }


}
