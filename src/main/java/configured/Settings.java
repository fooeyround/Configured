package configured;


import dev.xpple.betterconfig.api.Config;

import java.util.ArrayList;

public class Settings {


    @Config(comment = "to default back to what was defined in server.properties, set it to _, to make it empty use __")
    public static String motd = "_";


    @Config(comment = "This makes the client think it is hardcore. respawn still works.")
    public static boolean fakeHardcore = false;


    @Config(comment = "This disables the ability to join the server. It does not kick players!\nAllowing non blocked uses the ")
    public static SettingTypes.PlayerConnectionSetting playerConnections = SettingTypes.PlayerConnectionSetting.ALLOW_ALL;


    @Config(adder = @Config.Adder(value = "playerListAdder"), remover = @Config.Remover(value = "playerListRemover"))
    public static ArrayList<String> playerConnectionBlockList = new ArrayList<>();
    public static void playerListAdder(String string) {
        if (Configured.MC_SERVER != null && Configured.MC_SERVER.getUserCache() != null){
            Configured.MC_SERVER.getUserCache().findByName(string).ifPresent(profile -> {
                String id = profile.getId().toString();
                if (!playerConnectionBlockList.contains(id)) {
                    playerConnectionBlockList.add(id);
                }
            });
        }
    }
    public static void playerListRemover(String string) {
        if (Configured.MC_SERVER != null && Configured.MC_SERVER.getUserCache() != null){
            Configured.MC_SERVER.getUserCache().findByName(string).ifPresent(profile -> playerConnectionBlockList.remove(profile.getId().toString()));
        }
    }


    public static String  disablePlayerConnectionsJoinMessage = "";


    @Config
    public static boolean disableEnd = false;


    @Config
    public static boolean disableEndPortalFrameFilling = false;


    @Config
    public static boolean disableEyeOfEnderCasting = false;


    @Config(comment="This feature is currently unavailable on 1.21, a fix will be made soon.")
    public static boolean disableEndGateways = false;


    @Config
    public static boolean disableNether = false;


    @Config(comment = "Disable Player v. Player combat")
    public static boolean disablePVP = false;


    @Config(comment = "Configure how long it takes for items to despawn (in ticks).\nThe default is 5 minutes (6000 ticks)")
    public static int itemDespawnAge = 6000;


}
