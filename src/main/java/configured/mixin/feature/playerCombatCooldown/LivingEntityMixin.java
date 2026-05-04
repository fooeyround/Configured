package configured.mixin.feature.playerCombatCooldown;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import configured.CombatCooldownHolder;
import configured.Settings;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyReturnValue(method = "canGlide", at=@At("RETURN"))
    private boolean configured$playerCombatCooldown$canGlide(boolean original) {
        if (Settings.playerCombatDisableElytra &&
                ((Object)this) instanceof CombatCooldownHolder cooldownHolder &&
                cooldownHolder.configured$getCombatCooldown() != 0) {
            return false;
        }
        return original;
    }
}
