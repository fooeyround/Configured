package configured.mixin.feature.playerCombatCooldown;

import com.google.gson.FormattingStyle;
import configured.CombatCooldownHolder;
import configured.Settings;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements CombatCooldownHolder {

    @Unique private int configured$combatCooldown = 0;

    @Override
    public int configured$getCombatCooldown() {
        return configured$combatCooldown;
    }

    @Override
    public void configured$setCombatCooldown(int value) {
        this.configured$combatCooldown = value;
    }

    @Inject(method = "hurtServer", at= @At("HEAD"))
    private void configured$playerCombatCooldown$hurtServer(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (Settings.playerCombatCooldown != 0 && source.getEntity() instanceof ServerPlayer player && player instanceof CombatCooldownHolder sourceCooldownHolder && (player.getTeam() == null || player.getTeam() != ((ServerPlayer)(Object)this).getTeam())) {
            if (Settings.playerCombatCooldownForVictim) this.configured$combatCooldown = Settings.playerCombatCooldown;
            if (Settings.playerCombatCooldownForAttacker) sourceCooldownHolder.configured$setCombatCooldown(Settings.playerCombatCooldown);
        }
    }

    @Inject(method = "tick", at= @At("HEAD"))
    private void configured$playerCombatCooldown$tick(CallbackInfo ci) {
        if (this.configured$combatCooldown > 0) {
            this.configured$combatCooldown--;
            if (Settings.playerCombatCooldownShowInActionBar && ((Object)this) instanceof ServerPlayer player) {
                if (this.configured$combatCooldown == 0) {
                    player.sendOverlayMessage(Component.literal(""));
                } else {
                    player.sendOverlayMessage(
                            Component.literal("Combat Cooldown: ").append(Component.literal(this.configured$combatCooldown/20 + "s").withColor(0xFF5555))
                    );
                }
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at= @At("HEAD"))
    private void configured$playerCombatCooldown$addAdditionalSaveData(final ValueOutput output, CallbackInfo ci) {
        output.putInt("configured$combatCooldown", this.configured$combatCooldown);
    }

    @Inject(method = "readAdditionalSaveData", at= @At("HEAD"))
    private void configured$playerCombatCooldown$readAdditionalSaveData(final ValueInput input, CallbackInfo ci) {
        this.configured$combatCooldown = input.getIntOr("configured$combatCooldown", 0);
    }

}
