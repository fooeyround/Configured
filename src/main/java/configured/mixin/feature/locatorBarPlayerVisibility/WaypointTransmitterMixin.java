package configured.mixin.feature.locatorBarPlayerVisibility;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import configured.Settings;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.waypoints.WaypointTransmitter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WaypointTransmitter.class)
public interface WaypointTransmitterMixin {

    @ModifyReturnValue(method = "doesSourceIgnoreReceiver", at = @At("RETURN"))
    private static boolean configured$locatorBarPlayerVisibility$doesSourceIgnoreReceiver(boolean original, @Local(name = "source") LivingEntity source, @Local(name = "receiver") ServerPlayer receiver) {
        return switch (Settings.locatorBarPlayerVisibility) {
            case TEAM -> original || source.getTeam() == null || receiver.getTeam() == null || source.getTeam() != receiver.getTeam();
            case TEAM_OR_BOTH_WITHOUT_TEAM -> original || source.getTeam() != receiver.getTeam();
            case NO_OTHER_PLAYERS -> original || source instanceof ServerPlayer;
            case null, default -> original;
        };
    }

}
