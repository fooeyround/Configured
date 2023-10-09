package tech.lemonlime.Configurable;

import dev.xpple.betterconfig.api.Config;

public class Settings {


    @Config(comment = "to default back to what was defined in server.properties, set it to _, to make it empty use __")
    public static String motd = "_";

    @Config(comment = "This makes the client think it is hardcore. respawn still works.")
    public static boolean fakeHardcore = false;

    @Config(comment = "This disables the ability to join the server. It does not kick players!")
    public static boolean disablePlayerConnections = false;

    @Config
    public static String  disablePlayerConnectionsJoinMessage = "";
    @Config
    public static boolean disableEnd = false;

    @Config
    public static boolean disableEndPortalFrameFilling = false;


    @Config
    public static boolean disableEyeOfEnderCasting = false;


    @Config
    public static boolean disableEndGateways = false;


    @Config
    public static boolean disableNether = false;



    @Config
    public static boolean disablePVP = false;



}
