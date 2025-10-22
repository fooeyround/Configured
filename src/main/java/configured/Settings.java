package configured;


import dev.xpple.betterconfig.api.Config;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


import java.util.ArrayList;
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
    @Config(comment = "dedicatedServerOnly", condition = "isDedicated", setter = @Config.Setter("setSimulationDistance")) public static int simulationDistance = 0;
    @Config(comment = "dedicatedServerOnly",  condition = "isDedicated", setter = @Config.Setter("setViewDistance"))  public static int viewDistance = 0;
    @Config(comment = "dedicatedServerOnly", condition = "isDedicated") public static int maxPlayers = -1;
    @Config(comment = "dedicatedServerOnly", condition = "isDedicated") public static int maxPlayersFakeListing = -1;
    @Config(comment = "dedicatedServerOnly", condition = "isDedicated") public static int spawnProtection = -1;

    @Config(comment = "dedicatedServerOnly", condition = "isDedicated") public static SettingTypes.PlayerConnectionSetting playerConnections = SettingTypes.PlayerConnectionSetting.ALLOW_ALL;

    @Config(comment = "dedicatedServerOnly", condition = "isDedicated", adder = @Config.Adder(value = "playerListAdder"), remover = @Config.Remover(value = "playerListRemover"), chatRepresentation = "playerListCustomChatRepresentation")
    public static ArrayList<String> playerConnectionBlockList = new ArrayList<>();
    public static void playerListAdder(String string) {
        if (Configured.MC_SERVER != null && Configured.MC_SERVER.getApiServices().nameToIdCache() != null) {
            Configured.MC_SERVER.getApiServices().nameToIdCache().findByName(string).ifPresent(playerConfigEntry -> {
                String id = playerConfigEntry.id().toString();

                if (!playerConnectionBlockList.contains(id)) {
                    playerConnectionBlockList.add(id);
                }

            });
        }
    }
    public static void playerListRemover(String string) {
        if (Configured.MC_SERVER != null && Configured.MC_SERVER.getApiServices().nameToIdCache() != null){
            Configured.MC_SERVER.getApiServices().nameToIdCache().findByName(string).ifPresent(playerConfigEntry -> playerConnectionBlockList.remove(playerConfigEntry.id().toString()));
        }
    }
    private static Text playerListCustomChatRepresentation() {
        if (Configured.MC_SERVER == null || Configured.MC_SERVER.getApiServices().nameToIdCache() == null) throw new IllegalStateException("Minecraft Server reference and user cache should not be null in the context of running a configured command\nPlease report this as a bug!");
        MutableText text = Text.literal("[");
        for (int i = 0; i < playerConnectionBlockList.size(); i++) {
            final int j = i;
            Configured.MC_SERVER.getApiServices().nameToIdCache().getByUuid(UUID.fromString(playerConnectionBlockList.get(i))).ifPresent(playerConfigEntry -> {
                String name = playerConfigEntry.name();
                text.append(name);
                if (j != playerConnectionBlockList.size()-1) text.append(", ");
            });


        }
        text.append("]");
        return text;
    }

    public static void setSimulationDistance(int value) {
        simulationDistance = value;
        if (value > 0) {
            Configured.MC_SERVER.getPlayerManager().setSimulationDistance(value);
        } else {
            if (Configured.MC_SERVER instanceof MinecraftDedicatedServer dedicatedServer) {
                dedicatedServer.setSimulationDistance(dedicatedServer.getProperties().simulationDistance.get());
            } else {
                Configured.LOGGER.error("Simulation Distance failed to reset in non-dedicated setting. Please use the video settings menu. If you are running a dedicated server, please report this as a bug.");
            }

        }


    }

    public static void setViewDistance(int value) {
        viewDistance = value;
        if (value > 0) {
            Configured.MC_SERVER.getPlayerManager().setViewDistance(value);
        } else {
            if (Configured.MC_SERVER instanceof MinecraftDedicatedServer dedicatedServer) {
                Configured.MC_SERVER.getPlayerManager().setViewDistance(dedicatedServer.getProperties().viewDistance.get());
            } else {
                Configured.LOGGER.error("View Distance failed to reset in non-dedicated setting. Please use the video settings menu. If you are running a dedicated server, please report this as a bug.");
            }
        }
    }




    private static Text dedicatedServerOnly() {
        return Text.literal("This feature may only work correctly on a dedicated server.").formatted(Formatting.GOLD);
    }
    private static boolean isDedicated() {
        return Configured.MC_SERVER instanceof MinecraftDedicatedServer;
    }


}
