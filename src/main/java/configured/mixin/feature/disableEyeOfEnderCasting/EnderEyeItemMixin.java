package configured.mixin.feature.disableEyeOfEnderCasting;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import configured.Settings;


@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    private void configured$conditionalEnderEyeCasting(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (Settings.disableEyeOfEnderCasting) cir.setReturnValue(InteractionResult.PASS);
    }
}
