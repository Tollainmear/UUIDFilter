package org.Tollainmear.UUIDFilter;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Plugin(id = "uuidfilter", name = "UUIDFilter", authors = "Tollainmear", description = "UUIDFilter will kick the player if display name in case mismatched")
public class UUIDFilter {
    public String PLUGINNAME = "UUIDFilter";
    public String PlayerExistednode = "PlayerExisted";
    private Text prifixText = Text.of(TextColors.DARK_GREEN, "[", TextColors.GREEN, TextStyles.BOLD, "UUID_Filter",TextStyles.RESET, TextColors.DARK_GREEN, "]");
    private static UUIDFilter instance;
    private final Logger logger;
    private final PluginContainer pluginContainer;
    private final Game game;
    private final UUFCommand uufcommand;
    private List<String> playerListed = new ArrayList<>();
    private static CommentedConfigurationNode rootNode;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path ConfigFile;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> ConfigManager;

    @Inject
    public UUIDFilter(Logger logger, PluginContainer pluginContainer, Game game) {
        instance = this;

        this.logger = logger;
        this.pluginContainer = pluginContainer;
        this.game = game;
        uufcommand = new UUFCommand(instance);
    }

    @Listener
    public void onreload(GameReloadEvent event) throws IOException, ObjectMappingException {
        reloadList();
    }

    @Listener
    public void initConfig(GamePreInitializationEvent event) throws IOException, ObjectMappingException {
        reloadList();
    }

    @Listener
    public void onServerStarting(GameStartingServerEvent event){
        uufcommand.init();
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @First Player player) throws IOException {
        String playerName = player.getName();
        for (String str : playerListed) {
            if (playerName.equals(str)) {
                return;
            }
            if (playerName.toLowerCase().equals(str.toLowerCase())) {
                Sponge.getScheduler().createTaskBuilder()
                        .execute(() -> {
                            player.kick(Text.of(TextColors.RED,"Could not pass the UUIDFilter"
                        +"\nPlease use the name: ",TextStyles.BOLD,TextColors.GOLD,str,
                                    TextStyles.RESET,TextColors.RED," to login(case sensitive)" +
                        "\n Or ask the server admin for help."));
                        })
                        .delay(1000, TimeUnit.MILLISECONDS)
                        .submit(instance);
                return;
            }
        }
        playerListed.add(playerName);
        saveList(playerListed);
    }

    public void saveList(List<String> list) throws IOException {
        rootNode.getNode("PlayerExisted").setValue(list);
        ConfigManager.save(rootNode);
    }

    public static UUIDFilter getInstance() {
        return instance;
    }

    public Logger getlogger() {
        return logger;
    }

    public Text getPrifixText() {
        return prifixText;
    }

    public List<String> getPlayerList() {
        return playerListed;
    }


    public void reloadList() throws IOException, ObjectMappingException {
        playerListed.removeAll(playerListed);
        if (Files.notExists(ConfigFile)) {
            try {
                if (Files.notExists(ConfigFile.getParent()))
                    Files.createDirectory(ConfigFile.getParent());
                Files.createFile(ConfigFile);
                logger.info("Could not find configuration file.Creating now!!!");
            } catch (IOException ioExc) {
                this.getlogger().error("Error creating a new config file", ioExc);
                return;
            }
        }
        rootNode = ConfigManager.load();
        if (rootNode.getNode(PlayerExistednode).isVirtual() ||
                rootNode.getNode(PlayerExistednode).getChildrenList().isEmpty()) {
            playerListed.add("Tollainmear");
            rootNode.getNode(PlayerExistednode).setValue(playerListed);
            ConfigManager.save(rootNode);
        } else {
            for (String playerName : getConfigList()) {
                playerListed.add(playerName);
            }
        }
    }

    private List<String> getConfigList() throws ObjectMappingException {
        return rootNode.getNode(PlayerExistednode).getList(TypeToken.of(String.class));
    }
}
