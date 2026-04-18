package configured.mixin.feature.disableEnd;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import configured.Settings;
import net.minecraft.world.level.block.EndPortalBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EndPortalBlock.class)
public class EndPortalBlockMixin {


    @ModifyExpressionValue(method = "entityInside", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;canUsePortal(Z)Z"))
    private boolean configured$conditionalEndPortalTravel(boolean original) {
        return !Settings.disableEnd && original;
    }



}
