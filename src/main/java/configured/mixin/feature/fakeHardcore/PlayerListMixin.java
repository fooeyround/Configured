package configured.mixin.feature.fakeHardcore;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import configured.Settings;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {

    @ModifyExpressionValue(
            method = "placeNewPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/LevelData;isHardcore()Z"
            )
    )
    private boolean configured$fakeHardcore(boolean original) {
        return original || Settings.fakeHardcore;
    }




}
