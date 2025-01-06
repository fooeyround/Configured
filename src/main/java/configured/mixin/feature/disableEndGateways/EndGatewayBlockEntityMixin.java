package configured.mixin.feature.disableEndGateways;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import configured.Settings;

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayBlockEntityMixin {


    //#if MC > 12100
    @ModifyReturnValue(method = "needsCooldownBeforeTeleporting", at = @At(value = "RETURN"))
    //#elseif MC > 11700
    //$$ @ModifyExpressionValue(method = "serverTick", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    //#else
    //$$ @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    //#endif
    private boolean configured$conditionalGatewayTeleport(boolean original) {
        return original || Settings.disableEndGateways;
    }

}
