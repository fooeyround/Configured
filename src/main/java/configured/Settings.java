package configured;


import com.mojang.authlib.GameProfile;
import dev.xpple.betterconfig.api.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.dedicated.DedicatedServer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Settings {

    @Config public static String motd = "_";
    @Config public static boolean fakeHardcore = false;
    @Config public static String  disablePlayerConnectionsJoinMessage = "";
    @Config public static boolean disableEnd = false;
    @Config public static boolean disableEndPortalFrameFilling = false;
    @Config public static boolean disableEyeOfEnderCasting = false;
    @Config public static boolean disableEndGateways = false;
    @Config public static int itemDespawnAge = 6000;
    @Config(setter = @Config.Setter("setSimulationDistance")) public static int simulationDistance = 0;
    @Config(setter = @Config.Setter("setViewDistance"))  public static int viewDistance = 0;
    @Config public static int maxPlayers = -1;
    @Config public static int maxPlayersFakeListing = -1;
    @Config public static int spawnProtection = -1;
    @Config public static SettingTypes.LocatorBarPlayerVisibility locatorBarPlayerVisibility = SettingTypes.LocatorBarPlayerVisibility.ALL;

    @Config public static Map<SettingTypes.PlayerDamageMultiplierType, Float> playerDamageMultiplier = new HashMap<>();


    @Config public static SettingTypes.PlayerConnectionSetting playerConnections = SettingTypes.PlayerConnectionSetting.ALLOW_ALL;

    @Config(adder = @Config.Adder(value = "playerListAdder"), remover = @Config.Remover(value = "playerListRemover"), chatRepresentation = "playerListCustomChatRepresentation")
    public static ArrayList<String> playerConnectionBlockList = new ArrayList<>();

    @Config
    public static ArrayList<GameProfile> playerConnectionBlockListTwo = new ArrayList<>();


    public static void playerListAdder(String string) {
        if (Configured.MC_SERVER == null) throw new IllegalStateException("Minecraft Server reference should not be null in the context of running a configured command\nPlease report this as a bug!");
        Configured.MC_SERVER.services().nameToIdCache().get(string).ifPresent(playerConfigEntry -> {
            String id = playerConfigEntry.id().toString();
            if (!playerConnectionBlockList.contains(id)) {
                playerConnectionBlockList.add(id);
            }
        });
    }

    public static void playerListRemover(String string) {
        if (Configured.MC_SERVER == null) throw new IllegalStateException("Minecraft Server reference should not be null in the context of running a configured command\nPlease report this as a bug!");
        Configured.MC_SERVER.services().nameToIdCache().get(string).ifPresent(playerConfigEntry -> playerConnectionBlockList.remove(playerConfigEntry.id().toString()));
    }

    private static Component playerListCustomChatRepresentation() {
        if (Configured.MC_SERVER == null) throw new IllegalStateException("Minecraft Server reference should not be null in the context of running a configured command\nPlease report this as a bug!");
        MutableComponent text = Component.literal("[");
        for (int i = 0; i < playerConnectionBlockList.size(); i++) {
            final int j = i;
            Configured.MC_SERVER.services().nameToIdCache().get(UUID.fromString(playerConnectionBlockList.get(i))).ifPresent(playerConfigEntry -> {
                String name = playerConfigEntry.name();
                text.append(name);
                if (j != playerConnectionBlockList.size() - 1) text.append(", ");
            });


        }
        text.append("]");
        return text;
    }

    public static void setSimulationDistance(int value) {
        simulationDistance = value;
        if (value > 0) {
            Configured.MC_SERVER.getPlayerList().setSimulationDistance(value);
        } else {
            if (Configured.MC_SERVER instanceof DedicatedServer dedicatedServer) {
                dedicatedServer.setSimulationDistance(dedicatedServer.getProperties().simulationDistance.get());
            } else {
                Configured.LOGGER.error("Simulation Distance failed to reset in non-dedicated setting. Please use the video settings menu. If you are running a dedicated server, please report this as a bug.");
            }

        }
    }

    public static void setViewDistance(int value) {
        viewDistance = value;
        if (value > 0) {
            Configured.MC_SERVER.getPlayerList().setViewDistance(value);
        } else {
            if (Configured.MC_SERVER instanceof DedicatedServer dedicatedServer) {
                Configured.MC_SERVER.getPlayerList().setViewDistance(dedicatedServer.getProperties().viewDistance.get());
            } else {
                Configured.LOGGER.error("View Distance failed to reset in non-dedicated setting. Please use the video settings menu. If you are running a dedicated server, please report this as a bug.");
            }
        }
    }

    @Deprecated
    private static Component dedicatedServerOnly() {
        return Component.literal("This feature may only work correctly on a dedicated server.").withStyle(ChatFormatting.GOLD);
    }

    @Deprecated
    private static boolean isDedicated() {
        return Configured.MC_SERVER instanceof DedicatedServer;
    }

}
