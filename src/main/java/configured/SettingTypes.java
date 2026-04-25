package configured;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.vehicle.minecart.MinecartTNT;

public class SettingTypes {

    public enum PlayerConnectionSetting {
        ALLOW_ALL,
        ALLOW_OPS,
        /// TODO: should this not be removed? Why use this instead of bans? This feature is meant as a general floodgate...
        ALLOW_ONLY_NON_BLOCKED
    }

    public enum LocatorBarPlayerVisibility {
        ALL,
        TEAM,
        TEAM_OR_BOTH_WITHOUT_TEAM,
        NO_OTHER_PLAYERS
    }

    public enum PlayerDamageMultiplierType {
        end_crystal,
        bad_respawn_point,
        tnt_minecart,
        ;

        public static PlayerDamageMultiplierType getSourceMultiplier(DamageSource source) {
            if (source.is(DamageTypes.PLAYER_EXPLOSION) && source.getDirectEntity() instanceof EndCrystal) {
                return end_crystal;
            }
            //TODO: split into bed/anchor
            if (source.is(DamageTypes.BAD_RESPAWN_POINT)) {
                return bad_respawn_point;
            }
            if (source.is(DamageTypes.EXPLOSION) && source.getDirectEntity() instanceof MinecartTNT) {
                return tnt_minecart;
            }
            return null;
        }
    }
}
