package tech.lemonlime.config;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.WorldSavePath;
import tech.lemonlime.configured.Configured;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ConfigManager {

    static record ConfigReadResult(Map<String, String> ruleMap, boolean locked) {}

    private final HashMap<String, ConfigOption<?>> options = new HashMap<>();
    public final String identifier;
    private boolean locked;

    private MinecraftServer server;


    ConfigManager(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }

    public void parseSettingsClass(Class<?> settingsClass)
    {



        for (Field field : settingsClass.getDeclaredFields()) {
            Option annotation = field.getAnnotation(Option.class);
            if (annotation == null) {
                continue;
            }


            ConfigOption<?> parsed = ParsedConfig.of(field, this);
            options.put(parsed.name(), parsed);
        }
    }


    public ConfigOption<?> getOption(String name)
    {
        return options.get(name);
    }

    public boolean locked() {
        return locked;
    }


    public Collection<ConfigOption<?>> getOptions() {
        return Collections.unmodifiableCollection(options.values());
    }

    public void addOption(ConfigOption<?> option) {
        if (options.containsKey(option.name()))
            throw new UnsupportedOperationException(identifier +"settings manager already contains a rule with that name!");
        options.put(option.name(), option);
    }


    ///I hope this does not change between MC versions, then I would need to use Fabric Loader's API...
    private Path getFile() {
        return server.getSavePath(WorldSavePath.ROOT).resolve(identifier + ".conf");
    }

    private void writeSettingsToConf(ConfigReadResult data)
    {
        if (locked)
            return;
        try (BufferedWriter fw = Files.newBufferedWriter(getFile()))
        {
            for (String key: data.ruleMap().keySet())
            {
                fw.write(key + " " + data.ruleMap().get(key));
                fw.newLine();
            }
        }
        catch (IOException e)
        {
            Configured.LOGGER.error("[Configured Config]: failed write "+identifier+".conf config file", e);
        }
    }

    private Collection<ConfigOption<?>> findStartupOverrides()
    {
        Set<String> defaults = readSettingsFromConf(getFile()).ruleMap().keySet();
        return options.values().stream().filter(r -> defaults.contains(r.name())).

                sorted(comparing(ConfigOption::name)).toList();
    }


    private ConfigReadResult readSettingsFromConf(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path))
        {
            String line;
            boolean confLocked = false;
            Map<String, String> result = new HashMap<>();
            while ((line = reader.readLine()) != null)
            {
                line = line.replaceAll("[\\r\\n]", "");
                if ("locked".equalsIgnoreCase(line)) {
                    confLocked = true;
                }
                String[] fields = line.split("\\s+",2);
                if (fields.length > 1)
                {
                    if (result.isEmpty() && fields[0].startsWith("#") || fields[1].startsWith("#"))
                    {
                        continue;
                    }
                    if (!options.containsKey(fields[0]))
                    {
                        Configured.LOGGER.error("[Configured Configs]: "+identifier+" Setting " + fields[0] + " is not a valid config option - ignoring...");
                        continue;
                    }
                    result.put(fields[0], fields[1]);
                }
            }
            return new ConfigReadResult(result, confLocked);
        }
        catch (NoSuchFileException e)
        {
            if (path.equals(getFile()) && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            {
                Path defaultsPath = FabricLoader.getInstance().getConfigDir().resolve("configured_config/default_"+identifier+".conf");
                try {
                    if (Files.notExists(defaultsPath))
                    {
                        Files.createDirectories(defaultsPath.getParent());
                        Files.createFile(defaultsPath);
                        try (BufferedWriter fw = Files.newBufferedWriter(defaultsPath))
                        {
                            fw.write("# This is " + identifier + "'s default configuration file");
                            fw.newLine();
                            fw.write("# Config options specified here will be used when a world doesn't have a config file, but they will be completely "
                                    + "ignored once the world has one.");
                            fw.newLine();
                        }
                    }
                    return readSettingsFromConf(defaultsPath);
                } catch (IOException e2) {
                    Configured.LOGGER.error("Exception when loading fallback default config: ", e2);
                }
            }
            return new ConfigReadResult(new HashMap<>(), false);
        }
        catch (IOException e) {
            Configured.LOGGER.error("Exception while loading config options from config file", e);
            return new ConfigReadResult(new HashMap<>(), false);
        }
    }


    // stores different defaults in the file
    private int setDefault(ConfigOption<?> option, String stringValue)
    {
        if (locked()) return 0;
        if (!options.containsKey(option.name())) return 0;
        ConfigReadResult conf = readSettingsFromConf(getFile());
        conf.ruleMap().put(option.name(), stringValue);
        writeSettingsToConf(conf);
        try {
            option.set(stringValue);
        } catch (Exception e) {
            Configured.LOGGER.error("Setting "+option.name()+" in settings manager"+ identifier + " could not be set!");
            return 0;
        }
        return 1;
    }


    private Collection<ConfigOption<?>> getOptionsSorted() {
        return options.values().stream().sorted(comparing(ConfigOption::name)).toList();
    }



    private int listSettings(String title, Collection<ConfigOption<?>> settings_list) {

        //TODO: can't do that without using more mc classes
        return settings_list.size();
    }


    static CompletableFuture<Suggestions> suggestMatchingContains(Stream<String> stream, SuggestionsBuilder suggestionsBuilder) {
        List<String> regularSuggestionList = new ArrayList<>();
        List<String> smartSuggestionList = new ArrayList<>();
        String query = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        stream.forEach((listItem) -> {
            // Regex camelCase Search
            var words = Arrays.stream(listItem.split("(?<!^)(?=[A-Z])")).map(s -> s.toLowerCase(Locale.ROOT)).collect(Collectors.toList());
            var prefixes = new ArrayList<String>(words.size());
            for (int i = 0; i < words.size(); i++)
                prefixes.add(String.join("", words.subList(i, words.size())));
            if (prefixes.stream().anyMatch(s -> s.startsWith(query))) {
                smartSuggestionList.add(listItem);
            }
            // Regular prefix matching, reference: CommandSource.suggestMatching
            if (CommandSource.shouldSuggest(query, listItem.toLowerCase(Locale.ROOT))) {
                regularSuggestionList.add(listItem);
            }
        });
        var filteredSuggestionList = regularSuggestionList.isEmpty() ? smartSuggestionList : regularSuggestionList;
        Objects.requireNonNull(suggestionsBuilder);
        filteredSuggestionList.forEach(suggestionsBuilder::suggest);
        return suggestionsBuilder.buildFuture();
    }


    /*
    public void registerCommand(final CommandDispatcher<ServerCommandSource> dispatcher)
    {
        if (dispatcher.getRoot().getChildren().stream().anyMatch(node -> node.getName().equalsIgnoreCase(identifier)))
        {
            Configured.LOGGER.error("Failed to add settings command for " + identifier + ". It is masking previous command.");
            return;
        }

        LiteralArgumentBuilder<ServerCommandSource> literalargumentbuilder = literal(identifier).requires((player) -> !locked());

        literalargumentbuilder.
                then(literal("removeDefault").
                        requires(s -> !locked()).
                        then(argument("rule", StringArgumentType.word()).
                                suggests( (c, b) -> suggestMatchingContains(getOptionsSorted().stream().map(ConfigOption::name), b)).
                                executes((c) -> setDefault(c)))).
                then(literal("setDefault").
                        requires(s -> !locked()).
                        then(argument("rule", StringArgumentType.word()).
                                suggests( (c, b) -> suggestMatchingContains(getOptionsSorted().stream().map(CarpetRule::name), b)).
                                then(argument("value", StringArgumentType.greedyString()).
                                        suggests((c, b)-> suggest(contextRule(c).suggestions(), b)).
                                        executes((c) -> setDefault(c.getSource(), contextRule(c), StringArgumentType.getString(c, "value")))))).
                then(argument("rule", StringArgumentType.word()).
                        suggests( (c, b) -> suggestMatchingContains(getOptionsSorted().stream().map(CarpetRule::name), b)).
                        requires(s -> !locked() ).
                        executes( (c) -> displayRuleMenu(c.getSource(), contextRule(c))).
                        then(argument("value", StringArgumentType.greedyString()).
                                suggests((c, b)-> suggest(contextRule(c).suggestions(),b)).
                                executes((c) -> setRule(c.getSource(), contextRule(c), StringArgumentType.getString(c, "value")))));

        dispatcher.register(literalargumentbuilder);
    }
    */

}
