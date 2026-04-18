package configured.mixin.feature.disableEndGateways;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import configured.Settings;

@Mixin(TheEndGatewayBlockEntity.class)
public class TheEndGatewayBlockEntityMixin {

    @ModifyReturnValue(method = "isCoolingDown", at = @At(value = "RETURN"))
    private boolean configured$conditionalGatewayTeleport(boolean original) {
        return original || Settings.disableEndGateways;
    }

}
