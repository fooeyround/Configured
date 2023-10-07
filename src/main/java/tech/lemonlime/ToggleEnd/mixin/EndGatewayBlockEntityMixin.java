package tech.lemonlime.ToggleEnd.mixin;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tech.lemonlime.ToggleEnd.Settings;

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayBlockEntityMixin {

    @ModifyExpressionValue(method = "serverTick", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    private static boolean toggleEnd$conditionalGatewayTeleport(boolean original) {
        return original || Settings.disableEndGateways;
    }

}
