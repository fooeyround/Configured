package configured.mixin.feature.fakeHardcore;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import configured.Settings;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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
