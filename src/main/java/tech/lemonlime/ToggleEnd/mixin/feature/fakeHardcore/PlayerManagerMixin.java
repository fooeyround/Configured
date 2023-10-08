package tech.lemonlime.ToggleEnd.mixin.feature.fakeHardcore;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tech.lemonlime.ToggleEnd.Settings;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @ModifyExpressionValue(
            method = "onPlayerConnect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldProperties;isHardcore()Z"
            )
    )
    private boolean configured$fakeHardcore(boolean original) {
        return original || Settings.fakeHardcore;
    }




}
