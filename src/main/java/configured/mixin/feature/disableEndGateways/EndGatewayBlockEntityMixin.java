package configured.mixin.feature.disableEndGateways;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import configured.Settings;

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayBlockEntityMixin {

    @ModifyReturnValue(method = "needsCooldownBeforeTeleporting", at = @At(value = "RETURN"))
    private boolean configured$conditionalGatewayTeleport(boolean original) {
        return original || Settings.disableEndGateways;
    }

}
