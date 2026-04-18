package configured;

public class SettingTypes {

    public enum PlayerConnectionSetting {
        ALLOW_ALL,
        ALLOW_OPS,
        /// TODO: should this not be removed? Why use this instead of bans? This feature is meant as a general floodgate...
        ALLOW_ONLY_NON_BLOCKED
    }


}
