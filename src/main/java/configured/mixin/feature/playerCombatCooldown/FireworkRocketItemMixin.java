package configured.mixin.feature.playerCombatCooldown;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import configured.CombatCooldownHolder;
import configured.Settings;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin {

    @ModifyExpressionValue(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isFallFlying()Z"))
    private boolean configured$isFallFlying(boolean original, @Local(argsOnly = true, name = "player") Player player) {
        if (Settings.playerCombatDisableElytraFireworkRockets &&
                ((Object)player) instanceof CombatCooldownHolder cooldownHolder &&
                cooldownHolder.configured$getCombatCooldown() != 0) {
            return false;
        }
        return original;
    }

}
