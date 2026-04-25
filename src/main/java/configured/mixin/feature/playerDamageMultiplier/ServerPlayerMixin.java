package configured.mixin.feature.playerDamageMultiplier;


import configured.Configured;
import configured.SettingTypes;
import configured.Settings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @ModifyArg(method = "hurtServer", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private static float configured$endCrystalPlayerDamageMultiplier$hurtServer(ServerLevel level, DamageSource source, float damage) {
        SettingTypes.PlayerDamageMultiplierType multiplierType = SettingTypes.PlayerDamageMultiplierType.getSourceMultiplier(source);
        if (multiplierType != null) {
            return damage * Settings.playerDamageMultiplier.getOrDefault(multiplierType, 1F);
        }
        return damage;
    }
}